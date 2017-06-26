package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import jadex.commons.future.IFuture;

/**
 * Specifies the signatures of the provided services of the ScannerControllerAgent. 
 *
 * @author Florian Abt
 */
public interface IScannerControllerService {

	/**
	 * Publish an ProductBarCodeScannedEvent to all subscriber.
	 */
	public IFuture<Void> sendProductBarCodeScannedEvent(long barcode);
	
	
}
