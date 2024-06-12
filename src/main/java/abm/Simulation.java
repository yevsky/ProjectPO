package abm;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class Simulation extends Application {
    public static double time = 0;
    private Slider starCountSlider;
    private Slider planetCountSlider;
    private Slider meteoriteCountSlider;
    private Slider planetSpeedSlider;
    private Slider meteoriteSpeedSlider;
    private boolean isPaused = false;
    private AnimationTimer timer;
    private int currentTime = 0;
    private XYChart.Series<Number, Number> meteoriteSeries;
    private XYChart.Series<Number, Number> planetSeries;
    private XYChart.Series<Number, Number> absorbedMeteoritesSeries;
    private Slider[] sliders;
    private Button startButton;
    private Button stopButton;

    @Override
    public void start(Stage primaryStage) {
        // Setting up the main layout and scene
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Simulation");
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Creating the input panel with sliders and labels
        VBox inputBox = new VBox(10);
        inputBox.setPadding(new Insets(10));
        inputBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        starCountSlider = new Slider(0, 10, 5);
        planetCountSlider = new Slider(0, 8, 4);
        meteoriteCountSlider = new Slider(0, 100, 50);
        planetSpeedSlider = new Slider(0.0, 100, 50);
        meteoriteSpeedSlider = new Slider(0, 10, 5);

        // Setting labels under each slider
        Label starCountLabel = new Label(Double.toString(starCountSlider.getValue()));
        Label planetCountLabel = new Label(Double.toString(planetCountSlider.getValue()));
        Label meteoriteCountLabel = new Label(Double.toString(meteoriteCountSlider.getValue()));
        Label planetSpeedLabel = new Label(Double.toString(planetSpeedSlider.getValue()));
        Label meteoriteSpeedLabel = new Label(Double.toString(meteoriteSpeedSlider.getValue()));

        startButton = new Button("Start Simulation");
        stopButton = new Button("Stop Simulation");

        inputBox.getChildren().addAll(
                new Label("Stars:"), starCountSlider, starCountLabel,
                new Label("Planets per Star:"), planetCountSlider, planetCountLabel,
                new Label("Meteorites:"), meteoriteCountSlider, meteoriteCountLabel,
                new Label("Planet Speed:"), planetSpeedSlider, planetSpeedLabel,
                new Label("Meteorite Speed:"), meteoriteSpeedSlider, meteoriteSpeedLabel,
                startButton, stopButton
        );

        // Configuring sliders
        configureSlider(starCountSlider, 1, starCountLabel);
        configureSlider(planetCountSlider, 1, planetCountLabel);
        configureSlider(meteoriteCountSlider, 10, meteoriteCountLabel);
        configureSlider(planetSpeedSlider, 1, planetSpeedLabel);
        configureSlider(meteoriteSpeedSlider, 1, meteoriteSpeedLabel);

        root.setLeft(inputBox);

        // Setting up the simulation area
        Group simulationGroup = new Group();
        simulationGroup.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        VBox.setVgrow(simulationGroup, Priority.ALWAYS);
        root.setCenter(simulationGroup);

        // Creating the charts panel
        VBox chartsBox = new VBox(10);
        chartsBox.setPadding(new Insets(10));
        chartsBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        chartsBox.setMinWidth(300);
        root.setRight(chartsBox);

        // Initializing charts for different metrics
        LineChart<Number, Number> meteoriteChart = createChart("Meteorites on the board ");
        meteoriteSeries = new XYChart.Series<>();
        meteoriteChart.getData().add(meteoriteSeries);

        LineChart<Number, Number> planetChart = createChart("Planets on the board");
        planetSeries = new XYChart.Series<>();
        planetChart.getData().add(planetSeries);

        // Edit this chart!!
        LineChart<Number, Number> absorbedMeteoritesChart = createChart("Meteorites out of border");
        absorbedMeteoritesSeries = new XYChart.Series<>();
        absorbedMeteoritesChart.getData().add(absorbedMeteoritesSeries);

        // Update collision with star & edit chart / 10.06
        LineChart<Number, Number> removedMeteoritesChart = createChart("Meteorites absorbed by Stars");
        XYChart.Series<Number, Number> removedMeteoritesSeries = new XYChart.Series<>();
        removedMeteoritesChart.getData().add(removedMeteoritesSeries);

        chartsBox.getChildren().addAll(meteoriteChart, planetChart, absorbedMeteoritesChart, removedMeteoritesChart);

        // Setting up start and stop buttons
        startButton.setOnAction(event -> {
            startSimulation(simulationGroup);
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });

        stopButton.setOnAction(event -> {
            stopSimulation();
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });

        stopButton.setDisable(true);

        // Setting up keyboard shortcuts for simulation control
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.NUMPAD1) {
                startSimulation(simulationGroup);
                startButton.setDisable(true);
                stopButton.setDisable(false);
            }
            if (event.getCode() == KeyCode.NUMPAD2) {
                isPaused = !isPaused;
            }
            if (event.getCode() == KeyCode.NUMPAD3) {
                stopSimulation();
                startButton.setDisable(false);
                stopButton.setDisable(true);
            }
        });

        // Initializing sliders and updating labels
        sliders = new Slider[]{starCountSlider, planetCountSlider, meteoriteCountSlider, planetSpeedSlider, meteoriteSpeedSlider};
        for (Slider slider : sliders) {
            slider.setDisable(false);
        }

        updateSliderLabels();
    }

    private void configureSlider(Slider slider, int majorTickUnit, Label label) {
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(majorTickUnit);
        slider.setBlockIncrement(majorTickUnit);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> label.setText(String.format("%.0f", newValue)));
    }

    private LineChart<Number, Number> createChart(String title) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(title.split(" ")[0]);

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setMinSize(300, 200);
        chart.setMaxSize(300, 200);
        return chart;
    }

    private void startSimulation(Group simulationGroup) {
        for (Slider slider : sliders) {
            slider.setDisable(true);
        }

        // Clearing previous simulation data
        simulationGroup.getChildren().clear();
        meteoriteSeries.getData().clear();
        planetSeries.getData().clear();
        absorbedMeteoritesSeries.getData().clear();
        //int absorbedMeteoritesCount = 0;

        // Retrieving values from sliders
        int starCount = (int) starCountSlider.getValue();
        int planetCount = (int) planetCountSlider.getValue();
        int meteoriteCount = (int) meteoriteCountSlider.getValue();
        double planetSpeed = planetSpeedSlider.getValue();
        double meteoriteSpeed = meteoriteSpeedSlider.getValue();

        // Initializing the board and agents generator
        Board board = new Board(1130, 800);
        Generator generator = new Generator();
        List<Agent> agents = generator.generateObjects(starCount, planetCount, meteoriteCount, planetSpeed, meteoriteSpeed);

        for (Agent agent : agents) {
            board.addAgent(agent);
        }

        // Defining the boundaries for interactions
        double minX = 0;
        double minY = 0;
        double maxX = 1100; // Width
        double maxY = 820; // Height
        InteractionManager interactionManager = new InteractionManager(minX, minY, maxX, maxY);

        // Adding agents to the simulation group and setting their visual representation
        for (Agent agent : board.getAgents()) {
            Circle circle = new Circle(agent.getX(), agent.getY(), getAgentRadius(agent));
            if (agent instanceof Star) {
                circle.setFill(Color.YELLOW);
            } else if (agent instanceof Planet) {
                circle.setFill(Color.DARKGREEN);
            } else if (agent instanceof Meteorite) {
                circle.setFill(Color.GRAY);
            }
            simulationGroup.getChildren().add(circle);
            agent.setShape(circle);
        }

        // Starting the animation timer for the simulation
        timer = new AnimationTimer() {
            private long lastChartUpdate = 0;

            @Override
            public void handle(long now) {
                if (!isPaused) {
                    time += 0.01;
                    interactionManager.move(board.getAgents());
                    interactionManager.collide(board.getAgents());
                    List<Agent> agentsToRemove = interactionManager.collide(board.getAgents());
                    removeAgents(agentsToRemove, simulationGroup, board);
                    updateShapes(board.getAgents());

                    // Updating charts every second
                    // 1_000_000_000 == 1 second
                    if (now - lastChartUpdate >= 1_000_000_000 && !isPaused) {
                        meteoriteSeries.getData().add(new XYChart.Data<>(currentTime, board.getMeteoriteCount()));
                        planetSeries.getData().add(new XYChart.Data<>(currentTime, board.getPlanetCount()));
                        absorbedMeteoritesSeries.getData().add(new XYChart.Data<>(currentTime, interactionManager.getRemovedMeteoritesCount()));

                        currentTime++;
                        lastChartUpdate = now;
                    }
                }
            }
        };
        timer.start();
    }

    private void stopSimulation() {
        if (timer != null) {
            timer.stop();
        }
        for (Slider slider : sliders) {
            slider.setDisable(false);
        }
        currentTime = 0;
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void removeAgents(List<Agent> agentsToRemove, Group simulationGroup, Board board) {
        for (Agent agent : agentsToRemove) {
            Circle circle = agent.getShape();
            if (circle != null) {
                simulationGroup.getChildren().remove(circle);
            }
            board.removeAgent(agent); // Removing agents from board
        }
    }

    private void updateShapes(List<Agent> agents) {
        for (Agent agent : agents) {
            Circle circle = agent.getShape();
            if (circle != null) {
                circle.setCenterX(agent.getX());
                circle.setCenterY(agent.getY());
            }
        }
    }

    private double getAgentRadius(Agent agent) {
        if (agent instanceof Star) {
            return 15;
        } else if (agent instanceof Planet) {
            return 5;
        } else if (agent instanceof Meteorite) {
            return 3;
        }
        return 1;
    }

    private void updateSliderLabels() {
        starCountSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> starCountSlider.setValue(newValue.intValue()));
        planetCountSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> planetCountSlider.setValue(newValue.intValue()));
        meteoriteCountSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> meteoriteCountSlider.setValue(newValue.intValue()));
        planetSpeedSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> planetSpeedSlider.setValue(newValue.doubleValue()));
        meteoriteSpeedSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> meteoriteSpeedSlider.setValue(newValue.doubleValue()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}