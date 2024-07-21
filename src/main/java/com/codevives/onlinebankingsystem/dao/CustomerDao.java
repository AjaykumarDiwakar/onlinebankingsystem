package com.codevives.onlinebankingsystem.dao;

import com.codevives.onlinebankingsystem.entity.Customer;
import com.codevives.onlinebankingsystem.exception.CustomerException;

public interface CustomerDao {
public Customer LoginCustomer(String customerUserName,String customerPassword,int customerAccountNumber) throws CustomerException;
public int viewBalance(int customerAccountNumber)throws CustomerException;
public boolean Deposit(int customerAccountNumber,int amount) throws CustomerException;
public boolean withdraw(int customerAccountNumber,int amount) throws CustomerException;
public void transfer(int senderAccNumb,int recAccNumb,int amount ) throws CustomerException;
}
