package fypa2c.cocome.tradingsystem.cashdeskline.exceptions;

/**
 * This Exception should be thrown by the DB, if no product with this barcode exists.
 *
 * @author Florian Abt
 */
public class NoSuchBarcodeException extends RuntimeException {

	private static final long serialVersionUID = -7625512381587743122L;

	public NoSuchBarcodeException(String message) {
		super(message);
	}
}
