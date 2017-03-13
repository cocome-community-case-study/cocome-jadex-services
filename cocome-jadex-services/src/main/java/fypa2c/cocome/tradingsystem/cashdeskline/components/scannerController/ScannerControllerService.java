package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

/**
 * This class implements the provided services of the ScannerControllerAgent. 
 * The send*Event methods are services which only the ScannerControllerAgent should call to publish the appropriate event. 
 * To subscribe for Events see IEventService. 
 *
 * @author Florian Abt
 */
@Service
public class ScannerControllerService extends EventService implements IScannerControllerService
{
	@ServiceComponent
	protected ScannerControllerAgent component;

	@Override
	public IFuture<Void> sendProductBarCodeScannedEvent(int barcode) {
		return component.publishEvent(new ProductBarcodeScannedEvent(barcode));
	}
}
