package fypa2c.cocome.tradingsystem.inventory;

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import jadex.commons.future.IFuture;

/**
 * Specifies the services that the inventory agent offers.
 * In general this are check requests whether a specific product is in stock or not.
 *
 * @author Florian Abt
 */
public interface IInventoryService {

	public IFuture<ProductWithStockItemTO> getProductWithStockItemTO(long barcode);
}
