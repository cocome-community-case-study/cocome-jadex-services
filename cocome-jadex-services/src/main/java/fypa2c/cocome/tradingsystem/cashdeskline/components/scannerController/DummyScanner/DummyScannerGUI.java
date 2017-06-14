package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.DummyScanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.NumPadKeyStroke;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;

/**
 * The CashDeskGUI for the cashier to use the cash desk.
 * It contains a number pad, a display and a status line.
 *
 * @author Florian Abt
 */
public class DummyScannerGUI {

	private JFrame frame;

	private JPanel mainPanel;
	
	//Rows of the number pad
	private JPanel[] rows = {
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 3)),
	};
	
	//The number pad buttons
	private JButton[][] buttons = {
			{new JButton(NumPadKeyStroke.ONE.label()), new JButton(NumPadKeyStroke.TWO.label()), new JButton(NumPadKeyStroke.THREE.label())},
			{new JButton(NumPadKeyStroke.FOUR.label()), new JButton(NumPadKeyStroke.FIVE.label()), new JButton(NumPadKeyStroke.SIX.label())},
			{new JButton(NumPadKeyStroke.SEVEN.label()), new JButton(NumPadKeyStroke.EIGHT.label()), new JButton(NumPadKeyStroke.NINE.label())},
			{new JButton(NumPadKeyStroke.DELETE.label()), new JButton(NumPadKeyStroke.ZERO.label()), new JButton(NumPadKeyStroke.SCAN.label())}
	};
	
	//Barcode textfield panel
	private JPanel barCodeTextFieldPanel = new JPanel();
	
	//TextField for barcode input
	private JFormattedTextField barCodeTextField;
	
	
	
	
	/**
	 * Constructor of the CashDeskGUI
	 * 
	 * @param cashDeskNumber
	 *            : Number of the CashDesk.
	 */
	public DummyScannerGUI() {
		frame = new JFrame("DummyScanner - not active");
		frame.setSize(300, 200);
		frame.getContentPane().setLayout(new BorderLayout());		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 1));
		
		//Set buttons of NumPad rows
		for(int row=0; row<4; row++){
			for(int button=0; button<3; button++){
				rows[row].add(buttons[row][button]);
			}
			mainPanel.add(rows[row]);
		}
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		//Disable scan button, because this is the default setting of the scanner
		setInActive();
		
		//Set barcode textfield
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		barCodeTextField  = new JFormattedTextField(format);
		barCodeTextFieldPanel.setLayout(new GridLayout(1, 1));
		barCodeTextFieldPanel.add(barCodeTextField);
		frame.getContentPane().add(barCodeTextFieldPanel, BorderLayout.NORTH);		
	}

	/**
	 * Call this method to get the auto created buttons, so that you can add an
	 * action listener to them
	 * 
	 * @return an array with all buttons in the order of the events array you
	 *         set at the creation of this object in the constructor..
	 */
	public JButton[][] getButtons() {
		return buttons;
	}
	
	/**
	 * Call this method to get the BarCodeTextField for input and output 
	 * 
	 * @return
	 */
	public JFormattedTextField getBarCodeTextField(){
		return barCodeTextField;
	}
	
	/**
	 * Is the CashDeskGui frame visible
	 * 
	 * @return true, if it's visible
	 */
	public boolean isVisible(){
		return frame.isVisible();
	}
	
	/**
	 * Shows the GUI to the user
	 */
	public void showGUI(){
		frame.setVisible(true);
	}
	
	/**
	 * Enables the scanner
	 */
	public void setActive(){
		frame.setTitle("DummyScanner - active");
		buttons[3][2].setEnabled(true);
	}
	
	/**
	 * Disables the scanner
	 */
	public void setInActive(){
		frame.setTitle("DummyScanner - not active");
		buttons[3][2].setEnabled(false);
	}
}