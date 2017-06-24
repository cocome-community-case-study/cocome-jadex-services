package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.DummyScanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI.CashDeskGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.IScanner;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.IScannerControllerService;

public class DummyScanner implements IScanner {
	
	private DummyScannerGUI gui = new DummyScannerGUI();
	private JFormattedTextField barCodeTextField = gui.getBarCodeTextField();
	private JButton[][] buttons = gui.getButtons();
	
	/**
	 * The Scanner is inactive as a default setting. 
	 * To Use the scanner and scan products an activation is first necessary.
	 */
	public DummyScanner() {}
	
	
	@Override
	public void setActionListenerBarCodeScanned(ActionListener aListener) {
		// set ActionListener of the Buttons 1 to 9
		JFormattedTextField barCodeTextField = gui.getBarCodeTextField();
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// Number pars into text field when button is pressed
				final int number = j + 1 + (i * 3);
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						barCodeTextField.setText(barCodeTextField.getText() + number);

					}
				});
			}
		}
		// setActionListoner of Button 0
		buttons[3][1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				barCodeTextField.setText(barCodeTextField.getText() + 0);

			}
		});
		// setActionListoner of Button Delete
		buttons[3][0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = barCodeTextField.getText();
				if(!content.equals("")){
					barCodeTextField.setText(content.substring(0, content.length() - 1));
				}

			}
		});
		// setActionListoner of Button Scan
		buttons[3][2].addActionListener(aListener);

	}
		

	@Override
	public void startScanProcess() {
		if(!gui.isVisible()){
			gui.showGUI();
		}
		gui.setActive();
	}

	@Override
	public void stopScanProcess() {
		gui.setInActive();
		
	}

	@Override
	public int getScannedBarCode() {
		if(!barCodeTextField.getText().equals("")){
			try{
				int barcode = Integer.parseInt(barCodeTextField.getText());
				barCodeTextField.setText("");
				return barcode;
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}	
		}
		return -1;
	}

}
