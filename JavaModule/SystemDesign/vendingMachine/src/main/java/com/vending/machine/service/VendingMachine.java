package com.vending.machine.service;

import java.util.List;

import com.vending.machine.model.Bucket;
import com.vending.machine.model.Coin;
import com.vending.machine.model.Item;

/*
 * public API for Vending Machine
 * 
 * design a Vending Machine which
 * REQUIREMENT:
 *  Accepts coins of 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.
 *  Allow user to select products Coke(25), Pepsi(35), Soda(45)
 *  Allow user to take refund by canceling the request.
 *  Return selected product and remaining change if any
 *  Allow reset operation for vending machine supplier.
 *  Read more: https://javarevisited.blogspot.com/2016/06/design-vending-machine-in-java.html#ixzz5ynHGnHAd
 *  
 * ASSUMTIONS:
 * 	1. Initially the vending machine will have 5 coins of each coinVal
 * 	2. Initially the vending machine will have 5 types of Items
 * 
 * CONSTRAINTS:
 * 	1. If there is no such item - SoldOutException
 *  2. If the inventory doesn't have sufficient change - NotSufficientChangeException
 *  3. If the insertedCoin is less than the price of the item - NotFullPaidException
 *  
 * GAP:
 * 	1. Inventory to hold number of coins of different coinVal
 *  2. Inventory to hold number of items of different itemName
 *  3. actions on Inventory: 
 *  	add
 *  	deduct
 *  	hasProduct
 *  	getQuantity
 *  	put
 *  	clear
 *  
 *  BUSINESS LOGIC - actions to be implemented:
 *  1. 	insertCoin
 *  2. 	selectItemAndGetPrice -> 
 *  3. 	collectItemAndChange
 *  4. 	refund
 *  5. 	reset
 */
public interface VendingMachine {

	public void insertCoin(Coin coin);
	public long selectItemAndGetPrice(Item item);
	public List<Coin> refund();
	public Bucket<Item, List<Coin>> collectItemAndChange();
	public void reset();
	
}
