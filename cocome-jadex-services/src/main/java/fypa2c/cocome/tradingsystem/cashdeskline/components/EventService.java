package fypa2c.cocome.tradingsystem.cashdeskline.components;

import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;

public class EventService {

	public IFuture<Void> publishEvent(IEvent event){
		//TODO implement sending of events
		return Future.DONE;
	}

	public IFuture<Void> receiveEvent(IEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
}
