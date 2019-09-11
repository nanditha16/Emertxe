package com.vending.machine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vending.machine.exception.NotFullPaidException;
import com.vending.machine.exception.NotSufficientChangeException;
import com.vending.machine.exception.SoldOutException;
import com.vending.machine.factory.Inventory;
import com.vending.machine.model.Bucket;
import com.vending.machine.model.Coin;
import com.vending.machine.model.Item;

/**
 * Pre-req. The data models can be Coin and Item enums that we deal with in Vending machine
 * 		Coin - coinType, and getCoinVal
 * 		Item - itemName, and getPrice
 * 		Inventory - generic Map to 
 * 1. Initialize the inventory Map of object and it's size
 * 		cashInventory has Coin enum with it's count (init 5)
 * 		itemInventory has item enum with it's count (init 5)
 * 
 * 2. Have a class constant variables used during the process
 * 		currentBalance
 * 		currentItem
 * 		totalSales
 * 
 * 3. Actions implementations:
 * 	 	1. 	public void insertCoin(Coin coin)
 * 			 : update currentBalance and cashInventory with latest value
 *  	2. 	public long selectItemAndGetPrice(Item item) -> 
 *  		 : check if inventory has the said item throws 
 *  		 	if true - update currentItem and return it's price
 *  			else - throw new SoldOutException 
 *  	3. 	Bucket<Item, List<Coin>> collectItemAndChange()
 *  		 : item = collectItem
 *  		 : update totalSales with collectItem's price added
 *  		 : change = collectChange
 *  		 : return new Bucket<Item, List<Coin>>(item, change)
 *  
 *  	4. 	public List<Coin> refund() - throws NotSufficientChangeException
 *  		 : getChange from the cashInventory without deducting 
 *  		 : updateCashInventory by deducting that refund coins
 *  			private void updateCashInventory(List<Coin> change)
 *  		 : reset currentBalance = 0 and currentItem and null
 *  	5. 	public void reset() {
 *			 : clear cashInventory and itemInventory
 *       	 : totalSales = 0; 
 *      	 : reset currentBalance = 0 and currentItem and null
 *
 *		6.  public void printStats()
 *			 : print totalSales, itemInventory and cashInventory
 *  
 *  	7.  public long getTotalSales()
 *  		 : return totalSales
 *  
 *  	Helper private classes:
 *  	1. private Item collectItem() throws NotSufficientChangeException, NotFullPaidException
 *  			: check private boolean isFullPaid()
 *  				 - if currentBalance >= currentItem's price, then true else false
 *  			: if true: check private boolean hasSufficientChange()
 *  				- true if for hasSufficientChangeForAmount (currentBalance - currentItem's price ) is true
 *  				: private boolean hasSufficientChangeForAmount(long amount) 
 *					- if getChange(currentBalance - currentItem's price), throws NotSufficientChangeException return false else true
 *						: if true: deduct currentItem from itemInventory and return currentItem
 *							else : throw NotSufficientChangeException
 *				: if false : throw NotFullPaidException with remainingBalance = currentItem'Price - currentBalance;
 *	
 *		2. private List<Coin> collectChange()
 *				: change = getChange(currentBalance - currentItem.getPrice());
 *				: updateCashInventory(change)
 *				: reset currentBalance = 0 and currentItem and null
 *				: return change
 *
 *		3. private void updateCashInventory(List<Coin> change)
 *				: deduct change from  cashInventory for every coinType
 *
 *  	4. private List<Coin> getChange(long amount) throws NotSufficientChangeException	
 *  			: return List<Coin> changes such that for every coin denominations applicable
 *  		check for each coinType in desc order if 
 *  			a) the balance initially = amount is >= that coin's value
 *  			b) and if cashInventory has that coin
 *  			c) and if CHANGE_QUARTER initially = 0 is < getQuantity present in that cashInventory for the given coin
 *  				if true: 
 *  						add the coin to change, 
 *  						reduce the balance, 
 *  						increase the CHANGE_QUARTER i.e count of that coin
 *  						continue;
 *  				else:
 *  					throw new NotSufficientChangeException		
 *  				
 * 		
 *
 */
public class VendingMachineImpl implements VendingMachine
{
	
	private Inventory<Coin> cashInventory = new Inventory<Coin>();
	private Inventory<Item> itemInventory = new Inventory<Item>();
	
	private long totalSales;
	private Item currentItem;
    private long currentBalance; 
	
	public VendingMachineImpl() {
		 initialize();
	}
   
	/*
	 * Initial Assumptions:
	 * 1. Initially the vending machine will have 5 coins of each coinVal
	 * 2. Initially the vending machine will have 5 types of Items
	 * 
	 */
	private void initialize() {
		for(Coin c: Coin.values()) {
			cashInventory.put(c, 5);
		}
		
		for(Item i : Item.values()) {
			itemInventory.put(i, 5);
		}
	}
    
