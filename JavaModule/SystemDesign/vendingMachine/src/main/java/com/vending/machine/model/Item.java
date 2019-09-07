package com.vending.machine.model;

/*
 * Allow user to select products Coke(25), Pepsi(35), Soda(45)
 */
public enum Item {
	COKE("Coke", 25), PEPSI("Pepsi", 35), SODA("Soda", 45);
	
	private String itemName;
	private long price;
	
	private Item(String itemName, long price) {
		this.itemName = itemName;
		this.price = price;
	}
	
	public long getPrice() {
		return price;
	}

	public String getItemName() {
		return itemName;
	}

	
}
