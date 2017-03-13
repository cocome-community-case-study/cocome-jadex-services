package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
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
 * This agent represents the card reader of a cash desk in the trading system.
 * It is responsible for the card reading process and the PIN entering process.
 * The information is delivered to the CashDeskApplication.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(name="controller",type=ICardReaderControllerService.class, implementation=@Implementation(CardReaderControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class CardReaderControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	ICardReaderControllerService providedService;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICardReaderControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("controller");
		
		return Future.DONE;
	}
	
	

}
