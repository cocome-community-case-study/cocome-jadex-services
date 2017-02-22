package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

@Service
public class CardReaderControllerService extends EventService implements ICardReaderControllerService
{
	@ServiceComponent
	protected CardReaderControllerAgent component;

	@Override
	public IFuture<Void> sendCreditCardScannedEvent(String creditInfo) {
		return publishEvent(new CreditCardScannedEvent(creditInfo));
	}

	@Override
	public IFuture<Void> sendPINEnteredEvent(int pin) {
		return publishEvent(new CreditCardPinEnteredEvent(pin));
	}
}
