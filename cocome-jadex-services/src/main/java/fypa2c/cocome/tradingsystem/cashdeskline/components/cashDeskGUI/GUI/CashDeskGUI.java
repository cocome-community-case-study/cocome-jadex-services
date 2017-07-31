package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import fypa2c.cocome.tradingsystem.cashdeskline.components.NumPadKeyStroke;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;

/**
 * The CashDeskGUI for the cashier to use the cash desk.
 * It contains a number pad, a display and a status line.
 *
 * @author Florian Abt
 */
public class CashDeskGUI {
	
	//actual sale process mode, default is SALE_NOT_STARTED at start up of the CashDesk
	private SaleProcessModes mode = SaleProcessModes.SALE_NOT_STARTED;

	private JFrame frame;
	
	//ListPanel with JList in a ScrollPane
	private JPanel productListPanel;
	private JList<String> list;
	private JScrollPane scrollPane;
	//After adding the first item to this list, the last entry of the list should ever be the running total as a productItem with no name and bar code.
	private ArrayList<ProductItem> productItemList;
	
	//ButtonPanel with actual product (JTextField), +1Button, -1 Button, start sale button, card payment button (SaleFinished), cash payment button (SaleFinished)
	private JPanel buttonPanelSale;
	private JTextField actualProduct;
	//FirstButtonSubPanel
	private JPanel buttonSubPanelOne;
	private JButton plusOneButton;
	private JButton minusOneButton;
	//SecondButtonSubPanel
	private JPanel buttonSubPanelTwo;
	private JButton startSaleButton;
	private JButton cashPaymentButton;
	private JButton cardPaymentButton;
	
	
	//Components of the payment view
	JPanel paymentButtonPanel;
	//Rows of the number pad
	private JPanel[] rows = {
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 3)),
			new JPanel(new GridLayout(1, 2))
	};
	
	//The number pad buttons
	private JButton[][] buttons = {
			{new JButton(NumPadKeyStroke.ONE.label()), new JButton(NumPadKeyStroke.TWO.label()), new JButton(NumPadKeyStroke.THREE.label())},
			{new JButton(NumPadKeyStroke.FOUR.label()), new JButton(NumPadKeyStroke.FIVE.label()), new JButton(NumPadKeyStroke.SIX.label())},
			{new JButton(NumPadKeyStroke.SEVEN.label()), new JButton(NumPadKeyStroke.EIGHT.label()), new JButton(NumPadKeyStroke.NINE.label())},
			{new JButton(NumPadKeyStroke.DELETE.label()), new JButton(NumPadKeyStroke.ZERO.label()), new JButton(NumPadKeyStroke.COMMA.label())},
			{new JButton(NumPadKeyStroke.ENTER.label()), new JButton(NumPadKeyStroke.FINISHSALE.label())}
	};
	
	
	//TextField for cashAmount input
	private JFormattedTextField cashAmountTextField;
	
	//TextFile for the changeAmount
	private JTextField changeAmountTextField;
	
		
	
	/**
	 * Constructor of the CashDeskGUI
	 * 
	 * @param cashDeskNumber
	 *            : Number of the CashDesk.
	 */
	public CashDeskGUI(int cashDeskNumber) {
		frame = new JFrame("CashDesk "+cashDeskNumber);
		frame.setSize(300, 200);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 	
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		//LsitPanel
		productListPanel = new JPanel(new BorderLayout());
		productItemList = new ArrayList<ProductItem>();
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane = new JScrollPane(list);
		productListPanel.add(scrollPane, BorderLayout.CENTER);
		
		//ButtonPanel (Sale_not_Started and Product_Selection mode)
		buttonPanelSale = new JPanel(new GridLayout(3, 1));
		actualProduct = new JTextField();
		actualProduct.setEditable(false);
		actualProduct.setFont(new Font("Arial", Font.PLAIN, 20));
		buttonPanelSale.add(actualProduct);
		buttonSubPanelOne = new JPanel(new GridLayout(1, 2));
		plusOneButton = new JButton("+1");
		plusOneButton.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonSubPanelOne.add(plusOneButton);
		minusOneButton = new JButton("-1");
		minusOneButton.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonSubPanelOne.add(minusOneButton);
		buttonPanelSale.add(buttonSubPanelOne);
		buttonSubPanelTwo = new JPanel(new GridLayout(1,3));
		startSaleButton = new JButton("Start a Sale");
		startSaleButton.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonSubPanelTwo.add(startSaleButton);
		cashPaymentButton = new JButton("Pay with cash");
		cashPaymentButton.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonSubPanelTwo.add(cashPaymentButton);
		cardPaymentButton = new JButton("Pay with card");
		cardPaymentButton.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonSubPanelTwo.add(cardPaymentButton);
		buttonPanelSale.add(buttonSubPanelTwo);
		
		
		
		//prepare payment view (CashPayment and CardPayment)
		paymentButtonPanel = new JPanel();
		paymentButtonPanel.setLayout(new GridLayout(7, 1));
		//Set barcode textfield
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setGroupingUsed(false);
		cashAmountTextField  = new JFormattedTextField(format);
		cashAmountTextField.setFont(new Font("Arial", Font.PLAIN, 30));
		changeAmountTextField = new JTextField();
		changeAmountTextField.setEditable(false);
		changeAmountTextField.setFont(new Font("Arial", Font.PLAIN, 30));
		paymentButtonPanel.add(changeAmountTextField);
		paymentButtonPanel.add(cashAmountTextField);
		//Set buttons of NumPad rows
		for(int row=0; row<4; row++){
			for(int button=0; button<3; button++){
				rows[row].add(buttons[row][button]);
				buttons[row][button].setFont(new Font("Arial", Font.PLAIN, 30));
			}
			paymentButtonPanel.add(rows[row]);
		}
		rows[4].add(buttons[4][0]);
		buttons[4][0].setFont(new Font("Arial", Font.PLAIN, 30));
		rows[4].add(buttons[4][1]);
		buttons[4][1].setFont(new Font("Arial", Font.PLAIN, 30));
		paymentButtonPanel.add(rows[4]);
		
		
		//Add components to frame
		setMode(SaleProcessModes.SALE_NOT_STARTED);
		frame.setVisible(true);
	}
	
	
	/**
	 * Shows the GUI to the user
	 */
	public void showGUI(){
		frame.setVisible(true);
	}
	
	/**
	 * Adds a product to the list, which is displayed on the CashDeskGUI.
	 * 
	 * @param productItem: the product to add to the displayed shopping card
	 * @param runningTotal: the new running total
	 */
	public void addProductItemToList(ProductItem productItem, double runningTotal){
		//remove the totalRunning as last entry, if it exists
		if(!productItemList.isEmpty()) {
			productItemList.remove(productItemList.size()-1);
		}
		productItemList.add(productItem);
		//TODO How to say what should be displayed if I use productItem instead of string in productItemList?
		
		//Add the totalRunning as a productItem
		productItemList.add(new ProductItem(0, "Running Total:", runningTotal));
		
		DefaultListModel<String> listModel = new DefaultListModel();
		productItemList.forEach((item) -> listModel.addElement(item.getProductName()+"   "+item.getProductPrice()+"€"));
		list.setModel(listModel);
	}
	
	/**
	 * Removes the last product from the displayed list.
	 * 
	 * @param runningTotal : the new running total
	 */
	public void removeLastProductItemFromList(double runningTotal) {
		//remove the runningTotal as last entry, if it exists
		if(!productItemList.isEmpty()) {
			productItemList.remove(productItemList.size()-1);
		}
		//remove the last product, if it exists
		if(!productItemList.isEmpty()) {
			productItemList.remove(productItemList.size()-1);
		}
		//Add the runningTotal as a productItem
		productItemList.add(new ProductItem(0, "Running Total:", runningTotal));
		
		DefaultListModel<String> listModel = new DefaultListModel();
		productItemList.forEach((item) -> listModel.addElement(item.getProductName()+"   "+item.getProductPrice()+"€"));
		list.setModel(listModel);
	}
	
	/**
	 * Removes all Items from the product list
	 */
	public void clearProductItemList() {
		productItemList.clear();
		DefaultListModel<String> listModel = new DefaultListModel();
		productItemList.forEach((item) -> listModel.addElement(item.getProductName()+"   "+item.getProductPrice()+"€"));
		list.setModel(listModel);
		
	}
	
	/**
	 * Getter for adding an ActionListener
	 * @return the button
	 */
	public JButton getPlusOneButton() {
		return plusOneButton;
	}

	/**
	 * Getter for adding an ActionListener
	 * @return the button
	 */
	public JButton getMinusOneButton() {
		return minusOneButton;
	}

	/**
	 * Getter for adding an ActionListener
	 * @return the button
	 */
	public JButton getStartSaleButton() {
		return startSaleButton;
	}

	/**
	 * Getter for adding an ActionListener
	 * @return the button
	 */
	public JButton getCashPaymentButton() {
		return cashPaymentButton;
	}

	/**
	 * Getter for adding an ActionListener
	 * @return the button
	 */
	public JButton getCardPaymentButton() {
		return cardPaymentButton;
	}
	
	/**
	 * Sets the the text of the TextField actualProduct. 
	 * 
	 * @param product: the product item
	 */
	public void setTextActualProductTextField(ProductItem product) {
		if(product == null) {
			actualProduct.setText("");
		}
		else {
			actualProduct.setText(product.getBarcode()+"\t"+product.getProductName()+"\t"+product.getProductPrice()+"€");
		}
	}
	
	/**
	 * To set the text to the TextField actualProduct (to display the amount of money that must be payed)
	 * 
	 * @param text
	 */
	public void setTextChangeAmount(String text) {
		changeAmountTextField.setText(text);
	}
	
	/**
	 * Returns the entered amount from this textField and removes it from the textField
	 * 
	 * @return the entered amount
	 */
	public double getTextCashAmountTextField() {
		if(!cashAmountTextField.getText().equals("")) {
			Double amount = Double.parseDouble(cashAmountTextField.getText());
			cashAmountTextField.setText("");
			return amount;
		}
		else {
			return 0;
		}
	}
	

	/**
	 * Set the mode of the GUI
	 * @param mode
	 */
	public void setMode(SaleProcessModes mode) {
		this.mode = mode;
		switch (mode.label()) {
		case 0:
			//Sale_not_started
			refreshSaleGUI();
			frame.getContentPane().removeAll();
			frame.getContentPane().setLayout(new BorderLayout());	
			frame.getContentPane().add(productListPanel, BorderLayout.CENTER);
			frame.getContentPane().add(buttonPanelSale, BorderLayout.EAST);	
			frame.setVisible(true);
			frame.repaint();
			plusOneButton.setEnabled(false);
			minusOneButton.setEnabled(false);
			cashPaymentButton.setEnabled(false);
			cardPaymentButton.setEnabled(false);
			startSaleButton.setEnabled(true);
			break;
		case 1:
			//Product selection
			frame.getContentPane().removeAll();
			frame.getContentPane().setLayout(new BorderLayout());	
			frame.getContentPane().add(productListPanel, BorderLayout.CENTER);
			frame.getContentPane().add(buttonPanelSale, BorderLayout.EAST);	
			frame.setVisible(true);
			frame.repaint();
			plusOneButton.setEnabled(true);
			minusOneButton.setEnabled(true);
			cashPaymentButton.setEnabled(true);
			cardPaymentButton.setEnabled(true);
			startSaleButton.setEnabled(false);
			break;
		case 2:
			//Payment_finished
			frame.getContentPane().removeAll();
			frame.getContentPane().setLayout(new BorderLayout());	
			frame.getContentPane().add(paymentButtonPanel, BorderLayout.CENTER);	
			frame.setVisible(true);
			frame.repaint();
			buttons[4][1].setEnabled(true);
			buttons[4][0].setEnabled(false);
			break;
		case 3:
			//Payment_cash
			frame.getContentPane().removeAll();
			frame.getContentPane().setLayout(new BorderLayout());	
			frame.getContentPane().add(paymentButtonPanel, BorderLayout.CENTER);	
			frame.setVisible(true);
			frame.repaint();
			buttons[4][1].setEnabled(false);
			buttons[4][0].setEnabled(true);
			break;
		}
	}
	
	private void refreshSaleGUI() {
		setTextActualProductTextField(null);
		setTextChangeAmount("");
		
		clearProductItemList();
	}


	/**
	 * Sets the ActionListener of the PaymentPanel
	 * 
	 * @param listenerEnter: the listener of the Enter button
	 * @param listenerFinishedSale: the listener of the FinishedSale button
	 */
	public void setActionListenerPayment(ActionListener listenerEnter, ActionListener listenerFinishedSale) {
		// set ActionListener of the Buttons 1 to 9
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// Number pars into text field when button is pressed
				final int number = j + 1 + (i * 3);
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cashAmountTextField.setText(cashAmountTextField.getText() + number);
					}
				});
			}
		}
		// setActionListoner of Button 0
		buttons[3][1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = cashAmountTextField.getText();
				if(!content.equals("0")){
					cashAmountTextField.setText(content + 0);
				}
			}
		});
		// setActionListoner of Button Delete
		buttons[3][0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = cashAmountTextField.getText();
				if(!content.equals("")){
					cashAmountTextField.setText(content.substring(0, content.length() - 1));
				}
			}
		});
		// setActionListoner of Button Comma
		buttons[3][2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = cashAmountTextField.getText();
				if(!content.equals("") && !content.contains(".")){
					cashAmountTextField.setText(content + ".");
				}
			}
		});
		// setActionListoner of Button Enter
		buttons[4][0].addActionListener(listenerEnter);
		// setActionListoner of Button FinishedSale
		buttons[4][1].addActionListener(listenerFinishedSale);
	}
	
	
}