package fypa2c.cocome.tradingsystem.cashdeskline.components.printerController;

import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
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
 * This agent represents the printer of a cash desk in the trading system.
 * It is responsible for printing the bill.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(type=IPrinterControllerService.class, implementation=@Implementation(PrinterControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class PrinterControllerAgent extends EventAgent
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
	
	/**
	 * This method is called after the creation of the agent. 
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	@AgentBody
	public void subscribeToEvents(){
		
		//create a listener for events
		IIntermediateResultListener<IEvent> listener = new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				System.out.println("PrinterController received the event : "+result.getClass().getName());
				//TODO receive events
				
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void resultAvailable(Collection<IEvent> result) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finished() {
				// TODO Auto-generated method stub
				
			}

		};
		
		//Subscribe to specific events
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new SaleStartedEvent()).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new RunningTotalChangedEvent(null,0,0)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new SaleFinishedEvent()).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new CashAmountEnteredEvent(0,true)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new ChangeAmountCalculatedEvent(0)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new CashBoxClosedEvent()).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new SaleSuccessEvent()).addIntermediateResultListener(listener);
		
	}

}
