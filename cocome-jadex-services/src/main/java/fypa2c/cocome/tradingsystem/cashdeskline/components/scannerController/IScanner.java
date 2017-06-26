package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

import java.awt.event.ActionListener;

/**
 * This interface specifies the callable methods of the Scanner. 
 * The ScannercontrollerAgent uses this methods to deliver an action listener for scanned barcodes.
 * A scanner should be disabled at the beginning as default.
 *
 * @author Florian Abt
 */
public interface IScanner {
	
	/**
	 * Set an ActionListener with the code being executed if a barcode is scanned
	 *  
	 * @param aListener: ActionListener for barcode scanned event
	 */
	public void setActionListenerBarCodeScanned(ActionListener aListener);
	
	/**
	 * To start the scan process. The scanner is then ready for scan. 
	 * You can scan as much Products as you want until the endScanProcess is called.
	 * Before calling this method it is not possible to scan a product with the scanner.
	 * Its necessary to set the ActionListener via setActionListenerBarCodeScanned() first. 
	 */
	public void startScanProcess();
	
	/**
	 * To stop the scan process. The scanner is then not ready for scan 
	 * and its not possible to scan another product.
	 */
	public void stopScanProcess();
	
	/**
	 * Call this if an AcitonEvent occurs via the ActionListener to get the scanned barcode.
	 * @return the barcode which was scanned, of -1 if nothing was scanned.
	 */
	public long getScannedBarCode();
	
}
