package fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController;

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


/**
 * Enumerates the keys that can be pressed on the cash box keyboard.
 * 
 * @author Yannick Welsch
 * @author Lubomir Bulej
 */
public enum NumPadKeyStroke {
	ZERO("0"),
	ONE("1"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	COMMA("."),
	ENTER("ENTER"),
	SCAN("SCAN"),
	DELETE("DELETE");

	//

	private final String __label;

	//

	NumPadKeyStroke(final String label) {
		__label = label;
	}

	public String label() {
		return __label;
	}

}
