package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.SaleTO;
import jadex.commons.future.IFuture;

public interface ICashDeskApplicationService {

	public IFuture<Void> sendRunningTotalChangedEvent(String productName, double productPrice, double runningTotal);
	
	public IFuture<Void> sendChangeAmountCalculatedEvent(double changeAmount);
	
	public IFuture<Void> sendSaleSuccessEvent();
	
	public IFuture<Void> sendAccountSaleEvent(SaleTO transferSale);
	
	public IFuture<Void> sendSaleRegisteredEvent(String topicName, ProductWithStockItemTO p, PaymentMode mode);
	
	//public IFuture<> validateCard(String creditInfo,int pin);
	
	public IFuture<Void> sendInvalidCardEvent();
	
	//public IFuture<> debitCard(transactionId);
	
	
	
}
