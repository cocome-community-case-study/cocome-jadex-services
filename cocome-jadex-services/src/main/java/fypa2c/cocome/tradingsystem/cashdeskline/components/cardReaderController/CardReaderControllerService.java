package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class CardReaderControllerService implements ICardReaderControllerService
{
	@ServiceComponent
	protected CardReaderControllerAgent component;
}
