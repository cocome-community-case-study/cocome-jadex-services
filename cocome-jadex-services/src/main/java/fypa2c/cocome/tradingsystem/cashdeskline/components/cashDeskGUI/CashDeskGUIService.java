package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

/**
 * This class implements the provided services of the CashDeskGUIAgent. 
 *
 * @author Florian Abt
 */
@Service
public class CashDeskGUIService implements ICashDeskGUIService
{
	@ServiceComponent
	protected CashDeskGUIAgent component;
}
