package fypa2c.cocome.tradingsystem.inventory.db;

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.StockItemTO;

/**
 * Specifies the signatures of the DB which contains 
 * the product specification and the inventory information.
 *
 * @author Florian Abt
 */
public interface IInventoryDB {

	public StockItemTO getStockItemByBarcode(long barcode);
}
