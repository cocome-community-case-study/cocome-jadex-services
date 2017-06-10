package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
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
 * This agent represents the product scanner of a cash desk in the trading system.
 * It scans the bar codes of the products.
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="scannerController",type=IScannerControllerService.class, implementation=@Implementation(ScannerControllerService.class))//,
})
public class ScannerControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("ScannerControllerAgent");
		
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
				printInfoLog("Received "+result.getClass().getName());
				
			}
			
			@Override
			public void finished() {
				printInfoLog("IntermediateFuture finished");
				
			}
		});
	}
	
	
	/**
	 * Test method to start the TestGUI and initialize the ActionLister methods of the buttons.
	 */
	public void initializeTestGUI(){
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
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private IScannerControllerService getServiceProvided()
	{
		return (IScannerControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("scannerController");
	}
}
