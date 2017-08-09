package fypa2c.cocome.tradingsystem.cashdeskline.events;

import java.util.Date;

public class Event implements IEvent {

	//Static event id counter
	public static int EVENTID = 0;
		
	//event id of the corresponding event
	private int eventID;
	
	//time stamp of the creation of this event
	private Date date; 
	
	//name of the component which created this event
	private String creator;
	
	//name of the method in which the event was created
	private String creatorMethod = null;
	
	//name of the component which fired this event
	private String gunner;
	
	public Event() {}; 
	
	public Event(String creator) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		creatorMethod = trace[3].getMethodName();
		this.creator = creator;
		eventID = EVENTID++;
		date = new Date();
	}
	
	/**
	 * Returns the ID of this event
	 * 
	 * @return id of the event
	 */
	public int getEventID() {
		return eventID;

	}

	/**
	 * Returns the creation date of this event. 
	 * It is a time stamp of the creation.
	 * 
	 * @return date of creation of this event
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns the name of the component which created this event
	 * 
	 * @return name of creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * To set the name of the component which created this event
	 * 
	 * @param creator: name of the component
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * Returns the name of the component which fiered this event.
	 * 
	 * @return name of gunner
	 */
	public String getGunner() {
		return gunner;
	}

	/**
	 * To set the name of the component which fired this event
	 * 
	 * @param gunner: name of component
	 */
	public void setGunner(String gunner) {
		this.gunner = gunner;
	}

	/**
	 * To set the id
	 * 
	 * @param id
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
		
	}

	/**
	 * To set the date
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
		
	}

	/**
	 * @return the creatorMethod
	 */
	public String getCreatorMethod() {
		return creatorMethod;
	}

	/**
	 * @param creatorMethod the creatorMethod to set
	 */
	public void setCreatorMethod(String creatorMethod) {
		this.creatorMethod = creatorMethod;
	}
	
	
}
