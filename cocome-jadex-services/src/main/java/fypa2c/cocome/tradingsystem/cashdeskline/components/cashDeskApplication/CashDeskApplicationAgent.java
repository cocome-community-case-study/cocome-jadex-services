package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.AccountSaleEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleRegisteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
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
import jadex.micro.annotation.AgentService;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the cash desk application.  
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(name="cashDeskApplication", type=ICashDeskApplicationService.class, implementation=@Implementation(CashDeskApplicationService.class))//,
})
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class CashDeskApplicationAgent extends EventAgent {

	@Agent
	protected IInternalAccess agent;
	
	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	ICashDeskApplicationService providedService;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICashDeskApplicationService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashDeskApplication");
		
		return Future.DONE;
	}
	
	/**
	 * This method is called after the creation of the agent. 
	 */
	@AgentBody
	public IFuture<Void> body(){
		subscribeToEvents();
		
		initializeTestGUI();
		
		return Future.DONE;
	}
	 
	/** 
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	public void subscribeToEvents(){
		
		//create a listener for events
		IIntermediateResultListener<IEvent> listener = new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				System.out.println("CashDeskApplicationAgent received the event : "+result.getClass().getName());
				//TODO receive events
				
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void resultAvailable(Collection<IEvent> result) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finished() {
				// TODO Auto-generated method stub
				
			}

		};
		
		//Subscribe to specific events
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new SaleStartedEvent()).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new ProductBarcodeScannedEvent(0)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new SaleFinishedEvent()).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new PaymentModeSelectedEvent(null)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new CashAmountEnteredEvent(0,true)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new CashBoxClosedEvent()).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new CreditCardScannedEvent(null)).addIntermediateResultListener(listener);
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvent(new CreditCardPinEnteredEvent(0)).addIntermediateResultListener(listener);		
	}
	
	/**
	 * Test method to start the TestGUI and initialize the ActionLister methods of the buttons.
	 * @return
	 */
	public IFuture<Void> initializeTestGUI(){
		IEvent[] events = new IEvent[6];
		events[0] = new RunningTotalChangedEvent(null, 0, 0);
		events[1] = new ChangeAmountCalculatedEvent(0);
		events[2] = new SaleSuccessEvent();
		events[3] = new AccountSaleEvent(null);
		events[4] = new SaleRegisteredEvent(null, 0, null);
		events[5] = new InvalidCreditCardEvent(null);
		TestGUI gui= createTestGUI("CashDeskApplicationAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendRunningTotalChangedEvent(null, 0, 0);
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
						
						providedService.sendChangeAmountCalculatedEvent(0);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		gui.getButtons()[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendSaleSuccessEvent();
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		gui.getButtons()[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendAccountSaleEvent(null);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		gui.getButtons()[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendSaleRegisteredEvent(null, null, null);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		gui.getButtons()[5].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IComponentStep<Void> step =  new IComponentStep<Void>() {

					@Override
					public IFuture<Void> execute(IInternalAccess ia) {
						
						providedService.sendInvalidCardEvent(null);
						return Future.DONE;
					}
				};
				agent.getExternalAccess().scheduleStep(step);
			}
		});
		
		return Future.DONE;
	}

}
