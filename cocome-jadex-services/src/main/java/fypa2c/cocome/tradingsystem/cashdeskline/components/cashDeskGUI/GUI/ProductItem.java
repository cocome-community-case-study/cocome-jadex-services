package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI;

/**
 * This class represents a product of the shopping card as an item of a JList on the CashDeskGUI.
 *
 * @author Florian Abt
 */
public class ProductItem {
	
	private String productName;
	private double productPrice;
	private long barcode;

	/**
	 * Creates an item representing a product in the JList on the CashDeskGUI.
	 * 
	 * @param productName:	name of the represented product
	 * @param productPrice:	price of the represented product
	 */
	public ProductItem(long barcode, String productName, double productPrice){
		this.productName = productName;
		this.productPrice = productPrice;
		this.barcode = barcode;
	}

	/**
	 * To get the name of the item.
	 * 
	 * @return name:	name of the represented product
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * To get the price of the item.
	 * 
	 * @return price:	price of the product
	 */
	public double getProductPrice() {
		return productPrice;
	}

	/**
	 * To get the barcode of the item.
	 * 
	 * @return barcode:	barcode of the product
	 */
	public long getBarcode() {
		return barcode;
	}
	
}
