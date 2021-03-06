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

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.SaleTO;

/**
 * Event emitted by the cash desk on the store topic when a sale is finished and
 * should be registered in the inventory. It contains details of the sale so
 * that it can be accounted for in the inventory system.
 * 
 * @author Yannick Welsch, Florian Abt
 */
public final class AccountSaleEvent extends Event {


	//

	private  SaleTO sale;

	//

	public AccountSaleEvent(){
		super();
	}
	
	public AccountSaleEvent(final SaleTO sale, String creator) {
		super(creator);
		this.sale = sale;
	}

	public SaleTO getSale() {
		return sale;
	}
	
	public void setSale(SaleTO sale){
		this.sale = sale;
	}

}
