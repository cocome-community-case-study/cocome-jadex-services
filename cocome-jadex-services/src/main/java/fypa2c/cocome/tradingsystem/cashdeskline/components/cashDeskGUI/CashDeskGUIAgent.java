package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
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
 * This agent represents the graphical user interface of a cash desk which shows the different information to the user.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(type=ICashDeskGUIService.class, implementation=@Implementation(CashDeskGUIService.class))//,
})
public class CashDeskGUIAgent extends EventAgent
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
						if(obj instanceof CashAmountEnteredEvent){
							return true;
						}
						if(obj instanceof SaleSuccessEvent){
							return true;
						}
						if(obj instanceof InvalidCreditCardEvent){
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
					System.out.println("CashDeskGUIAgent received "+result.getClass().getName());
					if(result instanceof SaleStartedEvent){
						//TODO Start GUI "Sale"
						System.out.println("CashDeskGUI: Satrt GUI \"Sale\"");
					}
					if(result instanceof RunningTotalChangedEvent){
						//TODO Update GUI with selected product information
						System.out.println("CashDeskGUI: Update GUI with selected product information");
					}
					if(result instanceof CashAmountEnteredEvent){
						//TODO Update GUI with entered cash amount
						System.out.println("CashDeskGUI: Update GUI with with entered cash amount");
					}
					if(result instanceof SaleSuccessEvent){
						//TODO Exit GUI "Sale"
						System.out.println("CashDeskGUI: Exit GUI \"Sale\"");
					}
					if(result instanceof InvalidCreditCardEvent){
						//TODO Udate GUI, invalid credit card
						System.out.println("CashDeskGUI: Udate GUI, invalid credit card");
					}
				}
				
			return Future.DONE;
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ICashDeskGUIService getServiceProvided()
	{
		return (ICashDeskGUIService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashBoxController");
	}

}
