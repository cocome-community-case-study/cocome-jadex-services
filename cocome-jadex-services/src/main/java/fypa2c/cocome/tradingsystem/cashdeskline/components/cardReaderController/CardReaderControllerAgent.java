package fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 * This agent represents the card reader of a cash desk in the trading system.
 * It is responsible for the card reading process and the PIN entering process.
 * The information is delivered to the CashDeskApplication.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(name="controller",type=ICardReaderControllerService.class, implementation=@Implementation(CardReaderControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="cmsservice", type=IComponentManagementService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class CardReaderControllerAgent
{
	@Agent
	protected IInternalAccess agent;
	
	ICardReaderControllerService providedService;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		providedService = (ICardReaderControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("controller");
		
		return Future.DONE;
	}

}
