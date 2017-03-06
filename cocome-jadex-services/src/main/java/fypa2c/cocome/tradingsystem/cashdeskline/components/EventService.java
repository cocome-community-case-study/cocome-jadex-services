package fypa2c.cocome.tradingsystem.cashdeskline.components;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import antlr.debug.NewLineEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.bridge.service.annotation.Service;
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
public class EventService implements IEventService {
	
	//Every event is mapped to set of subscriber, which want to be notified at this event
	protected Map<String, Set<SubscriptionIntermediateFuture<IEvent>>> subscriptions 
		= new HashMap<String, Set<SubscriptionIntermediateFuture<IEvent>>>();

	/**
	 * Notifies all subscriber of the event
	 * @param event : the specific event whose subscriber should be notified
	 */
	protected IFuture<Void> publishEvent(IEvent event){
		//Notify the subscriber of this event.
		if(subscriptions.containsKey(event.getClass().getName())){
			for(SubscriptionIntermediateFuture<IEvent> subscriber : subscriptions.get(event.getClass().getName())){
				subscriber.addIntermediateResultIfUndone(event);
			}
		}
		
		return Future.DONE;
	}

	protected IFuture<Void> receiveEvent(IEvent event) {
		// TODO receive events
		return null;
	}

	@Override
	public ISubscriptionIntermediateFuture<IEvent> subscribeToEvent(IEvent event) {
		System.out.println("EventService recieved an subscription");
		//new SubscriptionIntermediateFuture for an event
		final SubscriptionIntermediateFuture<IEvent> sifuture = new SubscriptionIntermediateFuture<IEvent>();
		
		//Add the subscriber to the specific set in the map, if it's the first subscriber of this event a new set must be added. 
		if(subscriptions.containsKey(event.getClass().getName())){
			subscriptions.get(event.getClass().getName()).add(sifuture);
		}
		else{
			subscriptions.put(event.getClass().getName(), new LinkedHashSet<SubscriptionIntermediateFuture<IEvent>>());
			subscriptions.get(event.getClass().getName()).add(sifuture);
		}
		
		//Add termination command, which is called if the subscription ends. It removes the SIFuture.
		sifuture.setTerminationCommand(new TerminationCommand() {
			
			@Override
			public void terminated(Exception reason) {
				
				subscriptions.get(event.getClass().getName()).remove(sifuture);
				
			}
		});
		
		return sifuture;
	}
}
