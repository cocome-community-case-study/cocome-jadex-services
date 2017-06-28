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
 * Event emitted by the credit card reader when a credit card information has
 * been successfully scanned.
 * <p>
 * TODO Credit card information should be a type (class).
 * 
 * @see CardReaderModel
 * 
 * @author Sebastian Herold
 */
public final class CreditCardScannedEvent implements IEvent {

	//

	private String creditCardInformation;

	//
	
	public CreditCardScannedEvent(){}

	public CreditCardScannedEvent(String creditCardInformation) {
		this.creditCardInformation = creditCardInformation;
	}

	public String getCreditCardInformation() {
		return creditCardInformation;
	}
	
	public void setCreditCardInformation(String creditCardInformation){
		this.creditCardInformation = creditCardInformation;
	}

}
