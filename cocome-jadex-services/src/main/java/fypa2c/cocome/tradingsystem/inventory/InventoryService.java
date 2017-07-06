package fypa2c.cocome.tradingsystem.inventory;

import fypa2c.cocome.tradingsystem.cashdeskline.exceptions.NoSuchProductException;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.StockItemTO;
import fypa2c.cocome.tradingsystem.inventory.db.DummyDB;
import fypa2c.cocome.tradingsystem.inventory.db.IInventoryDB;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.Future;
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
	
	IInventoryDB db = new DummyDB();

	@Override
	public IFuture<ProductWithStockItemTO> getProductWithStockItemTO(long barcode) throws NoSuchProductException {
		StockItemTO stockItemTO = db.getStockItemByBarcode(barcode);
		if(stockItemTO == null) {
			throw new NoSuchProductException("There is no product with the barcode: "+barcode);
		}
		else {
			ProductWithStockItemTO pwsiTO = new ProductWithStockItemTO();
			pwsiTO.setStockItemTO(stockItemTO);
			pwsiTO.setBarcode(barcode);
			pwsiTO.setId(barcode);
			pwsiTO.setName(stockItemTO.getProductName());
			pwsiTO.setPurchasePrice(stockItemTO.getSalesPrice());
			return new Future<ProductWithStockItemTO>(pwsiTO);
		}
	}
}
