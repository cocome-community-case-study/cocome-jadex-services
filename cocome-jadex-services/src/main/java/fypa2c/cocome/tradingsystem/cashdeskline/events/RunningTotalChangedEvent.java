/***************************************************************************
 * Copyright 2013 DFG SPP 1593 (http://dfg-spp1593.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package fypa2c.cocome.tradingsystem.cashdeskline.events;

import java.io.Serializable;

/**
 * Event emitted by the cash desk after an item has been added to the current
 * sale in response to a barcode scan. It contains information about the current
 * item, its price and the running total.
 * If the barcode equals the value -1 this event indicates the removal of the 
 * last product in the shopping card.
 * 
 * @author Florian Abt
 */
public final class RunningTotalChangedEvent extends Event {


	//-1 indicates the removal of the last product in the shopping card.
	private long barcode;

	private  String productName;

	private  double productPrice;

	private  double runningTotal;

	//
	
	public RunningTotalChangedEvent(){
		super();
	}

	public RunningTotalChangedEvent(long barcode, String productName,  double productPrice, double runningTotal, String creator) {
		super(creator);
		
		this.productName = productName;
		this.productPrice = productPrice;
		this.runningTotal = runningTotal;
		this.barcode = barcode;
	}

	public String getProductName() {
		return productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public double getRunningTotal() {
		return runningTotal;
	}
	
	public long getBarcode(){
		return barcode;
	}
	
	//setter for the Jadex Bean transmission
	
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public void setRunningTotal(double runningTotal) {
		this.runningTotal = runningTotal;
	}
	
	public void setBarcode(long barcode){
		this.barcode = barcode;
	}

	//

	@Override
	public String toString() {
		return String.format(
				"RunningTotalChangedEvent(%s, %s, %s, %s)",
				barcode, productName, productPrice, runningTotal
				);
	}

}
