package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
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
@RequiredServices({
	@RequiredService(name="cmsservice", type=IComponentManagementService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class ScannerControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	IScannerControllerService providedService;
	
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
		
		return Future.DONE;
	}
	
	/**
	 * Test method to start the TestGUI and initialize the ActionLister methods of the buttons.
	 */
	public IFuture<Void> initializeTestGUI(){
		IEvent[] events = new IEvent[1];
		events[0] = new ProductBarcodeScannedEvent(0);
		TestGUI gui= createTestGUI("ScannerControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendProductBarCodeScannedEvent(0);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		
		return Future.DONE;
	}
}
