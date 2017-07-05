package fypa2c.cocome.tradingsystem.inventory.db;

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.StockItemTO;

/**
 * Specifies the signatures of the DB which contains 
 * the product specification and the inventory information.
 *
 * @author Florian Abt
 */
public interface IInventoryDB {

	/**
	 * Call to get a stock item (product) by ID. 
	 * If no product with this barcode exists 
	 * 
	 * @param barcode
	 * @return
	 */
	public StockItemTO getStockItemByBarcode(long barcode);
}
