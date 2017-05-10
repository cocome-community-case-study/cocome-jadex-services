package fypa2c.cocome.tradingsystem.cashdeskline.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.Boolean3;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.AgentKilled;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * Superclass of all agents. 
 * It implements a method to publish an event using the EventBus publish service. 
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
	@RequiredService(name="cms", type=IComponentManagementService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))
})
public class EventAgent {
	
	private String log;

	@AgentFeature
	protected IRequiredServicesFeature requiredServicesFeature;

	/**
	 * This method is called before the deletion of the agent. 
	 */
	@AgentKilled
	public IFuture<Void> kill(){
		
		System.out.println(log+": Agent killed");
		
		return Future.DONE;
	}
	
	/**
	 * Publish an event on the EventBus in the cash desk channel.
	 * @param event : The event to publish
	 */
	public IFuture<Void> publishEvent(IEvent event){
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).publishEvent(event);
		
		return Future.DONE;
	}
	
	/**
	 * Sets the log name of this class.
	 * The log name is used to identify log messages in the console.
	 * @param log: name of the class, printed in the console
	 */
	protected void setLog(String log){
		this.log = log;
	}
	
	/**
	 * Prints a log message in the console identified by the log name of the class.
	 * @param message
	 */
	protected void printInfoLog(String message){
		System.out.println(log+": "+message);
	}
	

}
