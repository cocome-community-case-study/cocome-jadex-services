package fypa2c.cocome.tradingsystem.cashdeskline.components.lightDisplayController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class LightDisplayControllerService implements ILightDisplayControllerService
{
	@ServiceComponent
	protected LightDisplayControllerAgent component;
}
