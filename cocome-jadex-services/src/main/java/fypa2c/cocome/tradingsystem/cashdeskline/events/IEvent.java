package fypa2c.cocome.tradingsystem.cashdeskline.events;

import java.util.Date;

public interface IEvent  {
	
	/**
	 * Returns the ID of this event
	 * 
	 * @return id of the event
	 */
	public int getEventID();
	
	/**
	 * To set the id
	 * 
	 * @param id
	 */
	public void setEventID(int eventID);
	
	/**
	 * Returns the creation date of this event. 
	 * It is a time stamp of the creation.
	 * 
	 * @return date of creation of this event
	 */
	public Date getDate();
	
	/**
	 * To set the date
	 * 
	 * @param date
	 */
	public void setDate(Date date);
	
	/**
	 * Returns the name of the component which created this event
	 * 
	 * @return name of creator
	 */
	public String getCreator();
	
	/**
	 * To set the name of the component which created this event
	 * 
	 * @param creator: name of the component
	 */
	public void setCreator(String creator);
	
	/**
	 * Returns the name of the component which fiered this event.
	 * 
	 * @return name of gunner
	 */
	public String getGunner();
	
	/**
	 * To set the name of the component which fired this event
	 * 
	 * @param gunner: name of component
	 */
	public void setGunner(String gunner);
	
	/**
	 * @return the creatorMethod
	 */
	public String getCreatorMethod();

	/**
	 * @param creatorMethod the creatorMethod to set
	 */
	public void setCreatorMethod(String creatorMethod);

}
