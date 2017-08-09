package fypa2c.cocome.tradingsystem.cashdeskline.events;

/**
 * This Event is fired if the last scanned product in the shopping card should be removed. (Cancellation)
 * This should be done when the -1 button is pressed.
 *
 * @author Florian Abt
 */
public class RemoveLastScannedProductEvent extends Event {

	public RemoveLastScannedProductEvent() {
		super();
	}
	
	public RemoveLastScannedProductEvent(String creator) {
		super(creator);
	}
	
}
