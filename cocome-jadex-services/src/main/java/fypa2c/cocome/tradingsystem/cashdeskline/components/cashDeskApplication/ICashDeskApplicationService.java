package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.events.AccountSaleEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleRegisteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
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
	public IFuture<Void> sendRunningTotalChangedEvent(RunningTotalChangedEvent event);
	
	/**
	 * Publish an ChangeAmountCalculatedEvent to all subscriber.
	 */
	public IFuture<Void> sendChangeAmountCalculatedEvent(ChangeAmountCalculatedEvent event);
	
	/**
	 * Publish an SaleSuccessEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleSuccessEvent(SaleSuccessEvent event);
	
	/**
	 * Publish an AccountSaleEvent to all subscriber.
	 */
	public IFuture<Void> sendAccountSaleEvent(AccountSaleEvent event);
	
	/**
	 * Publish an SaleRegisterdEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleRegisteredEvent(SaleRegisteredEvent event);
	
	//TODO implement bank instance to use the validateCard method
	//public IFuture<> validateCard(String creditInfo,int pin);
	
	/**
	 * Publish an InvalidCardEvent to all subscriber.
	 */
	public IFuture<Void> sendInvalidCardEvent(InvalidCreditCardEvent event);
	
	//TODO implement bank instance to use the debitCard method
	//public IFuture<> debitCard(transactionId);
	
	
	
}
