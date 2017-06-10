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
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.commons.Boolean3;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

/**
 * This agent represents the printer of a cash desk in the trading system.
 * It is responsible for printing the bill.
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="printerController", type=IPrinterControllerService.class, implementation=@Implementation(PrinterControllerService.class))//,
})
public class PrinterControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("PrinterControllerAgent");
		
		return Future.DONE;
	}
	
	/**
	 * This method is called after the creation of the agent. 
	 */
	@AgentBody 
	public void body(){
		
		subscribeToEvents();
		
	}
	 
	/**
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	public void subscribeToEvents(){
		
		//Create filter for specific events
		IFilter<IEvent> filter = new IFilter<IEvent>() {
			
			@Override
			public boolean filter(IEvent obj) {
				if(obj instanceof SaleStartedEvent){
					return true;
				}
				if(obj instanceof RunningTotalChangedEvent){
					return true;
				}
				if(obj instanceof SaleFinishedEvent){
					return true;
				}
				if(obj instanceof CashAmountEnteredEvent){
					return true;
				}
				if(obj instanceof ChangeAmountCalculatedEvent){
					return true;
				}
				if(obj instanceof CashBoxClosedEvent){
					return true;
				}
				if(obj instanceof SaleSuccessEvent){
					return true;
				}
				return false;
			}
		};
		
		//subscribe
		ISubscriptionIntermediateFuture<IEvent> sifuture = ((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvents(filter);
		
		//waiting for Events
		sifuture.addIntermediateResultListener(new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void exceptionOccurred(Exception exception) {
				printInfoLog("Exception occurred");
				exception.printStackTrace();
				
			}
			
			@Override
			public void resultAvailable(Collection<IEvent> result) {
				printInfoLog("Received IEvent collection");
				
			}
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				printInfoLog("Received "+result.getClass().getName());
				if(result instanceof SaleStartedEvent){
					//TODO Start printing process
					printInfoLog("Start printing process");
				}
				if(result instanceof RunningTotalChangedEvent){
					//TODO print changes
					printInfoLog("Print changes");
				}
				if(result instanceof SaleFinishedEvent){
					//TODO Print total
					printInfoLog("Print total");
				}
				if(result instanceof CashAmountEnteredEvent){
					//TODO Print entered cash amount
					printInfoLog("Print entered cash amount");
				}
				if(result instanceof ChangeAmountCalculatedEvent){
					//TODO Print change amount
					printInfoLog("Print change amount");
				}
				if(result instanceof CashBoxClosedEvent){
					//do nothing and wait for SaleSuccessEvent
				}
				if(result instanceof SaleSuccessEvent){
					//TODO Finish printing
					printInfoLog("Finish printing");
				}
				
			}
			
			@Override
			public void finished() {
				printInfoLog("IntermediateFuture finished");
				
			}
		});
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private IPrinterControllerService getServiceProvided()
	{
		return (IPrinterControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("printerController");
	}

}
