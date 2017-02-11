package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendPINEnteredEvent(int pin) {
		// TODO Auto-generated method stub
		return null;
	}
}
