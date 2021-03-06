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
 * Event emitted by cash desk after accepting the payment mode the cashier
 * indicated.
 * 
 * @see CashBoxModel
 */
public final class PaymentModeSelectedEvent extends Event {

	private  PaymentMode mode;

	//
	public PaymentModeSelectedEvent(){
		super();
	}

	public PaymentModeSelectedEvent( PaymentMode mode, String creator) {
		super(creator);
		
		this.mode = mode;
	}

	public PaymentMode getMode() {
		return this.mode;
	}
	
	public void setMode(PaymentMode mode){
		this.mode = mode;
	}

}
