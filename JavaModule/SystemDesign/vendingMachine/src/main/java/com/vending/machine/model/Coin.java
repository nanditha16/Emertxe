package com.vending.machine.model;

/*
 * Accepts coins of 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.
 */
public enum Coin {
	PENNY(1), NICKEL(5), DIME(10), QUARTER(25);
	
	private int coinVal;
	
	private Coin(int coinVal) {
		this.coinVal = coinVal;
	}

	public int getCoinVal() {
		return coinVal;
	}


	
}
