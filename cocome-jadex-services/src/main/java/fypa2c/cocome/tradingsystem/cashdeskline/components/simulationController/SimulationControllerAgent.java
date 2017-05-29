package fypa2c.cocome.tradingsystem.cashdeskline.components.simulationController;

import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController.ICardReaderControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.commons.Boolean3;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

/**
 * This agent is a simulation module for one cashdesk. 
 * It simulates costumers using the cashdesk in different 
 * ways (cash or card payment) and for an different 
 * amount of any products. The probability distribution
 * of the different events must be defined in the simulation properties file (//TODO create simulation properties file)
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="SimulationController",type=ISimulationControllerService.class, implementation=@Implementation(SimulationControllerService.class))//,
})
public class SimulationControllerAgent extends EventAgent {

	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		setLog("SimulationControllerAgent");
		
		return Future.DONE;
	}
	
	@AgentBody
	public void body(){
		initializeSimulationGUI();
		
		subscribeToEvents();
		
	}
	
	public void initializeSimulationGUI() {
		SimulationGUI gui = new SimulationGUI();
		
		// TODO create Simulation GUI
		
	}

	/**
	 * The agent subscribes to all events.
	 */
	public void subscribeToEvents(){
		//subscribe
		ISubscriptionIntermediateFuture<IEvent> sifuture = ((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvents(null);
		
		//waiting for Events
		sifuture.addIntermediateResultListener(new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void exceptionOccurred(Exception exception) {
				printInfoLog("Exception occurred");
				exception.printStackTrace();
			}
			
			@Override
			public void resultAvailable(Collection<IEvent> result) {
				printInfoLog("Received IEvent collection");
			}
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				printInfoLog("Received "+result.getClass().getName());
			}
			
			@Override
			public void finished() {
				printInfoLog("IntermediateResult finished");
			}
		});
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ISimulationControllerService getServiceProvided()
	{
		return (ISimulationControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("SimulationController");
	}
}
