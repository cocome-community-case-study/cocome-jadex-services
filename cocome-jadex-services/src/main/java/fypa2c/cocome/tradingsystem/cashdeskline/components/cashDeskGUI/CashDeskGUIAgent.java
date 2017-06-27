package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI.CashDeskGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI.ProductItem;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.GUI.SaleProcessModes;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.IScannerControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
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
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.AgentKilled;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the graphical user interface of a cash desk which shows
 * the different information to the user.
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
		@ProvidedService(name = "cashDeskGUI", type = ICashDeskGUIService.class, implementation = @Implementation(CashDeskGUIService.class))// ,
})
@RequiredServices({
	@RequiredService(name="cashBoxController", type=ICashBoxControllerService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))//,
})
public class CashDeskGUIAgent extends EventAgent {
	@Agent
	protected IInternalAccess agent;

	@AgentFeature
	protected IRequiredServicesFeature requiredServicesFeature;

	// Counter of running CashDesks
	public static volatile int cashdesknumber = 0;

	// Number of this CashDesk;
	private int myCashDeskNumber;

	// CashDeskGUI
	CashDeskGUI gui;

	@AgentCreated
	public IFuture<Void> creation() {
		super.creation();

		setLog("CashDeskGUIAgent");

		// Set my cashDeskNumber and increase the counter
		myCashDeskNumber = ++cashdesknumber;

		return Future.DONE;
	}

	/**
	 * This method is called after the creation of the agent.
	 */
	@AgentBody
	public void body() {

		initializeGUI();

		subscribeToEvents();

	}

	/**
	 * Creates the Gui of the CashDesk.
	 * In general the ActionListener of the buttons are set here.
	 */
	private void initializeGUI() {
		gui = new CashDeskGUI(myCashDeskNumber);

		gui.getCardPaymentButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendSaleFinishedEvent();
				gui.setMode(SaleProcessModes.PAYMENT_CARD);	
				((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendPaymentModeEvent(PaymentMode.CREDIT_CARD);
			}
		});
		
		gui.getCashPaymentButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendSaleFinishedEvent();
				gui.setMode(SaleProcessModes.PAYMENT_CASH);				
				((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendPaymentModeEvent(PaymentMode.CASH);
			}
		});
		
		gui.getMinusOneButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO remove last Product from product list
				
			}
		});

		gui.getPlusOneButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO add another product of the last chosen product to the product list
				
			}
		});
		
		gui.getStartSaleButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendSaleStartedEvent();
				gui.setMode(SaleProcessModes.SALE_PRODUCT_SELECTION);
			}
		});
	}

	/**
	 * This method is called before the deletion of the agent.
	 */
	@AgentKilled
	public IFuture<Void> kill() {

		super.kill();

		return Future.DONE;
	}

	/**
	 * The agent subscribes to all events, it wants to listen by the event bus.
	 */
	public void subscribeToEvents() {

		// Create filter for specific events
		IFilter<IEvent> filter = new IFilter<IEvent>() {

			@Override
			public boolean filter(IEvent obj) {
				if (obj instanceof SaleStartedEvent) {
					return true;
				}
				if (obj instanceof SaleFinishedEvent) {
					return true;
				}
				if (obj instanceof RunningTotalChangedEvent) {
					return true;
				}
				if (obj instanceof CashAmountEnteredEvent) {
					return true;
				}
				if (obj instanceof SaleSuccessEvent) {
					return true;
				}
				if (obj instanceof InvalidCreditCardEvent) {
					return true;
				}
				return false;
			}
		};

		// subscribe
		ISubscriptionIntermediateFuture<IEvent> sifuture = ((IEventBusService) requiredServicesFeature
				.getRequiredService("eventBus").get()).subscribeToEvents(filter);

		// waiting for Events
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
				printInfoLog("Received " + result.getClass().getName());
				if (result instanceof SaleStartedEvent) {
					// TODO Show sale GUI
					printInfoLog("Show sale GUI");
				}
				if (result instanceof SaleFinishedEvent) {
					// TODO Switch from sale GUI to pay GUI
					printInfoLog("Switch from sale GUI to pay GUI");
				}
				if (result instanceof RunningTotalChangedEvent) {
					gui.addProductItemList(new ProductItem(((RunningTotalChangedEvent) result).getProductName(), ((RunningTotalChangedEvent) result).getProductPrice()));
				}
				if (result instanceof CashAmountEnteredEvent) {
					// TODO Update GUI with entered cash amount
					printInfoLog("Update GUI with with entered cash amount");
				}
				if (result instanceof SaleSuccessEvent) {
					// TODO Exit GUI "Sale"
					printInfoLog("Exit GUI \"Sale\"");
				}
				if (result instanceof InvalidCreditCardEvent) {
					// TODO Udate GUI, invalid credit card
					printInfoLog("Udate GUI, invalid credit card");
				}
			}

			@Override
			public void finished() {
				printInfoLog("IntermediateFuture finished");

			}
		});
	}

	/**
	 * to get the Service of this agent for access to all provided services
	 * 
	 * @return
	 */
	private ICashDeskGUIService getServiceProvided() {
		return (ICashDeskGUIService) agent.getComponentFeature(IProvidedServicesFeature.class)
				.getProvidedService("cashDeskGUI");
	}
}
