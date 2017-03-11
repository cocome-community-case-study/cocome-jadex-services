package fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus;

import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.commons.future.ISubscriptionIntermediateFuture;

/**
 * This interface declares the provided subscription service for an event. 
 * 
 * @author Florian Abt
 */
public interface IEventBusService {

	/**
	 * Subscribe to an event to be notified by the specific service.
	 * @param event : The event to be listened
	 * @return the IntermediateFuture, so the subscriber can listen to an event
	 */
	//TODO add filter
	public ISubscriptionIntermediateFuture<IEvent> subscribeToEvent(IEvent event);
}
