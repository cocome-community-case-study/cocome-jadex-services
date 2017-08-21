package fypa2c.cocome.tradingsystem.cashdeskline.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.logging.LogEntry;
import fypa2c.cocome.tradingsystem.cashdeskline.components.logging.LogWriter;
import fypa2c.cocome.tradingsystem.cashdeskline.components.simulationController.SimulationControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.Boolean3;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
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
	
	//Log name of the agent
	private String log;
	
	//properties value of testON
	private boolean testON = false;
	
	//properties value of logON
	private boolean logON = false;

	@AgentFeature
	protected IRequiredServicesFeature requiredServicesFeature;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		//set testON value from properties, testGUI's of Agents only start if this value is true
		final Properties properties = new Properties();
		try{
			File file = new File("properties.xml");
			final FileInputStream in = new FileInputStream(file.getAbsolutePath());
			properties.loadFromXML(in);
	        in.close();
	        if(properties.getProperty("testON").equals("true")){
				testON=true;
			}
	        else{
	        	testON=false;
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("properties.xml not found, couldn't load simulationOn value - simulation component is not started yet");
		}
		
		//set logON value from properties, logging of events only if this value is true
		try{
			File file = new File("properties.xml");
			final FileInputStream in = new FileInputStream(file.getAbsolutePath());
			properties.loadFromXML(in);
	        in.close();
	        if(properties.getProperty("logON").equals("true")){
				logON=true;
			}
	        else{
	        	logON=false;
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("properties.xml not found, couldn't load logOn value - Events aren't logged yet");
		}
		
		
		
		
		return Future.DONE;
	}

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
		event.setGunner(getLog());
		logEvent(event, null);
		
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
	 * Returns the log (in general the name) of this component.
	 * 
	 * @return log
	 */
	protected String getLog() {
		return log;
	}
	
	/**
	 * Prints a log message in the console identified by the log name of the class.
	 * @param message
	 */
	public void printInfoLog(String message){
		System.out.println(log+": "+message);
	}
	
	
	
	/**
	 * Specific if the test mode is running
	 * @return true if the test mode is running
	 */
	protected boolean isTestON(){
		return testON;
	}
	
	/**
	 * Logs an Event, if the logON value (properties) is set to true
	 * 
	 * @param event: the event
	 * @param receiverName: the name of the component which receives the event, null is actual no component received
	 */
	protected void logEvent(IEvent event, String receiverName) {
		if(logON) {
			LogWriter.writeLog(new LogEntry(event.getEventID(), event.getClass().getSimpleName(), event.getGunner(), event.getCreator(), event.getCreatorMethod(), receiverName, event.getDate()),getLog());
		}
	}

}
