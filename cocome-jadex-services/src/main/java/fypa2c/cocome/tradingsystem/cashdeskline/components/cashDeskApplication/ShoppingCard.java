package fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication;

import java.util.LinkedList;

import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.ProductTO;

/**
 * This class represents a shopping card. 
 * It handles a list of all chosen products in the order of selection and the runnting total.
 * Adding, removing the last product, discarding , restoring last shopping card, 
 * checkout and getting actual list are supported.
 *
 * @author Florian Abt
 */
public class ShoppingCard {

	//actual shopping card represented by a LinkList
	private LinkedList<ProductTO> shoppingCard = new LinkedList<ProductTO>();
	
	//last shopping card, which was checked out or discarded
	private LinkedList<ProductTO> oldShoppingCard = new LinkedList<ProductTO>();
	
	//the running total of all so far selected products in the shopping card in Euro
	double runningTotal = 0;
	
	
	
	/**
	 * Removes all products from the shopping card.
	 * Before that a copy of the shopping card is made and stored in the oldShoppingCard.
	 * So it is possible to restore the last shopping card.
	 */
	public void discard(){
		oldShoppingCard = getActualShoppingCard();
		shoppingCard.clear();
		runningTotal = 0;
	}
	
	/**
	 * Restores the oldShoppingCard, which might be discarded or checked out 
	 * (by restore after check out the check out itself is not canceled).
	 * If there is no restorable shopping card nothing will be changed
	 * 
	 * @return true: if restore was successful
	 * 		   false: if there is no chopping card to restore 
	 */
	public boolean restore(){
		if(oldShoppingCard.isEmpty()){
			return false;
		}
		else{
			shoppingCard = (LinkedList<ProductTO>) oldShoppingCard.clone();
			oldShoppingCard.clear();
			runningTotal = 0;
			shoppingCard.forEach((product) -> runningTotal = runningTotal + product.getPurchasePrice());
			return true;
		}
	}
	
	/**
	 * Adds a product to the shopping card.
	 * 
	 * @param product to add
	 */
	public void addProduct(ProductTO product){
		if(product != null){
			shoppingCard.add(product);
			runningTotal = runningTotal + product.getPurchasePrice();
		}
		
	}
	
	
	/**
	 * Removes the last added product from the shopping card.
	 * An UNDO of this is not possible.
	 * 
	 * @return removedProduct: returns the removed product if there is a product to remove
	 * 		   null: if the shopping card is empty 
	 */
	public ProductTO removeLastProduct(){
		if(shoppingCard.isEmpty()){
			return null;
		}
		else{
			ProductTO removedProduct =  shoppingCard.removeLast();
			runningTotal = runningTotal - removedProduct.getPurchasePrice();
			return removedProduct;
		}
	}
	
	/**
	 * To get a copy of the list of the products actual in the shopping card. 
	 * (Each product itself is not a copy!)
	 * 
	 * @return LinkedList<ProductTO> containing all products which are actual in the shopping card.
	 */
	public LinkedList<ProductTO> getActualShoppingCard(){
		return (LinkedList<ProductTO>) shoppingCard.clone();
	}
	
	/**
	 * To checkout. This returns all chosen products as a LinkedList in the order of the shopping card. 
	 * The shopping card is empty afterwards.
	 * 
	 * @return LinkedList<ProductTO>: contains all products of the shopping card
	 * 		   null: if there is no product in the shopping card
	 */
	public LinkedList<ProductTO> checkOut(){
		if(!shoppingCard.isEmpty()){
			discard();
			return (LinkedList<ProductTO>) oldShoppingCard.clone();
		}
		else{
			return null;
		}
	}
	
	/**
	 * To get the running total of all products in the shopping card.
	 * 
	 * @return running total of all products
	 */
	public double getRunningTotal(){
		return runningTotal;
	}
	
}
