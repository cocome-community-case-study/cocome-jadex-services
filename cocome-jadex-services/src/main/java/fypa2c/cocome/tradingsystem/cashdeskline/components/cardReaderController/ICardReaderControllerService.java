package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import jadex.commons.future.IFuture;

/**
 * Specifies the signatures of the provided services of the CardReaderControllerAgent. 
 *
 * @author Florian Abt
 */
public interface ICardReaderControllerService {

	/**
	 * Publish an CreditCardScannedEvent to all subscriber.
	 */
	public IFuture<Void> sendCreditCardScannedEvent(String creditInfo);
	
	/**
	 * Publish an PINEnteredEvent to all subscriber.
	 */
	public IFuture<Void> sendPINEnteredEvent(int pin);
	
}
