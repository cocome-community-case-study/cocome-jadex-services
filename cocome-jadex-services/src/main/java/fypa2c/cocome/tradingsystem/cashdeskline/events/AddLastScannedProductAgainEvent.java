package fypa2c.cocome.tradingsystem.cashdeskline.events;

/**
 * This Event is fired if the last scanned product should be added to the shopping card again.
 * This is done by pressing the "+1" Button on the GUI.
 *
 * @author Florian Abt
 */
public class AddLastScannedProductAgainEvent extends Event {

	public AddLastScannedProductAgainEvent() {
		super();
	}
	
	public AddLastScannedProductAgainEvent(String creator) {
		super(creator);
	}
}
