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
		StockItemTO result = null;
		try {
			FileReader in = new FileReader("StockItems.txt");
		    BufferedReader br = new BufferedReader(in);
		    
		    //to jump over the first line of column names
		    br.readLine();
		    String line;
		    while((line = br.readLine()) != null) {
		    	String[] product = line.split(";");
		    	if(Long.parseLong(product[0]) == barcode) {
		    		result = new StockItemTO(Long.parseLong(product[0]), product[1], Double.parseDouble(product[2]), Long.parseLong(product[3]), Long.parseLong(product[4]), Long.parseLong(product[5]));
		    		break;
		    	}
		    	//TODO extract product Info
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
