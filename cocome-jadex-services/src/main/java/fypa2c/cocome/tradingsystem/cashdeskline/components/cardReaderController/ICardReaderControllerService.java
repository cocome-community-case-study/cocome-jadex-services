package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import jadex.commons.future.IFuture;

public interface ICardReaderControllerService {

	public IFuture<Void> sendCreditCardScannedEvent(String creditInfo);
	
	public IFuture<Void> sendPINEnteredEvent(int pin);
	
}
