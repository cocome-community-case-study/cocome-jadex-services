package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI.CashDeskGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.DummyScanner.DummyScanner;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
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
	
	//The Scanner, actual its a dummy software scanner but it could replaced by an hardware scanner later
	private IScanner scanner = new DummyScanner();
	
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
		
		//GUI is not displayed in simulation mode
		if(!isSimulationOn()) {
			initializeScanner();
			
			subscribeToEvents();
		}
		
	}
	
	private void initializeScanner() {
		scanner.setActionListenerBarCodeScanned(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long barcode = scanner.getScannedBarCode();
				if(barcode > -1){
					getServiceProvided().sendProductBarCodeScannedEvent(new ProductBarcodeScannedEvent(barcode,getLog()));
				}
			}
		});
		
	}

	/**
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	public void subscribeToEvents(){
		
		//Create filter for specific events
		IFilter<IEvent> filter = new IFilter<IEvent>() {
			
			@Override
			public boolean filter(IEvent obj) {
				if (obj instanceof SaleStartedEvent) {
					return true;
				}
				if (obj instanceof SaleFinishedEvent){
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
				logEvent(result, getLog());
				//printInfoLog("Received "+result.getClass().getName());
				//If simulation is on, the scanner should do nothing
				if(!isSimulationOn()) {
					if (result instanceof SaleStartedEvent) {
						scanner.startScanProcess();
					}
					if (result instanceof SaleFinishedEvent){
						scanner.stopScanProcess();
					}
				}
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
		events[0] = new ProductBarcodeScannedEvent(0,getLog());
		TestGUI gui= new TestGUI("ScannerControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendProductBarCodeScannedEvent(new ProductBarcodeScannedEvent(0,getLog()));
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
