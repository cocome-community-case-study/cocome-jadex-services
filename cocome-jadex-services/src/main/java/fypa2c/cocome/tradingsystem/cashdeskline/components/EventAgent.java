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
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * Superclass of all agents. 
 * It implements a method to publish an event using the EventBus publish service. 
 *
 * @author Florian Abt
 */
@Agent
@RequiredServices({
	@RequiredService(name="eventBus", type=IEventBusService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class EventAgent {
	
	private TestGUI gui;

	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	/**
	 * Publish an event on the EventBus in the cash desk channel.
	 * @param event : The event to publish
	 */
	public IFuture<Void> publishEvent(IEvent event){
		((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).publishEvent(event);
		
		return Future.DONE;
	}
	
	/**
	 * Create and get test GUI to test the sending and receiving of events
	 * @param title : title of the Frame
	 * @param events : events which can be send
	 * @return the TestGUI
	 */
	public TestGUI createTestGUI(String title, IEvent[] events){
		gui = new TestGUI(title, events);
		return gui;
	}
	
	
	/**
	 * Test class which creates a GUI to test the sending and receiving of events 
	 * between the different components.
	 *
	 * @author Florian Abt
	 */
	public class TestGUI{
		
		private JFrame frame;
		
		private JButton[] buttons;
		
		private JPanel gridPanel;
		
		public TestGUI(String title, IEvent[] events){
			frame = new JFrame(title);
			int wigth = 300;
			frame.setSize(wigth, 200);
			frame.getContentPane().setLayout(new BorderLayout());
			buttons = new JButton[events.length];
			gridPanel = new JPanel();
			gridPanel.setLayout(new GridLayout(events.length, 1));
			for(int i=0; i<events.length; i++){
				String[] name = events[i].getClass().getName().split("\\.");
				buttons[i] = new JButton("send "+name[name.length-1]);
				gridPanel.add(buttons[i]);
			}
			frame.getContentPane().add(gridPanel, BorderLayout.NORTH);
			frame.setVisible(true);
		}
		
		public JButton[] getButtons(){
			return buttons;
		}
	}
}
