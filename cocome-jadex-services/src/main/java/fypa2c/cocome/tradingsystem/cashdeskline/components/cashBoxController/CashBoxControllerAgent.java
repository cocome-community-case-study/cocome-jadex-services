package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
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
 * This agent represents the CashBox of a cash desk in the trading system.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(name="cashBoxController", type=ICashBoxControllerService.class, implementation=@Implementation(CashBoxControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class CashBoxControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	ICashBoxControllerService providedService;
	
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICashBoxControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashBoxController");

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
				System.out.println("CashBoxControllerAgent received the event : "+result.getClass().getName());
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
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new ChangeAmountCalculatedEvent(0)).addIntermediateResultListener(listener);
		
		
		//Call test method
		testRun();
	}
	
	
	
	public void startButtonPressed()
	{
		providedService.sendSaleStartedEvent();
	}
	
	
	/*
	 * This method is only for testing the system during development.
	 * It simulates a sale process.
	 */
	public void testRun()
	{
		System.out.println("testRun() called");
		IComponentStep<Void> step =  new IComponentStep<Void>() {

			@Override
			public IFuture<Void> execute(IInternalAccess ia) {
				
				startButtonPressed();
				return Future.DONE;
			}
		};
		
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		agent.getExternalAccess().scheduleStep(step);
	}

}
