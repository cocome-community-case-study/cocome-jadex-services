package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
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
 * This agent represents the card reader of a cash desk in the trading system.
 * It is responsible for the card reading process and the PIN entering process.
 * The information is delivered to the CashDeskApplication.
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="CardReaderController",type=ICardReaderControllerService.class, implementation=@Implementation(CardReaderControllerService.class))//,
})
public class CardReaderControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("CardReaderControllerAgent");
		
		return Future.DONE;
	}
	
	@AgentBody
	public void body(){
		if(isTestON()){
			initializeTestGUI();
		}
		
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
				//This Agent listen to nothing
				//If it should listen to an event, add an instanceof test here
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
				logEvent(result, getLog());
				//printInfoLog("Received "+result.getClass().getName());
			}
			
			@Override
			public void finished() {
				printInfoLog("IntermediateResult finished");
			}
		});
	}
	
	/**
	 * Test method to start the TestGUI and initialize the ActionLister methods of the buttons.
	 * @return
	 */
	public void initializeTestGUI(){
		IEvent[] events = new IEvent[2];
		events[0] = new CreditCardScannedEvent(null,getLog());
		events[1] = new CreditCardPinEnteredEvent(0, getLog());
		TestGUI gui= new TestGUI("CardReaderControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendCreditCardScannedEvent(new CreditCardScannedEvent(null, getLog()));
			}
		});
		
		gui.getButtons()[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendPINEnteredEvent(new CreditCardPinEnteredEvent(0, getLog()));
			}
		});
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ICardReaderControllerService getServiceProvided()
	{
		return (ICardReaderControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("CardReaderController");
	}

}
