package com.cg.mypaymentapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class TestClass {

	WalletService service;

	@Before
	public void initData(){
		service= new WalletServiceImpl();
		Customer cust1=service.createAccount("Amit", "9900112212",new BigDecimal(9000));
		Customer cust2=service.createAccount("Ajay", "9963242422",new BigDecimal(6000));
		Customer cust3=service.createAccount("Yogini", "9922950519",new BigDecimal(7000));
	}

	@Test(expected = NullPointerException.class)
	public void TestcreateAccount(){
		service = new WalletServiceImpl();
		service.createAccount(null,null, null);
	}

	@Test(expected = InvalidInputException.class)
	public void TestshowBalance(){
		service = new WalletServiceImpl();
		service.showBalance(null);
	}
	@Test(expected = InvalidInputException.class)
	public void TestfundTransfer1(){
		service = new WalletServiceImpl();
		String source="9900112212";
		String target="9900112212";
		BigDecimal amount=new BigDecimal(11000);
		Customer cust=service.fundTransfer(source, target, amount);
	}

	@Test(expected=NullPointerException.class)
	public void TestfundTransfer2(){
		service = new WalletServiceImpl();
		String source="9900112212";
		String target="9963242422";
		BigDecimal amount=new BigDecimal(-10);
		Customer cust=service.fundTransfer(source, target, amount);
	}

	@Test(expected = InvalidInputException.class)
	public void TestdepositAmount2(){
		Customer cust=new Customer();
		String mobile="99001122";
		BigDecimal amount=new BigDecimal(1000);
		cust=service.depositAmount(mobile, amount);
	}

	@Test(expected = InvalidInputException.class)
	public void TestwithdrawAmount(){
		Customer cust=new Customer();
		String mobile="99001122";
		BigDecimal amount=new BigDecimal(1000);
		cust=service.withdrawAmount(mobile, amount);
	}

	@Test
	public void TestName(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		assertEquals(cust1.getName(), "Amit");
	}

	@Test
	public void Testnullname1(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer(null, "9900112212",new Wallet(new BigDecimal(11000)));
	}

	@Test
	public void Testinvalidname2(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		assertNotSame(cust1.getName(), "Ajay");
	}

	@Test
	public void Testinvalidname(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer(null, "9900112212",new Wallet(new BigDecimal(11000)));
		assertNull(cust1.getName());
	}

	@Test
	public void TestMobileNo(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(12000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		assertEquals(cust1.getMobileNo(), "9900112212");
	}

	@Test
	public void TestinvalidmobileNo(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(12000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		assertNotEquals(cust1.getMobileNo(), "99002212");
	}

	@Test
	public void TestinvalidmobileNo1(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		assertNotNull(cust1.getName());
	}

	@Test
	public void TestnullmobileNo(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", null,new Wallet(new BigDecimal(11000)));
		assertNull(cust1.getMobileNo());
	}

	@Test
	public void TestnullmobileNo1(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		assertNotNull(cust1.getMobileNo());
	}

	@Test
	public void TestnullWallet(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(null));
		assertNull(cust1.getWallet().getBalance());
	}

	@Test
	public void TestWallet(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		Wallet w1=cust1.getWallet();
		assertEquals(new BigDecimal(11000),w1.getBalance());
	}

	@Test
	public void TestWallet1(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		assertNotNull(cust1.getWallet().getBalance());
	}
	@Test
	public void TestnullWallet2(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(null));
	}	

	@Test
	public void TestWallet2(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(11000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		assertNotSame(cust1.getWallet().getBalance(), new BigDecimal(2000));
	}

	@Test
	public void TestWallet3(){
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(-11000)));
		data.put("9900112212", cust1);
		service = new WalletServiceImpl(data);
		assertNotSame(cust1.getWallet().getBalance(), new BigDecimal(2000));

	}
}

