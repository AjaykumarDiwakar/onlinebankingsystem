package com.codevives.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.codevives.onlinebankingsystem.databaseconnection.DatabaseConnection;
import com.codevives.onlinebankingsystem.entity.Customer;
import com.codevives.onlinebankingsystem.exception.CustomerException;

public class CustomerDaoImplementation implements CustomerDao {

	@Override
	public Customer LoginCustomer(String customerUserName, String customerPassword, int customerAccountNumber)
			throws CustomerException {
		// TODO Auto-generated method stub
		Customer customer=null;
		
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select * from customerinformation i inner join account a on i.cid=a.cid where customerName=? and customerPassword=? and customerAccountNumber=?");
			ps.setString(1, customerUserName);
			ps.setString(2, customerPassword);
			ps.setInt(3, customerAccountNumber);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				int a=rs.getInt("customerAccountNumber");
				String n=rs.getString("customerName");
				int b=rs.getInt("customerBalance");
				String e=rs.getString("customerMail");
				String p=rs.getString("customerPassword");
				String m=rs.getString("customerMobile");
				String ad=rs.getString("customerAddress");
				customer=new Customer(a,n,b,p,e,m,ad);
			}else {
				throw new CustomerException("Invalid Customer Name and Password !!!! Please Try Again !!!!");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public int viewBalance(int customerAccountNumber) throws CustomerException {
		// TODO Auto-generated method stub
		int b=-1;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select customerBalance from account where customerAccountNumber= ?");
			ps.setInt(1, customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				b=rs.getInt("customerBalance");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomerException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean Deposit(int customerAccountNumber, int amount) throws CustomerException {
		// TODO Auto-generated method stub
		if(amount<0) {
			throw new CustomerException("Amount cannot be negative");
		}
		
		int x=-1;
		int finalAmount=0;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select customerBalance from account where customerAccountNumber=?");
			ps.setInt(1,customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int currAmount=rs.getInt("customerBalance");
				finalAmount=currAmount+amount;
				PreparedStatement ps1=connection.prepareStatement("update account set customerBalance=? where  customerAccountNumber=?");
				ps1.setInt(1,finalAmount);
				ps1.setInt(2, customerAccountNumber);
				x=ps1.executeUpdate();
				if(x>0) {
					System.out.println("Updated Successfully......................................");
					return true;
				}else {
					System.out.println("Not Updatation.............................................");
					return false;
				}
				
			}
			else {
				System.out.println("------------------- No Such Account Exist........................");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean withdraw(int customerAccountNumber, int amount) throws CustomerException {
		// TODO Auto-generated method stub
		if(amount<0) {
			throw new CustomerException("Amount cannot be negative");
		}
		int x=-1;
		int finalAmount=0;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select customerBalance from account where customerAccountNumber=?");
			ps.setInt(1,customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int currAmount=rs.getInt("customerBalance");
				if(currAmount<amount) {
					throw new CustomerException("Not Sufficient Balance");
				}
				finalAmount=currAmount-amount;
				PreparedStatement ps1=connection.prepareStatement("update account set customerBalance=? where  customerAccountNumber=?");
				ps1.setInt(1,finalAmount);
				ps1.setInt(2, customerAccountNumber);
				x=ps1.executeUpdate();
				if(x>0) {
					System.out.println("Withdraw Successfully......................................");
					return true;
				}else {
					System.out.println("Not Updatation.............................................");
					return false;
				}
				
			}
			else {
				System.out.println("------------------- No Such Account Exist........................");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void transfer(int senderAccNumb, int recAccNumb, int amount) throws CustomerException {
		// TODO Auto-generated method stub
		try {
			
			boolean w=withdraw(senderAccNumb, amount);
			boolean d=false;
			if(w) {
				d=Deposit(recAccNumb, amount);
				if(d) {
					System.out.println("Balance of Sender: "+viewBalance(senderAccNumb));
					System.out.println("Balance of receiver: "+viewBalance(recAccNumb));
					return;
				}else {
					Deposit(senderAccNumb, amount);
					
					System.out.println("Balance of Sender: "+viewBalance(senderAccNumb));
					return;
				}
			}
			else {
				System.out.println("Request cannot be served");
				return;
			}
			
//			boolean d=Deposit(recAccNumb, amount);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
