package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.SaleTO;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

@Service
public class CashDeskApplicationService implements ICashDeskApplicationService
{
	@ServiceComponent
	protected CashDeskApplicationAgent component;

	@Override
	public IFuture<Void> sendRunningTotalChangedEvent(String productName, double productPrice, double runningTotal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendChangeAmountCalculatedEvent(double changeAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendSaleSuccessEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendAccountSaleEvent(SaleTO transferSale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendSaleRegisteredEvent(String topicName, ProductWithStockItemTO p, PaymentMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendInvalidCardEvent() {
		// TODO Auto-generated method stub
		return null;
	}
}
