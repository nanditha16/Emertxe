package com.vending.machine;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import com.vending.machine.exception.NotFullPaidException;
import com.vending.machine.exception.NotSufficientChangeException;
import com.vending.machine.exception.SoldOutException;
import com.vending.machine.factory.VendingMachineFactory;
import com.vending.machine.model.Bucket;
import com.vending.machine.model.Coin;
import com.vending.machine.model.Item;
import com.vending.machine.service.VendingMachine;
import com.vending.machine.service.VendingMachineImpl;

/*
 * 1. buying items with exact change
 * 2. with more change
 * 3. less change
 * 4. canceling an item
 * 5. resetting vending machine
 * 6. When the machine has no item
 * 7. When the machine has lesser change
 * 
 */


/**
 * Unit test for simple VendingMachine.
 */
public class VendingMachineTest {
   
    private static VendingMachine vm;
    
    
    @BeforeClass 
    public static void setUp(){ 
    	vm = VendingMachineFactory.createVendingMachine(); 
    }
   
    @AfterClass 
    public static void tearDown(){ 
    	vm = null; 
    }

    @Test
    public void testBuyItemWithExactPrice()  { 
    	//select item, price in cents 
    	long price = vm.selectItemAndGetPrice(Item.COKE); 
    	
    	//price should be Coke's price 
    	assertEquals(Item.COKE.getPrice(), price); 
    	
    	//25 cents paid 
    	vm.insertCoin(Coin.QUARTER);
    	
    	Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange(); 
    	Item item = bucket.getFirst(); 
    	List<Coin> change = bucket.getSecond(); 
    	
    	//should be Coke 
    	assertEquals(Item.COKE, item); 
    	
    	//there should not be any change 
    	assertTrue(change.isEmpty()); 
    }
    
    @Test
    public void testBuyItemWithMorePrice()  { 
    	//select item, price in cents 
    	long price = vm.selectItemAndGetPrice(Item.SODA); 
    	
    	//price should be SODA's price 
    	assertEquals(Item.SODA.getPrice(), price); 
    	
    	//50 cents paid 
    	vm.insertCoin(Coin.QUARTER);
    	vm.insertCoin(Coin.QUARTER);
    	
    	Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange(); 
    	Item item = bucket.getFirst(); 
    	List<Coin> change = bucket.getSecond(); 
    	
    	//should be SODA 
    	assertEquals(Item.SODA, item); 
    	
    	//there should not be any change                                     
        assertTrue(!change.isEmpty());        
        
        //comparing change                             
        assertEquals(50 - Item.SODA.getPrice(), getTotal(change));  
    	
    }

	private long getTotal(List<Coin> change) {
		long total = 0;
		for(Coin c : change) {
			total += c.getCoinVal();
		}
		return total;
	}

	@Test(expected=NotFullPaidException.class) 
	public void testBuyItemWithLessPrice()  { 
    	//select item, price in cents 
    	long price = vm.selectItemAndGetPrice(Item.PEPSI); 
    	
    	//price should be PEPSI's price 
    	assertEquals(Item.PEPSI.getPrice(), price); 
    	
    	//25 cents paid 
    	vm.insertCoin(Coin.QUARTER);
    
    	Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange(); 
    	
    }
	
	@Test(expected=SoldOutException.class)
	public void testReset(){ 
		VendingMachine vmachine = VendingMachineFactory.createVendingMachine(); 
		vmachine.reset(); 
		vmachine.selectItemAndGetPrice(Item.COKE);
	}

	@Test(expected=NotSufficientChangeException.class) 
	public void testNotSufficientChangeException(){ 
		for (int i = 0; i < 5; i++) { 
			vm.selectItemAndGetPrice(Item.SODA); 
			vm.insertCoin(Coin.QUARTER); 
			vm.insertCoin(Coin.QUARTER); 
			vm.collectItemAndChange(); 
			vm.selectItemAndGetPrice(Item.PEPSI); 
			vm.insertCoin(Coin.QUARTER); 
			vm.insertCoin(Coin.QUARTER); 
			vm.collectItemAndChange(); 
		} 
	}

	@Test(expected=SoldOutException.class) 
	public void testSoldOut(){ 
		for (int i = 0; i <= 5; i++) { 
			vm.selectItemAndGetPrice(Item.COKE); 
			vm.insertCoin(Coin.QUARTER); 
			vm.collectItemAndChange(); 
		} 
	}

	@Ignore 
	public void testVendingMachineImpl(){ 
		VendingMachineImpl vm = new VendingMachineImpl(); 
	}

		
}
