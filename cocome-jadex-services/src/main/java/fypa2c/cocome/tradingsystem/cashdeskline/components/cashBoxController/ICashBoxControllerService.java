package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import jadex.commons.future.IFuture;

public interface ICashBoxControllerService {
	
	public IFuture<Void> sendSaleStartedEvent();
	
	public IFuture<Void> sendSaleFinishedEvent();
	
	public IFuture<Void> sendPaymentModeEvent(PaymmentMode mode);
	
	public IFuture<Void> sendCashAmountEnteredEvent(double amount, boolean finalInput);
	
	public IFuture<Void> sendCashBoxClosedEvent();

}
