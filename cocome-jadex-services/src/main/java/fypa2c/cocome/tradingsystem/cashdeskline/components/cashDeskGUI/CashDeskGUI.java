package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.NumPadKeyStroke;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;

/**
 * The CashDeskGUI for the cashier to use the cash desk.
 * It contains a number pad, a display and a status line.
 *
 * @author Florian Abt
 */
public class CashDeskGUI {

	private JFrame frame;
	
	//ListPanel with JList in a ScrollPane
	private JPanel productListPanel;
	private JList<ProductItem> list;
	private JScrollPane scrollPane;
	private ProductItem[] productItemList;
	
	//ButtonPanel with actual product, +1Button, -1 Button, start sale button, card payment button (SaleFinished), cash payment button (SaleFinished)
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
		frame.getContentPane().setLayout(new BorderLayout());		
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		//LsitPanel
		productListPanel = new JPanel(new BorderLayout());
		productItemList = new ProductItem[0];
		list = new JList<ProductItem>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(list);
		productListPanel.add(scrollPane, BorderLayout.CENTER);
		
		//ButtonPanel
		buttonPanelSale = new JPanel(new GridLayout(3, 1));
		actualProduct = new JTextField();
		actualProduct.setEditable(false);
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
		cardPaymentButton = new JButton("Pay with Card");
		cardPaymentButton.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonSubPanelTwo.add(cardPaymentButton);
		buttonPanelSale.add(buttonSubPanelTwo);
		
		//Add components to frame
		frame.getContentPane().add(productListPanel, BorderLayout.WEST);
		frame.getContentPane().add(buttonPanelSale, BorderLayout.CENTER);		
		
		frame.setVisible(true);
	}
	
	
	/**
	 * Shows the GUI to the user
	 */
	public void showGUI(){
		frame.setVisible(true);
	}
	
	public void setProductItemList(ProductItem[] productItemList){
		this.productItemList = productItemList.clone();
		//TODO Is a propagation of the array to the JList necessary?
	}

}