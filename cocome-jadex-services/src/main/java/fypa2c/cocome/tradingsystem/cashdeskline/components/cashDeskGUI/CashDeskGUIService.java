package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import fypa2c.cocome.tradingsystem.cashdeskline.events.AddLastScannedProductAgainEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RemoveLastScannedProductEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

/**
 * This class implements the provided services of the CashDeskGUIAgent. 
 *
 * @author Florian Abt
 */
@Service
public class CashDeskGUIService implements ICashDeskGUIService
{
	@ServiceComponent
	protected CashDeskGUIAgent component;

	@Override
	public IFuture<Void> sendAddLastScannedProductAgainEvent() {
		return component.publishEvent(new AddLastScannedProductAgainEvent());
	}

	@Override
	public IFuture<Void> sendRemoveLastScannedProductEvent() {
		return component.publishEvent(new RemoveLastScannedProductEvent());
	}
}
