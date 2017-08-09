package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

/**
 * This class implements the provided services of the CashBoxControllerAgent. 
 * The send*Event methods are services which only the CashBoxController should call to publish the appropriate event.
 * To subscribe for Events see IEventService. 
 *
 * @author Florian Abt
 */
@Service
public class CashBoxControllerService extends EventService implements ICashBoxControllerService
{
	@ServiceComponent
	protected CashBoxControllerAgent component;

	
	@Override
	public IFuture<Void> sendSaleStartedEvent(SaleStartedEvent event) {
		System.out.println("sendSaleStartedEvent called");
		return component.publishEvent(event);
	}


	@Override
	public IFuture<Void> sendSaleFinishedEvent(SaleFinishedEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendPaymentModeEvent(PaymentModeSelectedEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendCashAmountEnteredEvent(CashAmountEnteredEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendCashBoxClosedEvent(CashBoxClosedEvent event) {
		return component.publishEvent(event);
	}

	
}
