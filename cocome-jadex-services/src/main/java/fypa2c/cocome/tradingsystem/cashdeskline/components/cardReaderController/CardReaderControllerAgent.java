package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IComponentStep;
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
 * This agent represents the card reader of a cash desk in the trading system.
 * It is responsible for the card reading process and the PIN entering process.
 * The information is delivered to the CashDeskApplication.
 *
 * @author Florian Abt
 */
@Agent
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
		
		return Future.DONE;
	}
	
	@AgentBody
	public IFuture<Void> body(){
		initializeTestGUI();
		
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
			System.out.println("CardReaderController received "+sifuture.getNextIntermediateResult().getClass().getName());
		}
		
	return Future.DONE;
	}
	
	/**
	 * Test method to start the TestGUI and initialize the ActionLister methods of the buttons.
	 * @return
	 */
	public IFuture<Void> initializeTestGUI(){
		IEvent[] events = new IEvent[2];
		events[0] = new CreditCardScannedEvent(null);
		events[1] = new CreditCardPinEnteredEvent(0);
		TestGUI gui= new TestGUI("CardReaderControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendCreditCardScannedEvent(null);
			}
		});
		
		gui.getButtons()[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendPINEnteredEvent(0);
			}
		});
		
		
		return Future.DONE;
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
