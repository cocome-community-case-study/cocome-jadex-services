package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import java.util.Collection;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.AgentService;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the cash desk application.  
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(name="cashDeskApplication", type=ICashDeskApplicationService.class, implementation=@Implementation(CashDeskApplicationService.class))//,
})
@RequiredServices({
	@RequiredService(name="cashBoxController", type=ICashBoxControllerService.class, multiple=false, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class CashDeskApplicationAgent extends EventAgent {

	@Agent
	protected IInternalAccess agent;
	
	@AgentFeature
	IRequiredServicesFeature requiredServicesFeature;
	
	ICashDeskApplicationService providedService;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		System.out.println("CDA created");
		providedService = (ICashDeskApplicationService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("cashDeskApplication");
		
		return Future.DONE;
	}
	
	@AgentBody
	public void subscribeToEvents(){
		System.out.println("CDA subscribes for SSE");
		ISubscriptionIntermediateFuture<IEvent> subscription = ((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).subscribeToEvent(new SaleStartedEvent());

		subscription.addIntermediateResultListener(new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void exceptionOccurred(Exception exception) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void resultAvailable(Collection<IEvent> result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				System.out.println("CashDeskApplication received the event : "+result.getClass().getName());
				//TODO receive events
				
			}
			
			@Override
			public void finished() {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
