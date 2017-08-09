package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.events.AccountSaleEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ChangeAmountCalculatedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.InvalidCreditCardEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleRegisteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductWithStockItemTO;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.SaleTO;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

/**
 * This class implements the provided services of the CashDeskApplicationAgent. 
 * The send*Event methods are services which only the CashDeskApplicationAgent should call to publish the appropriate event. 
 * To subscribe for Events see IEventService. 
 *
 * @author Florian Abt
 */
@Service
public class CashDeskApplicationService extends EventService implements ICashDeskApplicationService
{
	@ServiceComponent
	protected CashDeskApplicationAgent component;

	@Override
	public IFuture<Void> sendRunningTotalChangedEvent(RunningTotalChangedEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendChangeAmountCalculatedEvent(ChangeAmountCalculatedEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendSaleSuccessEvent(SaleSuccessEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendAccountSaleEvent(AccountSaleEvent event) {
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendSaleRegisteredEvent(SaleRegisteredEvent event) {
		//TODO calculate saleItemCount, 
		return component.publishEvent(event);
	}

	@Override
	public IFuture<Void> sendInvalidCardEvent(InvalidCreditCardEvent event) {
		return component.publishEvent(event);
	}
}
