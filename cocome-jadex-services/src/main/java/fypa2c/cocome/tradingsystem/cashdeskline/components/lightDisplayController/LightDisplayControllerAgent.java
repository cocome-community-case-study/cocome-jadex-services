package fypa2c.cocome.tradingsystem.cashdeskline.components.lightDisplayController;

import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController.ICardReaderControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.ISubscriptionIntermediateFuture;
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
public class LightDisplayControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		return Future.DONE;
	}
	
	@AgentBody
	public IFuture<Void> body(){
		
		subscribeToEvents();
		
		return Future.DONE;
	}
	
	/**
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	public IFuture<Void> subscribeToEvents(){
		
		//Create filter for specific events
		IFilter<IEvent> filter = new IFilter<IEvent>() {
			
			@Override
			public boolean filter(IEvent obj) {
				//This Agent listen to nothing
				//If it should listen to an event, add an instanceof test here
				return false;
			}
		};
		
		//subscribe
		ISubscriptionIntermediateFuture<IEvent> sifuture = ((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvents(filter);
		
		//waiting for Events
		while(sifuture.hasNextIntermediateResult()){
			IEvent result = sifuture.getNextIntermediateResult();
			System.out.println("LightDisplayController received "+result.getClass().getName());
		}
		
	return Future.DONE;
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ILightDisplayControllerService getServiceProvided()
	{
		return (ILightDisplayControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("CardReaderController");
	}

}
