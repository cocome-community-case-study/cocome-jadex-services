package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

/**
 * This class implements the provided services of the CardReaderControllerAgent. 
 * The send*Event methods are services which only the CardReaderControllerAgent should call to publish the appropriate event. 
 * To subscribe for Events see IEventService. 
 *
 * @author Florian Abt
 */
@Service
public class CardReaderControllerService extends EventService implements ICardReaderControllerService
{
	@ServiceComponent
	protected CardReaderControllerAgent component;

	@Override
	public IFuture<Void> sendCreditCardScannedEvent(CreditCardScannedEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendPINEnteredEvent(CreditCardPinEnteredEvent event) {
		return component.publishEvent(event);
	}
}
