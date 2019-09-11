package com.vending.machine.factory;

import java.util.HashMap;
import java.util.Map;

/*
 * An Adapter over Map to create Inventory to hold 
 * cash and Items 
 * inside Vending Machine
 * 
 */
public class Inventory<T> {
	private Map<T, Integer> inventory = new HashMap<T, Integer>();

	public int getQuantity(T product) {
		Integer itemCount = inventory.get(product);
		return itemCount == null? 0: itemCount;
	}
	
	public boolean hasProduct(T product) {
		return getQuantity(product) > 0;
	}
	
	public void clear(){
        inventory.clear();
    }
	
	public void put(T product, int quantity) {
		inventory.putIfAbsent(product, quantity);
	}
	
	public void add(T product) {
		int count = inventory.get(product);
		inventory.put(product, count+1);
	}
	
	public void deduct(T product) {
		if(hasProduct(product)) {
			int count = inventory.get(product);
			inventory.put(product, count-1);
		} 
	}
	
}
