package fypa2c.cocome.tradingsystem.cashdeskline.components;

import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * Superclass of all agents. 
 * It implements a method to publish an event using the EventBus publish service. 
 *
 * @author Florian Abt
 */
@Agent
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class EventAgent {

	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	/**
	 * Publish an event on the EventBus in the cash desk channel.
	 * @param event : The event to publish
	 */
	public IFuture<Void> publishEvent(IEvent event){
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).publishEvent(event);
		
		return Future.DONE;
	}
	
}
