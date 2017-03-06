package fypa2c.cocome.tradingsystem.cashdeskline.components.printerController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

/**
 * This class implements the provided services of the PrinterControllerAgent. 
 *
 * @author Florian Abt
 */
@Service
public class PrinterControllerService implements IPrinterControllerService
{
	@ServiceComponent
	protected PrinterControllerAgent component;
}
