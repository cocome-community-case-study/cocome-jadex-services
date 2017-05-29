package fypa2c.cocome.tradingsystem.cashdeskline.components.simulationController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

/**
 * This class implements the provided services of the SimulationControllerService. 
 *
 * @author Florian Abt
 */
@Service
public class SimulationControllerService extends EventService implements ISimulationControllerService {
	
	@ServiceComponent
	protected SimulationControllerAgent component;
	
	
}
