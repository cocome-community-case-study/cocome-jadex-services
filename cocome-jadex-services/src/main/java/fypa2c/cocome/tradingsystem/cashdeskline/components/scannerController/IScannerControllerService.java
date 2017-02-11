package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import jadex.commons.future.IFuture;

public interface IScannerControllerService {

	public IFuture<Void> sendProductBarCodeScannedEvent(int barcode);
	
	
}
