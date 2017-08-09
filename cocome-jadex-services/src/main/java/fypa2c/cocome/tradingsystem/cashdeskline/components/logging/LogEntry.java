package fypa2c.cocome.tradingsystem.cashdeskline.components.logging;

import java.util.Date;

import fypa2c.cocome.tradingsystem.cashdeskline.events.Event;
import fypa2c.cocome.tradingsystem.cashdeskline.exceptions.InvalidLogFileLineException;

/**
 * A log entry containing the event name and ID, 
 * also the time stamp of throwing this event, 
 * the component, method and line number, 
 * which throws this event
 *
 * @author Florian Abt
 */
public class LogEntry {

	private final int eventID;
	private final String eventName;
	//the name of the component, which fired the event
	private final String firedComponentName;
	//the name of the component, which called the firedComponent to fire the event (they can be different)
	private final String callComponentName;
	//the name of the method in which the service of the firedComponent was called
	private final String callingMethod;
	//the time stamp of the creation of this entry;
	private final Date timeStamp;
	//the receiver of the event, if it is null there is no receiver
	private final String receiverName;

	/**
	 * To create a log entry object.
	 * 
	 * @param eventID
	 * @param eventName
	 * @param firedComponentName: the name of the component, which fired the event
	 * @param callComponentName: the name of the component, which called the firedComponent to fire the event (they can be different)
	 * @param callingMethod: the name of the method in which the service of the firedComponent was called
	 * @param receiverName: the name of the component which received the event, null if where is actual no receiver
	 * @param timeStamp: the timeStamp of the creation of the event
	 */
	public LogEntry(int eventID, String eventName,String firedComponentName, String callComponentName, String callingMethod, String receiverName, Date timeStamp) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		this.firedComponentName = firedComponentName;
		this.callComponentName = callComponentName;
		this.callingMethod = callingMethod;
		this.receiverName = receiverName;
		this.timeStamp = timeStamp;
	}
	
	/**
	 * creates a LogEntry out of a given line from the log file.
	 * 
	 * @param logLine: line from the log file;
	 * @throws InvalidLogFileLineException if the creation of the logEntry fails
	 */
	public LogEntry(String logLine) throws InvalidLogFileLineException {
		try {
			String[] line = logLine.split(";");
			timeStamp = new Date(Long.parseLong(line[0]));
			firedComponentName = line[1];
			callComponentName = line[2];
			callingMethod = line[3];
			receiverName = line[4];
			eventName = line[5];
			eventID = Integer.parseInt(line[6]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new InvalidLogFileLineException("LogEntry creation from log file line failed");
		}
				
	}

	/**
	 * @return the firedComponentName: the name of the component, which fired the event
	 */
	public String getFiredComponentName() {
		return firedComponentName;
	}
	
	/**
	 * @return the callComponentName: the name of the component, which called the firedComponent to fire the event (they can be different)
	 */
	public String getCallComponentName() {
		return callComponentName;
	}

	/**
	 * @return the eventID
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @return the callingMethod: the name of the method in which the service of the firedComponent was called
	 */
	public String getCallingMethod() {
		return callingMethod;
	}

	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Creates an String containing the whole information of this entry in one line:
	 * 				"TimeStamp in milliseconds;				
	 * 				Name of firedComponent;
	 * 				Name of callComponent;
	 * 				Name of callingMethod;
	 * 				event name;
	 * 				event id"
	 */
	public String toString() {
		return timeStamp.getTime()+";"
				+firedComponentName+";"
				+callComponentName+";"
				+callingMethod+";"
				+receiverName+";"
				+eventName+";"
				+eventID;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}
	
	
}
