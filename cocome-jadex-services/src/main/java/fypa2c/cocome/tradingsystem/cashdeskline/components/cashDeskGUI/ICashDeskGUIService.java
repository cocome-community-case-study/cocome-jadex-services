package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI;

import jadex.commons.future.IFuture;

/**
 * Specifies the signatures of the provided services of the CardReaderControllerAgent. 
 *
 * @author Florian Abt
 */
public interface ICashDeskGUIService {
	
	/**
	 * To send a AddLastScannedProductAgainEvent 
	 * if the last selected product should be added 
	 * to the shopping card again.
	 */
	public IFuture<Void> sendAddLastScannedProductAgainEvent();
	
	/**
	 * To send a RemoveLastScannedProductEvent 
	 * if the last selected product should be 
	 * removed from the shopping card.
	 */
	public IFuture<Void> sendRemoveLastScannedProductEvent();

}
