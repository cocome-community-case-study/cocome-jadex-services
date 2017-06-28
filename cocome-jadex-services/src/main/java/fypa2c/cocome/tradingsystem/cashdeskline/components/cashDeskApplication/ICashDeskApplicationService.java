package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.SaleTO;
import jadex.commons.future.IFuture;

/**
 * Specifies the signatures of the provided services of the CashDeskApplicationAgent. 
 *
 * @author Florian Abt
 */
public interface ICashDeskApplicationService {

	/**
	 * Publish an RunningTotalChangedEvent to all subscriber.
	 */
	public IFuture<Void> sendRunningTotalChangedEvent(long productBarcode, String productName, double productPrice, double runningTotal);
	
	/**
	 * Publish an ChangeAmountCalculatedEvent to all subscriber.
	 */
	public IFuture<Void> sendChangeAmountCalculatedEvent(double changeAmount);
	
	/**
	 * Publish an SaleSuccessEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleSuccessEvent();
	
	/**
	 * Publish an AccountSaleEvent to all subscriber.
	 */
	public IFuture<Void> sendAccountSaleEvent(SaleTO transferSale);
	
	/**
	 * Publish an SaleRegisterdEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleRegisteredEvent(String topicName, ProductWithStockItemTO p, PaymentMode mode);
	
	//TODO implement bank instance to use the validateCard method
	//public IFuture<> validateCard(String creditInfo,int pin);
	
	/**
	 * Publish an InvalidCardEvent to all subscriber.
	 */
	public IFuture<Void> sendInvalidCardEvent(String creditInfo);
	
	//TODO implement bank instance to use the debitCard method
	//public IFuture<> debitCard(transactionId);
	
	
	
}
