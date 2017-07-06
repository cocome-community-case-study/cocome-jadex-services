package fypa2c.cocome.tradingsystem.inventory;

import jadex.base.PlatformConfiguration;
import jadex.base.Starter;

public class StarterInventory {

	public static void main(String[] args) {
		PlatformConfiguration config = PlatformConfiguration.getDefault();
		
		config.addComponent(InventoryAgent.class);
		
		
		Starter.createPlatform(config).get();
		config.setDebugFutures(true);
	}
}
