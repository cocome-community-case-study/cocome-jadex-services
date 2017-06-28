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

import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;

/**
 * Event emitted by the cash desk on the store topic when a successful sale has
 * been registered in the inventory. It contains statistical information about
 * the sale (number of items, mode of payment) intended for the cash desk line
 * coordinator.
 * 
 * @author Florian Abt
 */
public final class SaleRegisteredEvent implements IEvent {

	//

	private int itemCount;

	private PaymentMode paymentMode;

	private String cashDesk;

	//
	public SaleRegisteredEvent(){}

	public SaleRegisteredEvent(
			 String cashDesk,
			 int itemCount,  PaymentMode paymentMode) {
		this.itemCount = itemCount;
		this.paymentMode = paymentMode;
		this.cashDesk = cashDesk;
	}

	public int getItemCount() {
		return itemCount;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public String getCashDesk() {
		return cashDesk;
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setCashDesk(String cashDesk) {
		this.cashDesk = cashDesk;
	}

}
