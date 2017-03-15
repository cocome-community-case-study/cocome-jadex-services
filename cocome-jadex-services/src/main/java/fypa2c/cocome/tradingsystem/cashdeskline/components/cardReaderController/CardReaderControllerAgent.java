package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
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
	@ProvidedService(name="controller",type=ICardReaderControllerService.class, implementation=@Implementation(CardReaderControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class CardReaderControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	ICardReaderControllerService providedService;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICardReaderControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("controller");
		
		return Future.DONE;
	}
	
	@AgentBody
	public IFuture<Void> body(){
		initializeTestGUI();
		
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
		TestGUI gui= createTestGUI("CardReaderControllerAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendCreditCardScannedEvent(null);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		gui.getButtons()[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendPINEnteredEvent(0);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		
		return Future.DONE;
	}

}
