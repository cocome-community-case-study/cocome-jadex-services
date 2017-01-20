package fypa2c.cocome.tradingsystem.cashdeskline.components.printerController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

@Service
public class PrinterControllerService implements IPrinterControllerService
{
	@ServiceComponent
	protected PrinterControllerAgent component;
}
