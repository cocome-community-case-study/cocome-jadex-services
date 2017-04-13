package fypa2c.cocome.tradingsystem.cashdeskline.components.printerController;

import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController.ICardReaderControllerService;
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
 * This agent represents the printer of a cash desk in the trading system.
 * It is responsible for printing the bill.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(type=IPrinterControllerService.class, implementation=@Implementation(PrinterControllerService.class))//,
})
public class PrinterControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		return Future.DONE;
	}
	
	/**
	 * This method is called after the creation of the agent. 
	 */
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
				while(sifuture.hasNextIntermediateResult()){
					IEvent result = sifuture.getNextIntermediateResult();
					System.out.println("PrinterControllerAgent received "+result.getClass().getName());
					if(result instanceof SaleStartedEvent){
						//TODO Start printing process
						System.out.println("PronterController: Start printing process");
					}
					if(result instanceof RunningTotalChangedEvent){
						//TODO print changes
						System.out.println("PrinterController: Print changes");
					}
					if(result instanceof SaleFinishedEvent){
						//TODO Print total
						System.out.println("PrinterController: Print total");
					}
					if(result instanceof CashAmountEnteredEvent){
						//TODO Print entered cash amount
						System.out.println("PrinterController: Print entered cash amount");
					}
					if(result instanceof ChangeAmountCalculatedEvent){
						//TODO Print change amount
						System.out.println("PrinterController: Print change amount");
					}
					if(result instanceof CashBoxClosedEvent){
						//do nothing and wait for SaleSuccessEvent
					}
					if(result instanceof SaleSuccessEvent){
						//TODO Finish printing
						System.out.println("PrinterController: Finish printing");
					}
				}
				
			return Future.DONE;
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private IPrinterControllerService getServiceProvided()
	{
		return (IPrinterControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("CardReaderController");
	}

}
