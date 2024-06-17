package abm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The ResultsPrinter class is responsible for saving simulation results to a text file.
 * It can store user-provided input data (meteorite count, planet count, etc.) and chart data.
 */
public class ResultsPrinter {
    private String starCount;
    private String planetCount;
    private String meteoriteCount;
    private String planetSpeed;
    private String meteoriteSpeed;
    private List<String> chartsData;

    /**
     * This function writes the user-provided input data for the simulation results.
     * It converts the provided double values to strings for writing it in text file.
     *
     * @param starCount The number of stars in the simulation.
     * @param planetCount The number of planets in the simulation.
     * @param meteoriteCount The number of meteorites in the simulation.
     * @param planetSpeed The speed of planets in the simulation.
     * @param meteoriteSpeed The speed of meteorites in the simulation.
     */
    public void setInputData(double starCount, double planetCount, double meteoriteCount, double planetSpeed, double meteoriteSpeed) {
        this.starCount = String.valueOf(starCount);
        this.planetCount = String.valueOf(planetCount);
        this.meteoriteCount = String.valueOf(meteoriteCount);
        this.planetSpeed = String.valueOf(planetSpeed);
        this.meteoriteSpeed = String.valueOf(meteoriteSpeed);
    }

    /**
     * This function sets the chart data for the simulation results.
     * The chart data is expected to be a list of strings.
     *
     * @param chartData A list of strings containing chart data.
     */
    public void setChartData(List<String> chartData) {
        this.chartsData = chartData;
    }

    /**
     * This function saves the simulation results to a text file named "simulation_results.txt".
     * It writes the user-provided input data and chart data to the file in a formatted way.
     */
    public void saveResults() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("simulation_results.txt"))) {
            writer.write("User Input Data:\n");
            writer.write("Star Count: " + starCount + "\n");
            writer.write("Planet Count: " + planetCount + "\n");
            writer.write("Meteorite Count: " + meteoriteCount + "\n");
            writer.write("Planet Speed: " + planetSpeed + "\n");
            writer.write("Meteorite Speed: " + meteoriteSpeed + "\n");
            writer.write("\nChart Data:\n");
            for (String data : chartsData) {
                writer.write(data + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the results.");
        }
    }
}
