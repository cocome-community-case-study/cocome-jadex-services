package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class CashBoxControllerService implements ICashBoxControllerService
{
	@ServiceComponent
	protected CashBoxControllerAgent component;
}
