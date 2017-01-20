package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class CashDeskApplicationService implements ICashDeskApplicationService
{
	@ServiceComponent
	protected CashDeskApplicationAgent component;
}
