package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI;

/**
 * Collection of different modes of the sale process. 
 * The shown components in the CashDeskGUI depends on the sale process mode.
 *
 * @author Florian Abt
 */
public enum SaleProcessModes {
	SALE_NOT_STARTED(0),
	SALE_PRODUCT_SELECTION(1),
	PAYMENT_FINISHED(2),
	PAYMENT_CASH(3);
	
	//

	private final int __label;

	//

	SaleProcessModes(final int label) {
		__label = label;
	}

	public int label() {
		return __label;
	}
}
