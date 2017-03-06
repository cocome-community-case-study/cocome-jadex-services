package fypa2c.cocome.tradingsystem.cashdeskline;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.CashBoxControllerAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication.CashDeskApplicationAgent;
import jadex.base.PlatformConfiguration;
import jadex.base.Starter;

/**
 * This is a test class to start and test the Jadex platform with CoCoME
 * @author Flo
 *
 */
public class StarterTest {

	public static void main(String[] args) {
		PlatformConfiguration config = PlatformConfiguration.getDefaultNoGui();
		
		config.addComponent(CashBoxControllerAgent.class);
		config.addComponent(CashDeskApplicationAgent.class);
		Starter.createPlatform(config).get();
	}

}
