package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashBoxClosedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.Future;
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
	public IFuture<Void> sendSaleStartedEvent() {
		return component.publishEvent(new SaleStartedEvent());
	}


	@Override
	public IFuture<Void> sendSaleFinishedEvent() {
		return component.publishEvent(new SaleFinishedEvent());
	}

	@Override
	public IFuture<Void> sendPaymentModeEvent(PaymentMode mode) {
		return component.publishEvent(new PaymentModeSelectedEvent(mode));
	}

	@Override
	public IFuture<Void> sendCashAmountEnteredEvent(double amount, boolean finalInput) {
		//TODO finalInput is included  in CAEEvent in the UseCase 1 specification. Don't know if this is really necessary
		return component.publishEvent(new CashAmountEnteredEvent(amount, finalInput));
	}

	@Override
	public IFuture<Void> sendCashBoxClosedEvent() {
		return component.publishEvent(new CashBoxClosedEvent());
	}

	
}
