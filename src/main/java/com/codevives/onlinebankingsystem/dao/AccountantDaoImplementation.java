package com.codevives.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.codevives.onlinebankingsystem.databaseconnection.DatabaseConnection;
import com.codevives.onlinebankingsystem.entity.Accountant;
import com.codevives.onlinebankingsystem.entity.Customer;
import com.codevives.onlinebankingsystem.exception.AccountantException;
import com.codevives.onlinebankingsystem.exception.CustomerException;

public class AccountantDaoImplementation implements AccountantDao{

	@Override
	public Accountant LoginAccountant(String accountantUsername, String accountantPassword) throws AccountantException {
		// TODO Auto-generated method stub
		Accountant accountant=null;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select * from accountant where accountantUsername=? and accountantPassword=?");
			ps.setString(1, accountantUsername);
			ps.setString(2, accountantPassword);
	
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				String name=rs.getString("accountantUsername");
				String email=rs.getString("accountantEmail");
				String password=rs.getString("accountantPassword");
				accountant=new Accountant(name,email,password);
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			throw new AccountantException("Invalid UserName and Password");
		}
		return accountant;
	}

	@Override
	public int addCustomer(String customerName, String customerMail, String customerPassword, String customerMobile,
			String customerAddress) throws CustomerException {
		// TODO Auto-generated method stub
		int cid=-1;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("insert into customerinformation (customerName,customerMail,customerPassword,customerMobile,customerAddress) values(?,?,?,?,?)");
			ps.setString(1,customerName);
			ps.setString(2,customerMail);
			ps.setString(3,customerPassword);
			ps.setString(4,customerMobile);
			ps.setString(5,customerAddress);
			
			int x=ps.executeUpdate();
			if(x>0) {
//				System.out.println("Customer Added Succesfully");
				PreparedStatement ps2=connection.prepareStatement("select cid from customerinformation where customerName=? and customerMobile=?");
				ps2.setString(1, customerName);
				ps2.setString(2, customerMobile);
				
				ResultSet rs=ps2.executeQuery();
				if(rs.next()) {
					cid=rs.getInt("cid");
				}else {
					System.out.println("Inserted Data is Incorrect Please Try again");
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
//			System.out.println("Customer NOT Added Successfully");
//			System.out.println("Some Exception occur");
			e.printStackTrace();
		}
		return cid;
	}

	@Override
	public String addAccount(int customerBalance, int cid) throws CustomerException {
		// TODO Auto-generated method stub
		String message=null;
		try {
//			System.out.println(cid);
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("insert into account(customerBalance,cid) values(?,?)");
			ps.setInt(1,customerBalance);
			ps.setInt(2, cid);
			int x=ps.executeUpdate();
			
			if(x>0) {
				System.out.println("Account added Successfully");
				PreparedStatement ps2=connection.prepareStatement("select * from account where cid=?");
				ps2.setInt(1, cid);
				ResultSet num=ps2.executeQuery();
				if(num.next()) {
					System.out.println("Account Number is : "+num.getInt("customerAccountNumber"));
				}
			}else {
				System.out.println("Inserted not added successfully");
			}
		}catch(SQLException e) {
			System.out.println("SQL related Exception");
//			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String updateCustomer(int customerAccountNumber, String customerAddress) throws CustomerException {
		// TODO Auto-generated method stub
		String message=null;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("update customerinformation i inner join account a on i.cid=a.cid set i.customerAddress=? where a.customerAccountNumber= ?");
			ps.setString(1, customerAddress);
			ps.setInt(2, customerAccountNumber);
			int x=ps.executeUpdate();
			
			if(x>0) {
				System.out.println("Address updated Successfully");
			}else {
				System.out.println("Address is not Updated Successfully");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message=e.getMessage();
		}
		return message;
	}
	
	public String deleteAccount(int customerAccountNumber) throws CustomerException{
		String message=null;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("delete i from customerinformation i inner join account a on i.cid=a.cid where a.customerAccountNumber=?");
			ps.setInt(1, customerAccountNumber);
			int x=ps.executeUpdate();
			
			if(x>0) {
				System.out.println("Account deleted successfully....");
			}else {
				System.out.println("Deletion failed..................Account not found");
				System.out.println("---------------------------------------------------");
			}
		} catch (Exception e) {
			// TODO: handle exception
			message=e.getMessage();
			System.out.println("Error in delete account");
		}
		return message;
	}

	@Override
	public Customer viewCustomer(int customerAccountNumber) throws CustomerException {
		// TODO Auto-generated method stub
		Customer cu=null;
		try {
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select * from customerinformation i inner join account a on i.cid=a.cid where customerAccountNumber=?");
			ps.setInt(1, customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int a=rs.getInt("customerAccountNumber");
				String n=rs.getString("customerName");
				int b=rs.getInt("customerBalance");
				String e=rs.getString("customerMail");
				String p=rs.getString("customerPassword");
				String m=rs.getString("customerMobile");
				String ad=rs.getString("customerAddress");
				cu=new Customer(a,n,b,p,e,m,ad);
			}else {
				throw new CustomerException("Invalid Account Number");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cu;
	}

	@Override
	public Customer viewAllCustomer() throws CustomerException {
		// TODO Auto-generated method stub
		Customer customer=null;
		try {
			
			Connection connection=DatabaseConnection.provideConnection();
			PreparedStatement ps=connection.prepareStatement("select * from customerinformation i inner join account a on a.cid=i.cid");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int a=rs.getInt("customerAccountNumber");
				String n=rs.getString("customerName");
				int b=rs.getInt("customerBalance");
				String e=rs.getString("customerMail");
				String p=rs.getString("customerPassword");
				String m=rs.getString("customerMobile");
				String ad=rs.getString("customerAddress");
				customer=new Customer(a,n,b,p,e,m,ad);
				System.out.println("********************************************************************");
				System.out.println("Account Number: "+customer.getCustomerAccountNumber());
				System.out.println("Name: "+customer.getCustomerName());
				System.out.println("Balance: "+customer.getCustomerBalance());
				System.out.println("Email : "+customer.getCustomerMail());
				System.out.println("Password : "+customer.getCustomerPassword());
				System.out.println("Mobile : "+customer.getCustomerMobile());
				System.out.println("Address : "+customer.getCustomerAddress());
				System.out.println("----------------------------------------------------");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	

}
