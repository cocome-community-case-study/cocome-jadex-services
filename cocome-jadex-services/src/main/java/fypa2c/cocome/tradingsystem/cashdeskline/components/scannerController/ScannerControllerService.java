package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.IFuture;

@Service
public class ScannerControllerService implements IScannerControllerService
{
	@ServiceComponent
	protected ScannerControllerAgent component;

	@Override
	public IFuture<Void> sendProductBarCodeScannedEvent(int barcode) {
		// TODO Auto-generated method stub
		return null;
	}
}
