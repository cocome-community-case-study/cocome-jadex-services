package fypa2c.cocome.tradingsystem.cashdeskline.components.lightDisplayController;

import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
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
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class LightDisplayControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		return Future.DONE;
	}

}
