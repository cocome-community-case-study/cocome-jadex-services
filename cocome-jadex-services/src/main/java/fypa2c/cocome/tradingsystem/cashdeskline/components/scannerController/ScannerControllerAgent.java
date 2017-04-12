package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController.ICardReaderControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the product scanner of a cash desk in the trading system.
 * It scans the bar codes of the products.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(type=IScannerControllerService.class, implementation=@Implementation(ScannerControllerService.class))//,
})
public class ScannerControllerAgent extends EventAgent
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
			System.out.println("ScannerControllerAgent received "+sifuture.getNextIntermediateResult().getClass().getName());
		}
		
	return Future.DONE;
	}
	
	
	/**
	 * Test method to start the TestGUI and initialize the ActionLister methods of the buttons.
	 */
	public IFuture<Void> initializeTestGUI(){
		IEvent[] events = new IEvent[1];
		events[0] = new ProductBarcodeScannedEvent(0);
		TestGUI gui= new TestGUI("ScannerControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendProductBarCodeScannedEvent(0);
			}
		});
		
		
		return Future.DONE;
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private IScannerControllerService getServiceProvided()
	{
		return (IScannerControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("CardReaderController");
	}
}
