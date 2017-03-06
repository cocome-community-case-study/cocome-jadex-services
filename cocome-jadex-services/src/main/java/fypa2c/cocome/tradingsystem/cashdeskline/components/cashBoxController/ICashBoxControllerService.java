package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.IEventService;
import jadex.commons.future.IFuture;

/**
 * Specifies the signatures of the provided services of the CashBoxControllerAgent. 
 *
 * @author Florian Abt
 */
public interface ICashBoxControllerService extends IEventService{
	
	/**
	 * Publish an SaleStartedEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleStartedEvent();
	
	/**
	 * Publish an SaleFinishedEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleFinishedEvent();
	
	/**
	 * Publish an PaymentModeEvent to all subscriber.
	 */
	public IFuture<Void> sendPaymentModeEvent(PaymentMode mode);
	
	/**
	 * Publish an CashAmountEnteredEvent to all subscriber.
	 */
	public IFuture<Void> sendCashAmountEnteredEvent(double amount, boolean finalInput);
	
	/**
	 * Publish an CashBoxClosedEvent to all subscriber.
	 */
	public IFuture<Void> sendCashBoxClosedEvent();

}
