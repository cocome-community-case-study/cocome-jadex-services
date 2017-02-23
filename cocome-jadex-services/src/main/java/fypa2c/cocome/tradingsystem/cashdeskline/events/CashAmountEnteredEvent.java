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
 * Event emitted by the cash box after a valid cash amount has been entered and
 * confirmed by the cashier by pressing the ENTER key.
 * 
 * @author Lubomir Bulej
 */
public final class CashAmountEnteredEvent implements IEvent, Serializable {

	private static final long serialVersionUID = -5441935251526952790L;

	//

	private final double __amount;
	
	private final boolean __finalInput;

	//

	public CashAmountEnteredEvent(final double amount, final boolean finalInput) {
		__amount = amount;
		__finalInput = finalInput;
	}

	public double getCashAmount() {
		return __amount;
	}
	
	public boolean isFinalinput(){
		return __finalInput;
	}

}