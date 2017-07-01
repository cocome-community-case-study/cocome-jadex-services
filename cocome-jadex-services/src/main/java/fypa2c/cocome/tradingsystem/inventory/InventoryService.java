package fypa2c.cocome.tradingsystem.inventory;

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

/**
 * Implements the offered services of the inventory agent.
 *
 * @author Florian Abt
 */
@Service
public class InventoryService implements IInventoryService {

	@ServiceComponent
	protected InventoryAgent component;

	@Override
	public IFuture<ProductWithStockItemTO> getProductWithStockItemTO(long barcode) {
		// TODO Auto-generated method stub
		return null;
	}
}
