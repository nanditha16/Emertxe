package com.vending.machine.factory;

import com.vending.machine.service.VendingMachine;
import com.vending.machine.service.VendingMachineImpl;

/*
 * Factory class to create instance of Vending Machine, 
 * this can be extended to create instance of 
 * different types of vending machines.
 * 
 */
public class VendingMachineFactory {
	
	public static VendingMachine createVendingMachine() {
		return new VendingMachineImpl();
	}
}
