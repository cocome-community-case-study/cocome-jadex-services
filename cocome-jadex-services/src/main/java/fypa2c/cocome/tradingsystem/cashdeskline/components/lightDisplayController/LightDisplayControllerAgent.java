package fypa2c.cocome.tradingsystem.cashdeskline.components.lightDisplayController;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the light which shows the express mode.
 * It should glow if the express mode is enabled.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(type=ILightDisplayControllerService.class, implementation=@Implementation(LightDisplayControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="cmsservice", type=IComponentManagementService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class LightDisplayControllerAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		return Future.DONE;
	}

}