    public void insertCoin(Coin coin) {
    	currentBalance += coin.getCoinVal();
    	cashInventory.add(coin);
	}

	public long selectItemAndGetPrice(Item item) {
		if(itemInventory.hasProduct(item)) {
			currentItem = item;
			return currentItem.getPrice();
			
		}
		throw new SoldOutException("Sold Out. Please wait for refill.");
	}

	public List<Coin> refund() {
		List<Coin> refund = getChange(currentBalance);
		updateCashInventory(refund);
		currentBalance = 0;
        currentItem = null;
		return refund;
	}

	public Bucket<Item, List<Coin>> collectItemAndChange() {
		Item item = collectItem();
		totalSales += currentItem.getPrice();
		List<Coin> change = collectChange();
		
		return new Bucket<Item, List<Coin>>(item, change);
	}

	public void reset() {
		cashInventory.clear();
        itemInventory.clear();
        totalSales = 0; 
        currentItem = null; 
        currentBalance = 0;
       
	}
	
	public void printStats() {
		System.out.println("Total sales : " + totalSales);
		System.out.println("Current Item Inventory : " + itemInventory);
		System.out.println("Current Cash Inventory : " + cashInventory);
		
	}
	
	public long getTotalSales() {
		return totalSales;
	}
	
	private List<Coin> collectChange() {
		long changeAmount = currentBalance - currentItem.getPrice();
		List<Coin> change = getChange(changeAmount);
		updateCashInventory(change);
		currentBalance = 0;
		currentItem = null;
		return change;
	}

	private void updateCashInventory(List<Coin> change) {
		for(Coin c : change){
            cashInventory.deduct(c);
        }
	}
	

	private Item collectItem() throws NotSufficientChangeException, NotFullPaidException {
		if(isFullPaid()){
			if(hasSufficientChange()) {
				itemInventory.deduct(currentItem);
				return currentItem;
				
			}
			throw new NotSufficientChangeException("Not Sufficient Change. "
					+ "Please try a differnt product.");
			
		}
		long remainingBalance = currentItem.getPrice() - currentBalance;
        throw new NotFullPaidException("Price not full paid, remaining : ", remainingBalance);

	}

	private boolean hasSufficientChange() {
		return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
	}

	private boolean hasSufficientChangeForAmount(long amount) {
		boolean hasChange = true;
		try {
			getChange(amount);
		} catch(NotSufficientChangeException nsce) {
			return hasChange = false;
		}
		return hasChange;
	}

	/*
	 * RECHECK
	 */
	private List<Coin> getChange(long amount) throws NotSufficientChangeException {
		
		List<Coin> changes = Collections.emptyList();
		//to consider the coin quantity
		int CHANGE_QUARTER = 0; 
		int CHANGE_PENNY = 0; 
		int CHANGE_NICKEL = 0; 
		int CHANGE_DIME = 0; 
	
		if(amount > 0){
			changes = new ArrayList<Coin>();
			long balance = amount;
			while(balance > 0){
				if(balance >= Coin.QUARTER.getCoinVal() 
                        && cashInventory.hasProduct(Coin.QUARTER) && CHANGE_QUARTER < cashInventory.getQuantity(Coin.QUARTER) ) {
					changes.add(Coin.QUARTER);
					balance -= Coin.QUARTER.getCoinVal();
					CHANGE_QUARTER++;
					continue;
				} else if (balance >= Coin.DIME.getCoinVal()
						&& cashInventory.hasProduct(Coin.DIME) && CHANGE_DIME < cashInventory.getQuantity(Coin.DIME) ) {
					changes.add(Coin.DIME);
					balance -= Coin.DIME.getCoinVal();
					CHANGE_DIME++;
					continue;
				} else if (balance >= Coin.NICKEL.getCoinVal()
						&& cashInventory.hasProduct(Coin.NICKEL) && CHANGE_NICKEL < cashInventory.getQuantity(Coin.NICKEL) ) {
					changes.add(Coin.NICKEL);
					balance -= Coin.NICKEL.getCoinVal();
					CHANGE_NICKEL++;
					continue;
				 }else if (balance >= Coin.PENNY.getCoinVal()
						&& cashInventory.hasProduct(Coin.PENNY)  && CHANGE_PENNY < cashInventory.getQuantity(Coin.PENNY)) {
					changes.add(Coin.PENNY);
					balance -= Coin.PENNY.getCoinVal();
					CHANGE_PENNY++;
					continue;
				} else {
					throw new NotSufficientChangeException("Not Sufficient Change. "
							+ "Please try a different product.");
				}
			}
		}
		return changes;
		
	}

	private boolean isFullPaid() {
		if(currentBalance >= currentItem.getPrice()) {
			return true;
		}
		return false;
	}
    	    
	    
}
