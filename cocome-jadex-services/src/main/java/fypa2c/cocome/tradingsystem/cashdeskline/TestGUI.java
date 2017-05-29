package fypa2c.cocome.tradingsystem.cashdeskline;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;

/**
 * Test class which creates a GUI to test the sending and receiving of events 
 * between the different components.
 *
 * @author Florian Abt
 */
public class TestGUI{
	
	private JFrame frame;
	
	private JButton[] buttons;
	
	private JPanel gridPanel;
	
	/**
	 * Constructor of the TestGUI
	 * 
	 * @param title : The title of the GUI displayed to the user.
	 * @param events : An array of events, for each event a button 
	 * is displayed at the test GUI.
	 */
	public TestGUI(String title, IEvent[] events){
		frame = new JFrame(title);
		int wigth = 300;
		frame.setSize(wigth, 200);
		frame.getContentPane().setLayout(new BorderLayout());
		buttons = new JButton[events.length];
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(events.length, 1));
		for(int i=0; i<events.length; i++){
			String[] name = events[i].getClass().getName().split("\\.");
			buttons[i] = new JButton("send "+name[name.length-1]);
			gridPanel.add(buttons[i]);
		}
		frame.getContentPane().add(gridPanel, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	/**
	 * Call this method to get the auto created buttons, 
	 * so that you can add an action listener to them
	 * 
	 * @return an array with all buttons in the order 
	 * of the events array you set at the creation of 
	 * this object in the constructor..
	 */
	public JButton[] getButtons(){
		return buttons;
	}
	
}