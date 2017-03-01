package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

@Service
public class ScannerControllerService extends EventService implements IScannerControllerService
{
	@ServiceComponent
	protected ScannerControllerAgent component;

	@Override
	public IFuture<Void> sendProductBarCodeScannedEvent(int barcode) {
		return publishEvent(new ProductBarcodeScannedEvent(barcode));
	}
}
