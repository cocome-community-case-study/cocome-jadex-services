package fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.commons.Boolean3;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

/**
 * This agent offers an publish/subscribe Service for all other components.
 * Events can be published on this bus by every component to all components of the cash desk.
 *
 * @author Florian Abt
 */
@ProvidedServices({
	@ProvidedService(name="eventBus", type=IEventBusService.class, implementation=@Implementation(EventBusService.class))//,
})
public class EventBusAgent extends EventAgent {

}
