package fypa2c.cocome.tradingsystem.cashdeskline.exceptions;


/**
 * Indicates the failure of the creation of a LogEntry from an log file line. 
 * Probably the passed String was not valid for the creation.
 *
 * @author Florian Abt
 */
public class InvalidLogFileLineException extends Exception {

	public InvalidLogFileLineException(final String message) {
		super(message);
	}
}
