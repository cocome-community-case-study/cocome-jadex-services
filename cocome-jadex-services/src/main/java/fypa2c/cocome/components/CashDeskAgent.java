package fypa2c.cocome.components;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

/**
 *  Agent offering a display service.
 */
@Description("Agent offering a display service.")
@ProvidedServices({
	@ProvidedService(type=ICashDeskService.class, implementation=@Implementation(CashDeskService.class))//,
})
@RequiredServices({
	@RequiredService(name="cmsservice", type=IComponentManagementService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
})
@Agent
public class CashDeskAgent
{
	//-------- attributes --------
	
	/** The component. */
	@Agent
	protected IInternalAccess agent;
	
	
	//-------- MicroAgent methods --------
	
	/**
	 *  Called once after agent creation.
	 */
	@AgentCreated
	public IFuture<Void> creation()
	{
				
		return Future.DONE;
	}

}
