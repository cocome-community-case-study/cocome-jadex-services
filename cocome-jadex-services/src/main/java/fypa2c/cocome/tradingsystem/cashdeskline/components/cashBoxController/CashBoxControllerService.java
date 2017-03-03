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

@Service
public class CashBoxControllerService extends EventService implements ICashBoxControllerService
{
	@ServiceComponent
	protected CashBoxControllerAgent component;

	
	@Override
	public IFuture<Void> receiveEvent(IEvent event) {
		System.out.println(this.toString());
		return Future.DONE;
	}
	
	@Override
	public IFuture<Void> sendSaleStartedEvent() {
		System.out.println("sendSaleStartedEvent() called");
		return this.publishEvent(new SaleStartedEvent());
	}


	@Override
	public IFuture<Void> sendSaleFinishedEvent() {
		return publishEvent(new SaleFinishedEvent());
	}

	@Override
	public IFuture<Void> sendPaymentModeEvent(PaymentMode mode) {
		return publishEvent(new PaymentModeSelectedEvent(mode));
	}

	@Override
	public IFuture<Void> sendCashAmountEnteredEvent(double amount, boolean finalInput) {
		//TODO finalInput is included  in CAEEvent in the UseCase 1 specification. Don't know if this is really necessary
		return publishEvent(new CashAmountEnteredEvent(amount, finalInput));
	}

	@Override
	public IFuture<Void> sendCashBoxClosedEvent() {
		return publishEvent(new CashBoxClosedEvent());
	}

	
}
