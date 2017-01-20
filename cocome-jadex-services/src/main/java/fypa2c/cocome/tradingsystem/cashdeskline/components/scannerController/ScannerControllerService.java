package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class ScannerControllerService implements IScannerControllerService
{
	@ServiceComponent
	protected ScannerControllerAgent component;
}
