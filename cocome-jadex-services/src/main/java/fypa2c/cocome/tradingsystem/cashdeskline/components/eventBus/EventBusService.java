package fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus;

import java.util.HashSet;
import java.util.Set;

import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.SFuture;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.commons.future.SubscriptionIntermediateFuture;
import jadex.commons.future.TerminationCommand;

/**
 * This service implements the publish/subscribe pattern for all services to deliver events. 
 * It provides a subscribe service for an specific event. 
 * The subscriber is notified if the event is published.
 * 
 * @author Florian Abt
 */
@Service
public class EventBusService implements IEventBusService{
	
	@ServiceComponent 
	protected IInternalAccess service;

	@ServiceComponent
	protected EventBusAgent component;
	
	//Every subscription is stored in a SubscriptionListEntry
	protected Set<SubscriptionEntry> subscriptions 
			= new HashSet<SubscriptionEntry>();
	
	@Override
	public ISubscriptionIntermediateFuture<IEvent> subscribeToEvents(IFilter<IEvent> filter) {
		component.printInfoLog("Service recieved an subscription");
		//new SubscriptionIntermediateFuture for an event
		SubscriptionIntermediateFuture<IEvent> sifuture = new SubscriptionIntermediateFuture<IEvent>();
		
		//Add the subscriber to the subscription list
		SubscriptionEntry entry = new SubscriptionEntry(filter, sifuture);
		subscriptions.add(entry);
	
		SFuture.avoidCallTimeouts((Future<?>) sifuture, service);
		
		
		//Add termination command, which is called if the subscription ends. It removes the SubscriptionEntry.
		sifuture.setTerminationCommand(new TerminationCommand() {
			
			@Override
			public void terminated(Exception reason) {
				
				reason.printStackTrace();
				subscriptions.remove(entry);
				
			}
		});
		
		return sifuture;
	}

	@Override
	public IFuture<Void> publishEvent(IEvent event) {
		//Notify the subscriber of this event.
		component.printInfoLog("Received Event and notifies subscriber");
		for(SubscriptionEntry entry : subscriptions){
			if(entry.getFilter()==null){
				entry.sifuture.addIntermediateResultIfUndone(event);
			}
			else if(entry.getFilter().filter(event)){
				entry.sifuture.addIntermediateResultIfUndone(event);
			}
		}
		
		return Future.DONE;
	}
	
	/**
	 * This class represents an entry in the SubscriberList. 
	 * It contains a set of filters for specific events and
	 *  the SubscriptionIntermediateFuture to notify the subscriber 
	 *
	 * @author Florian Abt
	 */
	public class SubscriptionEntry {
		
		private final IFilter<IEvent> filter;
		
		private final SubscriptionIntermediateFuture<IEvent> sifuture;
		
		public SubscriptionEntry(IFilter<IEvent> filter, SubscriptionIntermediateFuture<IEvent> sifuture){
			this.filter = filter;
			this.sifuture = sifuture;
		}

		/**
		 * to get the set of filter the subscriber has set
		 * @return set of filter
		 */
		public IFilter<IEvent> getFilter() {
			return filter;
		}

		/**
		 * to get the SubscriptionIntermediateFuture to notify the subscriber
		 * @return IntermediateFuture object
		 */
		public SubscriptionIntermediateFuture<IEvent> getSifuture() {
			return sifuture;
		}
		
		
	}


}
