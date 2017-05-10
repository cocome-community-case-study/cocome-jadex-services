package fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus;

import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.bridge.service.annotation.Timeout;
import jadex.commons.IFilter;
import jadex.commons.future.IFuture;
import jadex.commons.future.ISubscriptionIntermediateFuture;

/**
 * This interface declares the provided subscription service for an event. 
 * Every cash desk has its own event bus, so that the publish/subscribe 
 * service is separated for a cash desk. 
 * 
 * @author Florian Abt
 */
//TODO Add store channel
public interface IEventBusService {
	
	/**
	 * Subscribe to all events to be notified if an event happens.
	 * @param filter : An optional filter, which listens to special events.
	 * 					  Must be null, if you want to receive all events.
	 * @return the IntermediateFuture, so the subscriber can listen to an event
	 */
	@Timeout(Timeout.NONE)
	public ISubscriptionIntermediateFuture<IEvent> subscribeToEvents(IFilter<IEvent> filter);
	
	/**
	 * Publish an event on the bus. Every component, which subscribed for the
	 * specific event is notified.
	 * @param event : The event to be published
	 */
	public IFuture<Void> publishEvent(IEvent event);
}
