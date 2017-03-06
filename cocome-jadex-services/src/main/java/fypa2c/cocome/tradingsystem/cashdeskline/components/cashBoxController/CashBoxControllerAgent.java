package fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the CashBox of a cash desk in the trading system.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(name="cashBoxController", type=ICashBoxControllerService.class, implementation=@Implementation(CashBoxControllerService.class))//,
})
public class CashBoxControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	ICashBoxControllerService providedService;
	
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICashBoxControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashBoxController");

		return Future.DONE;
	}
	
	
	public void startButtonPressed()
	{
		System.out.println("startButtonPressed() called");
		providedService.sendSaleStartedEvent();
	}
	
	/*
	 * This method is only for testing the system during development.
	 * It starts the agents and simulates a sale process.
	 */
	@AgentBody
	public void testRun()
	{
		System.out.println("testRun() called");
		IComponentStep<Void> step =  new IComponentStep<Void>() {

			@Override
			public IFuture<Void> execute(IInternalAccess ia) {
				
				startButtonPressed();
				return Future.DONE;
			}
		};
		
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		agent.getExternalAccess().scheduleStep(step);
	}

}
