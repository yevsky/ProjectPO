# Project Overview

## Project Name
ProjectPO

## Description
This project is an agent-based modeling (ABM) space simulation written in Java using JavaFX. The simulation involves agents such as stars, planets, and meteorites interacting within a defined space. The simulation's parameters can be adjusted using various sliders, and the progress can be visualized through animated shapes and real-time charts. After the simulation completes, its results are saved to a text file.

## Repository Structure
- **Root Directory**
  - `.gradle` Directory: Contains configuration scripts written in Groovy.
  - `.idea` Directory: Configuration files for the IntelliJ IDEA project.
  - `build` Directory: Directory that Gradle create during the build process. 
  - `gradle` Directory: Is used to store Gradle-specific files related to the build configuration of your project.
  - `.gitignore`: Specifies files and directories to be ignored by Git.
  - `build.gradle`: Gradle build script for the project.
  - `gradlew`: Gradle wrapper script for Unix-based systems.
  - `gradlew.bat`: Gradle wrapper script for Windows systems.
  - `settings.gradle`: Gradle settings file.
     
  - `src` Directory: Contains the source code.
    - **Java Source Files**
      - `Agent.java`: The Agent class is the base class for all objects of this simulation.
      - `Board.java`: Represents the simulation area and manages the agents within it.
      - `Generator.java`: Is responsible for generating the initial set of agents for the space simulation.
      - `InteractionManager.java`: Handles agent movement, collision detection and out-of-bounds removal within the simulation area.
      - `Meteorite.java`: Represents a meteorite agent within the simulation.
      - `Planet.java`: Represents a planet agent in the simulation.
      - `ResultsPrinter.java`: Responsible for saving simulation results to a text file.
      - `Simulation.java`: Handles the initialization of the user interface, simulation setup, and animation loop.
      - `Star.java`: Represents a star agent within the simulation.
  
## Detailed Description of Java Classes
 - **Agent.java**: The Agent class is the base class for all objects of this simulation (stars, planets, and meteorites). It provides basic properties and functionalities common to all agents.
 - **Board.java**: The Board class represents the simulation area and manages the agents (stars, planets, and meteorites) within it.
 - **Generator.java**: The Generator class is responsible for generating the initial set of agents (stars, planets, and meteorites) for the space simulation.
 - **InteractionManager.java**: Handles agent movement, collision detection and out-of-bounds removal within the simulation area.
 - **Meteorite.java**: The Meteorite class represents a meteorite agent within the simulation. Meteorites move with a random direction and speed, and upon collision with a planet, they break into smaller fragments (the fragmentation is still in work).
 - **Planet.java**: The Planet class represents a planet agent in the simulation. Planets orbit a star following a circular path defined by their angular velocity and orbit radius.
 - **ResultsPrinter.java**: The ResultsPrinter class is responsible for saving simulation results to a text file. It can store user-provided input data (meteorite count, planet count, etc.) and chart data.
 - **Simulation.java**: The Simulation class is the main class for the space simulation application. It handles the initialization of the user interface, simulation setup, and animation loop.
 - **Star.java**: The Star class represents a star agent within the simulation. Unlike planets, stars do not move within the simulation and serve as fixed reference points for planets to orbit around.

## How to Run the Project
1. **Clone the Repository**:
    ```sh
    git clone https://github.com/yevsky/ProjectPO.git
    ```
2. **Open in IntelliJ IDEA**:
   Open the cloned repository in IntelliJ IDEA.
3. **Build the Project**
4. **Run the Project**:
   Open terminal and run the following command:
    ```sh
    ./gradlew run
    ```