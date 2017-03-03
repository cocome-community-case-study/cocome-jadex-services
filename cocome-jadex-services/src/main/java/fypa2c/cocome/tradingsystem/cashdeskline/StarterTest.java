package fypa2c.cocome.tradingsystem.cashdeskline;

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.CashBoxControllerAgent;
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
		Starter.createPlatform(config).get();
	}

}
