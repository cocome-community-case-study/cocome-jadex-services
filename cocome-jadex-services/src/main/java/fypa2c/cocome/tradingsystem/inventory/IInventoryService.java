package fypa2c.cocome.tradingsystem.inventory;

import fypa2c.cocome.tradingsystem.cashdeskline.exceptions.NoSuchProductException;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import jadex.commons.future.IFuture;

/**
 * Specifies the services that the inventory agent offers.
 * In general this are check requests whether a specific product is in stock or not.
 *
 * @author Florian Abt
 */
public interface IInventoryService {

	/**
	 * Returns a ProductWithStockItemTO with the product with the given barcode. 
	 * Throws NoSuchProductException if no there is no product with the given barcode.
	 * 
	 * @throws NoSuchProductException
	 * @param barcode
	 * @return
	 */
	public IFuture<ProductWithStockItemTO> getProductWithStockItemTO(long barcode) throws NoSuchProductException;
}
