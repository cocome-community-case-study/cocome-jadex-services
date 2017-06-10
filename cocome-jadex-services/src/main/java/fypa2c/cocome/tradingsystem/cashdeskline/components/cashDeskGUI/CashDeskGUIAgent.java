package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
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
 * This agent represents the graphical user interface of a cash desk which shows the different information to the user.
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="cashDeskGUI",type=ICashDeskGUIService.class, implementation=@Implementation(CashDeskGUIService.class))//,
})
public class CashDeskGUIAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	//Counter of running CashDesks
	public static volatile int cashdesknumber = 0;
	
	//CashDeskGUI
	CashDeskGUI gui;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("CashDeskGUIAgent");
		
		return Future.DONE;
	}
	
	/**
	 * This method is called after the creation of the agent. 
	 */
	@AgentBody
	public void body(){
		initializeGUI();
		
		subscribeToEvents();
		
	}
	
	/**
	 * Creates the Gui of the CashDesk
	 */
	private void initializeGUI() {
		if(gui != null){
			if(!gui.isVisible()){
				gui.showGUI();
			}
		}
		else{
			cashdesknumber++;
			gui = new CashDeskGUI(cashdesknumber);
		}
		
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
					if(gui != null){
						if(!gui.isVisible()){
							gui.showGUI();
						}
					}
					else{
						cashdesknumber++;
						gui = new CashDeskGUI(cashdesknumber);
					}
					printInfoLog("Start GUI \"Sale\"");
				}
				if(result instanceof RunningTotalChangedEvent){
					//TODO Update GUI with selected product information
					printInfoLog("Update GUI with selected product information");
				}
				if(result instanceof CashAmountEnteredEvent){
					//TODO Update GUI with entered cash amount
					printInfoLog("Update GUI with with entered cash amount");
				}
				if(result instanceof SaleSuccessEvent){
					//TODO Exit GUI "Sale"
					printInfoLog("Exit GUI \"Sale\"");
				}
				if(result instanceof InvalidCreditCardEvent){
					//TODO Udate GUI, invalid credit card
					printInfoLog("Udate GUI, invalid credit card");
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
	private ICashDeskGUIService getServiceProvided()
	{
		return (ICashDeskGUIService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashDeskGUI");
	}

}
