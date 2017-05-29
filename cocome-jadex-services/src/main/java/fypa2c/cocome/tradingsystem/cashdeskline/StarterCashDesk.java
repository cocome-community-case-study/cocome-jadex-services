package fypa2c.cocome.tradingsystem.cashdeskline;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController.CardReaderControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.CashBoxControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication.CashDeskApplicationAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.CashDeskGUIAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.EventBusAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.lightDisplayController.LightDisplayControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.printerController.PrinterControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.ScannerControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.simulationController.SimulationControllerAgent;
import jadex.base.PlatformConfiguration;
import jadex.base.Starter;

/**
 * This is a test class to start and test the Jadex platform with CoCoME
 * @author Flo
 *
 */
public class StarterCashDesk {

	public static void main(String[] args) {
		PlatformConfiguration config = PlatformConfiguration.getDefault();
		
		config.addComponent(EventBusAgent.class);
		config.addComponent(CashBoxControllerAgent.class);
		config.addComponent(CashDeskApplicationAgent.class);
		config.addComponent(CardReaderControllerAgent.class);
		config.addComponent(CashDeskGUIAgent.class);
		config.addComponent(LightDisplayControllerAgent.class);
		config.addComponent(PrinterControllerAgent.class);
		config.addComponent(ScannerControllerAgent.class);
		config.addComponent(SimulationControllerAgent.class);
		Starter.createPlatform(config).get();
		config.setDebugFutures(true);
	}

}
