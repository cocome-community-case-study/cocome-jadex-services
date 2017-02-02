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

@ProvidedServices({
	@ProvidedService(name="controller", type=ICashBoxControllerService.class, implementation=@Implementation(CashBoxControllerService.class))//,
})
@Agent
public class CashBoxControllerAgent extends EventAgent
{
	@Agent
	protected IInternalAccess agent;
	
	ICashBoxControllerService providedService;
	
//	@ProvidedService("controller")
//	protected ICashBoxControllerService cashService;
	
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICashBoxControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("controller");

		return Future.DONE;
	}
	
	public void startButtonPressed()
	{
		providedService.sendSaleStartedEvent();
	}
	
	@AgentBody
	public void testRun()
	{
		IComponentStep<Void> step =  new IComponentStep<Void>() {

			@Override
			public IFuture<Void> execute(IInternalAccess ia) {
				startButtonPressed();
				return Future.DONE;
			}
		};
		
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
		agent.getExternalAccess().scheduleStep(step);
	}

}
