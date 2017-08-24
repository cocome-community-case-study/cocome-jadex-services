package fypa2c.cocome.tradingsystem.cashdeskline.components.simulationController;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This class creates a GUI for the simulation of the cashdesk and the sale
 * process.
 *
 * @author Florian Abt
 */
public class SimulationGUI {

	private JFrame frame;

	private JPanel gridButtonPanel;

	private JButton startButton;
	private JButton stopButton;

	public SimulationGUI() {
		// create the GUI
		frame = new JFrame("Simulation");
		frame.setSize(300, 200);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		startButton = new JButton("Strart Simulation");
		stopButton = new JButton("Stop Simulation");
		gridButtonPanel = new JPanel();
		gridButtonPanel.setLayout(new GridLayout(2, 1));
		gridButtonPanel.add(startButton);
		gridButtonPanel.add(stopButton);
		frame.getContentPane().add(gridButtonPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/**
	 * Use this to add an action listener to the startButton
	 * 
	 * @return a reference of the start button
	 */
	public JButton getStartButton(){
		return startButton;
	}
	
	/**
	 * Use this to add an action listener to the stopButton
	 * 
	 * @return a reference of the stop button
	 */
	public JButton getStopButton(){
		return stopButton;
	}
	
	/**
	 * Refreshes the GUI if other Threads block this.
	 */
	public void refreshGUI() {
		frame.repaint();
	}
}
