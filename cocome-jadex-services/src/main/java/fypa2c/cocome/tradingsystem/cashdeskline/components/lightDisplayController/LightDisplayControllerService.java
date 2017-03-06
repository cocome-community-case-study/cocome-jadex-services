package fypa2c.cocome.tradingsystem.cashdeskline.components.lightDisplayController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

/**
 * This class implements the provided services of the LightDisplayControllerAgent. 
 *
 * @author Florian Abt
 */
@Service
public class LightDisplayControllerService implements ILightDisplayControllerService
{
	@ServiceComponent
	protected LightDisplayControllerAgent component;
}
