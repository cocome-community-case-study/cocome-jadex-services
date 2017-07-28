package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import fypa2c.cocome.tradingsystem.cashdeskline.TestGUI;
import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.ICashDeskGUIService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.IScannerControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.AccountSaleEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.AddLastScannedProductAgainEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardPinEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CreditCardScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RemoveLastScannedProductEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleRegisteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.exceptions.NoSuchProductException;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductTO;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import fypa2c.cocome.tradingsystem.inventory.IInventoryService;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.Boolean3;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.IResultListener;
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
 * This agent represents the cash desk application.  
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="cashDeskApplication", type=ICashDeskApplicationService.class, implementation=@Implementation(CashDeskApplicationService.class))//,
})
@RequiredServices({
	@RequiredService(name="inventory", type=IInventoryService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_GLOBAL)),
	@RequiredService(name="scannerController", type=IScannerControllerService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))
})
public class CashDeskApplicationAgent extends EventAgent {
	
	//Shopping card represented by a LinkList
	private ShoppingCard shoppingCard = new ShoppingCard();
	
	
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("CashDeskApplicationAgent");
		
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
				if(obj instanceof SaleStartedEvent){
					return true;
				}
				if(obj instanceof ProductBarcodeScannedEvent){
					return true;
				}
				if(obj instanceof SaleFinishedEvent){
					return true;
				}
				if(obj instanceof PaymentModeSelectedEvent){
					return true;
				}
				if(obj instanceof CashAmountEnteredEvent){
					return true;
				}
				if(obj instanceof CashBoxClosedEvent){
					return true;
				}
				if(obj instanceof CreditCardScannedEvent){
					return true;
				}
				if(obj instanceof CreditCardPinEnteredEvent){
					return true;
				}
				if(obj instanceof AddLastScannedProductAgainEvent) {
					return true;
				}
				if(obj instanceof RemoveLastScannedProductEvent) {
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
				printInfoLog("Received "+result.getClass().getName());
				if(result instanceof SaleStartedEvent){
					//TODO Start process "Product Selection"
					printInfoLog("Start process \"Product Selection\"");
				}
				if(result instanceof ProductBarcodeScannedEvent){
					long barcode = ((ProductBarcodeScannedEvent) result).getBarcode();
					try {
						IFuture<ProductWithStockItemTO> future = ((IInventoryService)requiredServicesFeature.getRequiredService("inventory").get()).getProductWithStockItemTO(barcode);
						future.addResultListener(new IResultListener<ProductWithStockItemTO>() {
							
							@Override
							public void exceptionOccurred(Exception exception) {
								exception.printStackTrace();
								
							}
							
							@Override
							public void resultAvailable(ProductWithStockItemTO result) {
								shoppingCard.addProduct(result);
								getServiceProvided().sendRunningTotalChangedEvent(result.getBarcode(), result.getName(), result.getPurchasePrice(), shoppingCard.getRunningTotal());
							}
						});
					} catch (NoSuchProductException e) {
						
						e.printStackTrace();
					}
				}
				if(result instanceof SaleFinishedEvent){
					//TODO Finish process "Product Selection"
					printInfoLog("Finish process \"Product Selection\"");
				}
				if(result instanceof PaymentModeSelectedEvent){
					if(((PaymentModeSelectedEvent)result).getMode().equals(PaymentMode.CASH)) {
						printInfoLog("Start process \"Cash Payment\"");		
					}
					else if(((PaymentModeSelectedEvent)result).getMode().equals(PaymentMode.CREDIT_CARD)) {
						printInfoLog("Start process \"Card Payment\"");
					}
					
				}
				if(result instanceof CashAmountEnteredEvent){
					//TODO Calculate (new due amount or) change amount
					printInfoLog("Calculate (new due amount or) change amount");
					getServiceProvided().sendChangeAmountCalculatedEvent(0);
				}
				if(result instanceof CashBoxClosedEvent){
					//TODO Update inventory and register sale
					printInfoLog("Update inventory and register sale");
					getServiceProvided().sendSaleSuccessEvent();
					getServiceProvided().sendAccountSaleEvent(null);
					getServiceProvided().sendSaleRegisteredEvent(null, null, null);
				}
				if(result instanceof CreditCardScannedEvent){
					//TODO Store credit card info and wait for CreditCardPinEnteredEvent
					printInfoLog("Store credit card info and wait for CreditCardPinEnteredEvent");
				}
				if(result instanceof CreditCardPinEnteredEvent){
					//TODO valdiateCard (How to realize the Bank?)
					printInfoLog("validate Card");
					//If Card is invalid, send InvalidCreditCardEvent()
					//getServiceProvided().sendInvalidCardEvent(null);
					//else
					printInfoLog("if Card is valid, finish payment process successfully");
					getServiceProvided().sendSaleSuccessEvent();
					getServiceProvided().sendAccountSaleEvent(null);
					getServiceProvided().sendSaleRegisteredEvent(null, null, null);
				}
				if(result instanceof AddLastScannedProductAgainEvent) {
					LinkedList<ProductTO> actualList = shoppingCard.getActualShoppingCard();
					if(!actualList.isEmpty()) {
						((IScannerControllerService)requiredServicesFeature.getRequiredService("scannerController").get()).sendProductBarCodeScannedEvent(actualList.get(actualList.size()-1).getBarcode());
					}
					else {
						printInfoLog("The shopping card is empty, I have no product to add again, sorry!");
					}
				}
				if(result instanceof RemoveLastScannedProductEvent) {
					ProductTO removedProduct = shoppingCard.removeLastProduct();
					if(removedProduct != null) {
						getServiceProvided().sendRunningTotalChangedEvent(-1, "cancel last product", removedProduct.getPurchasePrice() * -1, shoppingCard.getRunningTotal());
					}
					else {
						printInfoLog("Trying to remove a product from an empty shopping card... Check your code!");
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
	 * @return
	 */
	public void initializeTestGUI(){
		IEvent[] events = new IEvent[6];
		events[0] = new RunningTotalChangedEvent(0,null, 0, 0);
		events[1] = new ChangeAmountCalculatedEvent(0);
		events[2] = new SaleSuccessEvent();
		events[3] = new AccountSaleEvent(null);
		events[4] = new SaleRegisteredEvent(null, 0, null);
		events[5] = new InvalidCreditCardEvent(null);
		TestGUI gui= new TestGUI("CashDeskApplicationAgent", events);
		
		//Add ActionListener to Buttons
		gui.getButtons()[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendRunningTotalChangedEvent(0, null, 0, 0);
			}
		});
		
		gui.getButtons()[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendChangeAmountCalculatedEvent(0);
			}
		});
		
		gui.getButtons()[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendSaleSuccessEvent();
			}
		});
		
		gui.getButtons()[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendAccountSaleEvent(null);
			}
		});
		
		gui.getButtons()[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendSaleRegisteredEvent(null, null, null);
			}
		});
		
		gui.getButtons()[5].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getServiceProvided().sendInvalidCardEvent(null);
			}
		});
	}

	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ICashDeskApplicationService getServiceProvided()
	{
		return (ICashDeskApplicationService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashDeskApplication");
	}
}
