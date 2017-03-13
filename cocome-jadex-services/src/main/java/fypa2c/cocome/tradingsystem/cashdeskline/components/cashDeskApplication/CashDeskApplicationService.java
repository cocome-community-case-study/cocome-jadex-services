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
	public IFuture<Void> sendRunningTotalChangedEvent(String productName, double productPrice, double runningTotal) {
		return component.publishEvent(new RunningTotalChangedEvent(productName, productPrice, runningTotal));
	}

	@Override
	public IFuture<Void> sendChangeAmountCalculatedEvent(double changeAmount) {
		return component.publishEvent(new ChangeAmountCalculatedEvent(changeAmount));
	}

	@Override
	public IFuture<Void> sendSaleSuccessEvent() {
		return component.publishEvent(new SaleSuccessEvent());
	}

	@Override
	public IFuture<Void> sendAccountSaleEvent(SaleTO transferSale) {
		return component.publishEvent(new AccountSaleEvent(transferSale));
	}

	@Override
	public IFuture<Void> sendSaleRegisteredEvent(String topicName, ProductWithStockItemTO p, PaymentMode mode) {
		//TODO calculate saleItemCount, 
		return component.publishEvent(new SaleRegisteredEvent(topicName, 0, mode));
	}

	@Override
	public IFuture<Void> sendInvalidCardEvent(String cardInfo) {
		return component.publishEvent(new InvalidCreditCardEvent(cardInfo));
	}
}
