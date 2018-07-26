package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.util.DBUtil;

public class WalletRepoImpl implements WalletRepo {
	private Map<String, Customer> data; 
	List<Transactions> transactions=new ArrayList<Transactions>();
	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}
	public WalletRepoImpl(){
		data=new HashMap<String,Customer>();
	}
	public boolean save(Customer customer) {
		try(Connection con=DBUtil.getConnection()){
			PreparedStatement pstm=con.prepareStatement("insert into customer values(?,?,?)");
			pstm.setString(1, customer.getMobileNo());
			System.out.println(customer.getName());
			pstm.setString(2,customer.getName());
			pstm.setBigDecimal(3, customer.getWallet().getBalance());
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return true;	
	}

	public Customer findOne(String mobileNo){
		Customer cus = null;
		try(Connection con=DBUtil.getConnection()){
		PreparedStatement pstm=con.prepareStatement("SELECT * FROM customer WHERE customer.mobileno = ?");
		pstm.setString(1, mobileNo);
		ResultSet rs= pstm.executeQuery();
		if(rs.next()!=false){
		cus = new Customer();
		cus.setMobileNo(rs.getString(1));
		cus.setName(rs.getString(2));
		Wallet wallet = new Wallet(rs.getBigDecimal(3));
		cus.setWallet(wallet);
		}
		else
			return null;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return cus;
	}
	public Customer update(Customer customer,Transactions transactionType){
		String mobileNo=customer.getMobileNo();
		BigDecimal balance = customer.getWallet().getBalance();
		try(Connection con=DBUtil.getConnection()) {
			Statement stmt= con.createStatement();
			String str="update customer set balance= '"+balance+" 'where mobileno='"+mobileNo+"'";
			stmt.execute(str);
			PreparedStatement pstm=con.prepareStatement("insert into Transactions values(?,?,?,?,?)");
			pstm.setString(1, customer.getMobileNo());
			pstm.setString(2, transactionType.getTransactionType());
			pstm.setBigDecimal(3, transactionType.getAmount());
			pstm.setString(4, transactionType.getTransactionDate());
			pstm.setString(5, transactionType.getTransactionStatus() );
			pstm.executeUpdate();
			}catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		return customer;
	}
	@Override
	public List<Transactions> Transactions(String mobileNo) {
		try(Connection con=DBUtil.getConnection()) {
			PreparedStatement pstm=con.prepareStatement("SELECT * FROM Transactions where mobileNo=?");
			pstm.setString(1, mobileNo);
			ResultSet res=pstm.executeQuery();
			if(res.next()==false) {
				throw new InvalidInputException("");
			}
			transactions.add(new Transactions(res.getString(1),res.getString(2),res.getBigDecimal(3),res.getString(4),res.getString(5)));
			while(res.next()) {
				transactions.add(new Transactions(res.getString(1),res.getString(2),res.getBigDecimal(3),res.getString(4),res.getString(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}
	

}
