package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import jadex.commons.future.IFuture;

public interface ICashBoxControllerService {
	
	public IFuture<Void> sendSaleStartedEvent();

}
