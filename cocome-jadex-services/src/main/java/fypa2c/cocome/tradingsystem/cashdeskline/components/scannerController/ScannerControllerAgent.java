package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
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
 * This agent represents the product scanner of a cash desk in the trading system.
 * It scans the bar codes of the products.
 *
 * @author Florian Abt
 */
@Agent
@ProvidedServices({
	@ProvidedService(type=IScannerControllerService.class, implementation=@Implementation(ScannerControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="cmsservice", type=IComponentManagementService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
public class ScannerControllerAgent
{
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		return Future.DONE;
	}

}
