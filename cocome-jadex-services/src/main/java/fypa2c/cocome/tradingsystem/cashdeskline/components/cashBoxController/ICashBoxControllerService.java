package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.IEventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
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
	public IFuture<Void> sendSaleStartedEvent(SaleStartedEvent event);
	
	/**
	 * Publish an SaleFinishedEvent to all subscriber.
	 */
	public IFuture<Void> sendSaleFinishedEvent(SaleFinishedEvent event);
	
	/**
	 * Publish an PaymentModeEvent to all subscriber.
	 */
	public IFuture<Void> sendPaymentModeEvent(PaymentModeSelectedEvent event);
	
	/**
	 * Publish an CashAmountEnteredEvent to all subscriber.
	 */
	public IFuture<Void> sendCashAmountEnteredEvent(CashAmountEnteredEvent event);
	
	/**
	 * Publish an CashBoxClosedEvent to all subscriber.
	 */
	public IFuture<Void> sendCashBoxClosedEvent(CashBoxClosedEvent event);

}
