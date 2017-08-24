# Usable simulation results:
There are three results in the logs folder. 
The first one uses speedOfSimulation = 100 (100 seconds real time are 1 second simulation time) with a run time of 1 minute.
The second simulation uses speedOfSimulation = 100 with a runtime of 10 minutes.
The third simulation uses speedOfSimulation = 500 with a run time of 1 minute.
The fourth simulation uses speedOfSimulation = 500 with a runtime of 10 minutes.

# Start up of this project:
Clone the project and import it into eclipse. You may have to add the Apache.commons-math frame work (http://commons.apache.org/proper/commons-math/) to your build-path.

# Simulation settings:
The class SimulationControllerAgent provides the possibility to set the simulation speed and the properties of the distributions.
	 
# Start simulation:
To start the simulation start the inventory first (run StarterInventory).
Than start the cash desk (run StarterCashDesk). If the simulationON value in the properties file is true the simulation GUI is displayed. You can start and stop a simulation now.
To log events while the simulation is running ensure that the logON value in the properties file is true.
The logs will be written to the logs folder.