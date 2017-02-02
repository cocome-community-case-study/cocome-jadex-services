package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class CardReaderControllerService extends EventService implements ICardReaderControllerService
{
	@ServiceComponent
	protected CardReaderControllerAgent component;
}
