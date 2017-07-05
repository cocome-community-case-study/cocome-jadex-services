package fypa2c.cocome.tradingsystem.inventory.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.StockItemTO;

public class DummyDB implements IInventoryDB {

	@Override
	public StockItemTO getStockItemByBarcode(long barcode) {
		try {
			FileReader in = new FileReader("StockItems.txt");
		    BufferedReader br = new BufferedReader(in);
		    
		    //to jump over the first line of column names
		    br.readLine();
		    boolean found = false;
		    String line;
		    while(!found && (line = br.readLine()) != null) {
		    	line.split(";");
		    	//TODO extract product Info
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
