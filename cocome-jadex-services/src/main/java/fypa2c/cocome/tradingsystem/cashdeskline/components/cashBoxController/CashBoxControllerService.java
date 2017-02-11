package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
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
	public IFuture<Void> sendSaleStartedEvent() {
		return this.publishEvent(new SaleStartedEvent());
	}


	@Override
	public IFuture<Void> receiveEvent(IEvent event) {
		
		System.out.println(this.toString());
		return Future.DONE;
	}

	@Override
	public IFuture<Void> sendSaleFinishedEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendPaymentModeEvent(PaymentMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendCashAmountEnteredEvent(double amount, boolean finalInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFuture<Void> sendCashBoxClosedEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
