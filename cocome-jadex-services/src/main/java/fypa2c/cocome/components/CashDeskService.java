package fypa2c.cocome.components;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

/**
 *  
 */
@Service
public class CashDeskService implements ICashDeskService
{
	/** The component. */
	@ServiceComponent
	protected CashDeskAgent component;
}
