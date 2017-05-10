package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import org.apache.commons.logging.Log;

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
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.Boolean3;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentKilled;
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
		setLog("CashBoxControllerAgent");
		
		return Future.DONE;
	}
	
	/**
	 * This method is called after the creation of the agent. 
	 */
	@AgentBody
	public void body(){
		
		initializeTestGUI();
		
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
		
		
		//waiting for Events
		sifuture.addIntermediateResultListener(new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void exceptionOccurred(Exception exception) {
				printInfoLog("Exception occurred");
			}
			
			@Override
			public void resultAvailable(Collection<IEvent> result) {
				printInfoLog("Received IEvent collection");
			}
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				printInfoLog("Received "+result.getClass().getName());
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
//		try{
//			while(sifuture.hasNextIntermediateResult()){
//				IEvent result = sifuture.getNextIntermediateResult();
//				System.out.println("CashBoxControllerAgent received "+result.getClass().getName());
//				if (result instanceof ChangeAmountCalculatedEvent) {
//					//TODO open CashBox
//					System.out.println("CashBoxController: Open CashBox");
//				} else {
//					//if more Events are added to the filter, describe here what the agent should do if it receives the event
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
		
		
	}
	
	
	/**
	 * start the TestGUI and initialize the ActionLister methods of the buttons.
	 * @return
	 */
	public void initializeTestGUI(){
		IEvent[] events = new IEvent[5];
		events[0] = new SaleStartedEvent();
		events[1] = new SaleFinishedEvent();
		events[2] = new PaymentModeSelectedEvent(null);
		events[3] = new CashAmountEnteredEvent(0, true);
		events[4] = new CashBoxClosedEvent(); 
		TestGUI gui= new TestGUI("CashBoxControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendSaleStartedEvent();
			}
						
		});
		
		gui.getButtons()[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendSaleFinishedEvent();
			}
		});
		
		gui.getButtons()[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendPaymentModeEvent(null);
			}
		});
		
		gui.getButtons()[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendCashAmountEnteredEvent(0, true);
			}
		});
		
		gui.getButtons()[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendCashBoxClosedEvent();
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
	
//---------------------------------------Test Methods-------------------------------
	
	public void startButtonPressed()
	{
		getServiceProvided().sendSaleStartedEvent();
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
				System.out.println("startButton pressed");
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
		System.out.println("testRun() finished");
		startButtonPressed();
	}
	
}
