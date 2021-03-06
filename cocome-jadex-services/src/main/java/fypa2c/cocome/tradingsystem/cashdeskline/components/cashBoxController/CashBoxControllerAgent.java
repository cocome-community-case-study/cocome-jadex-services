package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.SFuture;
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
 * This agent represents the CashBox of a cash desk in the trading system.
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="cashBoxController", type=ICashBoxControllerService.class, implementation=@Implementation(CashBoxControllerService.class))//,
})
public class CashBoxControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("CashBoxControllerAgent");
		
		return Future.DONE;
	}
	
	/**
	 * This method is called after the creation of the agent. 
	 */
	@AgentBody
	public void body(){
		
		if(isTestON()){
			initializeTestGUI();
		}
		
		subscribeToEvents();
				
		//testRun();
		printInfoLog("Body ended");
		
	}
	
	
	/**
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	public void subscribeToEvents(){
		
		//Create filter for specific events
		IFilter<IEvent> filter = new IFilter<IEvent>() {
			
			@Override
			public boolean filter(IEvent obj) {
				if(obj instanceof ChangeAmountCalculatedEvent){
					return true;
				}
				return false;
			}
		};
		
		
		//subscribe
		ISubscriptionIntermediateFuture<IEvent> sifuture = ((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvents(filter);
		
		SFuture.avoidCallTimeouts((Future<?>) sifuture, agent);
		
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
				logEvent(result, getLog());
				//printInfoLog("Received "+result.getClass().getName());
				if (result instanceof ChangeAmountCalculatedEvent) {
					//TODO open CashBox
					printInfoLog("Open CashBox");
				} else {
					//if more Events are added to the filter, describe here what the agent should do if it receives the event
				}
				
			}
			
			@Override
			public void finished() {
				printInfoLog("IntermediateResult finished");
			}
		});
	}
	
	
	/**
	 * start the TestGUI and initialize the ActionLister methods of the buttons.
	 * @return
	 */
	public void initializeTestGUI(){
		IEvent[] events = new IEvent[5];
		events[0] = new SaleStartedEvent(getLog());
		events[1] = new SaleFinishedEvent(getLog());
		events[2] = new PaymentModeSelectedEvent(null,getLog());
		events[3] = new CashAmountEnteredEvent(0, true,getLog());
		events[4] = new CashBoxClosedEvent(getLog()); 
		TestGUI gui= new TestGUI("CashBoxControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendSaleStartedEvent(new SaleStartedEvent(getLog()));
			}
						
		});
		
		gui.getButtons()[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendSaleFinishedEvent(new SaleFinishedEvent(getLog()));
			}
		});
		
		gui.getButtons()[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendPaymentModeEvent(new PaymentModeSelectedEvent(null, getLog()));
			}
		});
		
		gui.getButtons()[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendCashAmountEnteredEvent(new CashAmountEnteredEvent(0, true,getLog()));
			}
		});
		
		gui.getButtons()[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendCashBoxClosedEvent(new CashBoxClosedEvent(getLog()));
			}
		});
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ICashBoxControllerService getServiceProvided()
	{
		return (ICashBoxControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashBoxController");
	}
}
