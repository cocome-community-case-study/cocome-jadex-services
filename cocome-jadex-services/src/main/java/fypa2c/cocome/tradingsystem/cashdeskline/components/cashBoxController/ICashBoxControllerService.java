package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.IEventService;
import jadex.commons.future.IFuture;


public interface ICashBoxControllerService extends IEventService{
	
	public IFuture<Void> sendSaleStartedEvent();
	
	public IFuture<Void> sendSaleFinishedEvent();
	
	public IFuture<Void> sendPaymentModeEvent(PaymentMode mode);
	
	public IFuture<Void> sendCashAmountEnteredEvent(double amount, boolean finalInput);
	
	public IFuture<Void> sendCashBoxClosedEvent();

}
