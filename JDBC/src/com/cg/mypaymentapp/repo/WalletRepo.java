package com.cg.mypaymentapp.repo;

import java.util.List;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.exception.InvalidInputException;

public interface WalletRepo {
	
	public boolean save(Customer customer) throws InvalidInputException;
	
	public Customer findOne(String mobileNo) throws InvalidInputException;
	
	public Customer update(Customer customer,Transactions transactions) throws InvalidInputException;
	
	public List<Transactions> Transactions(String mobileNo) throws InvalidInputException;
}
