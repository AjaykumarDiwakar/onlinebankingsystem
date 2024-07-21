package com.codevives.onlinebankingsystem;

import java.util.Scanner;

import com.codevives.onlinebankingsystem.dao.AccountantDao;
import com.codevives.onlinebankingsystem.dao.AccountantDaoImplementation;
import com.codevives.onlinebankingsystem.dao.CustomerDao;
import com.codevives.onlinebankingsystem.dao.CustomerDaoImplementation;
import com.codevives.onlinebankingsystem.entity.Accountant;
import com.codevives.onlinebankingsystem.entity.Customer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Scanner scanner = new Scanner(System.in);
		boolean f = true;

		while (f) {
			System.out.println("-------------------------WELCOME TO ONLINE BANKING SYSTEM---------------------");
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("1.ADMIN LOGIN PORTAL \r\n" + "2.CUSTOMER");
			System.out.println("Choose your option: ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Admin Login Credentials -------------------Accountant");
				System.out.println("Enter UserName: ");
				String username = scanner.next();
				System.out.println("Enter Password: ");
				String pass = scanner.next();

				AccountantDao ad = new AccountantDaoImplementation();
				try {
					Accountant accountant = ad.LoginAccountant(username, pass);
					if (accountant == null) {
						System.out.println("Wrong credential");
						break;
					}
					System.out.println("Login Successfully !!!");
					System.out.println("Welcome to : " + accountant.getAccountantUsername()
							+ " as Admin of online Bankingt System");

					boolean y = true;
					while (y) {
						System.out.println("--------------------------------------------------\r\n"
								+ "1.Add New Customer Account \r\n" + "2.Update Address\r\n" + "3.Delete Customer\r\n"
								+ "4.View Particular Account details by giving Account number\r\n"
								+ "5.View All Accounts/Customers\r\n" + "6.Log Out Account\r\n");

						int x = scanner.nextInt();

						if (x == 1) {
							System.out.println("------------------------New Account----------");
							System.out.println("Enter Customer Name: ");
							String a1 = scanner.next();
							System.out.println("Enter Account opening balance");
							Integer a2 = scanner.nextInt();
							System.out.println("Enter Customer Mail: ");
							String a3 = scanner.next();
							System.out.println("Enter Customer Password: ");
							String a4 = scanner.next();
							System.out.println("Enter Customer Mobile Number: ");
							String a5 = scanner.next();
							System.out.println("Enter Customer Address: ");
							String a6 = scanner.next();

							int s1 = -1;
							try {
								s1 = ad.addCustomer(a1, a3, a4, a5, a6);

								try {
									ad.addAccount(a2, s1);
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							} catch (Exception e) {
								// TODO: handle exception
								System.out.println(e.getMessage());
							}
							System.out.println("-----------------------------------------------");
						}
						if (x == 2) {
							System.out.println("Update Customer Address......");
							System.out.println("Enter Customer Account Number...");
							int u = scanner.nextInt();
							System.out.println("Enter new Address.....");
							String u2 = scanner.next();
							try {
								String mes = ad.updateCustomer(u, u2);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
						if (x == 3) {
							System.out.println("----------------Remove Account----------------------------");
							System.out.println("Enter the Account number:  ");
							int ac = scanner.nextInt();
							String s = null;
							try {
								s = ad.deleteAccount(ac);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
						if (x == 4) {
							System.out.println("--------------------Customer Details--------------------");
							System.out.println("Enter Account Number: ");
							int ac = scanner.nextInt();

							try {
								Customer customer = ad.viewCustomer(ac);
								if (customer != null) {
									System.out.println("**************************************************");
									System.out.println("Account Number: " + customer.getCustomerAccountNumber());
									System.out.println("Name: " + customer.getCustomerName());
									System.out.println("Balance: " + customer.getCustomerBalance());
									System.out.println("Email : " + customer.getCustomerMail());
									System.out.println("Password : " + customer.getCustomerPassword());
									System.out.println("Mobile : " + customer.getCustomerMobile());
									System.out.println("Address : " + customer.getCustomerAddress());
									System.out.println("----------------------------------------------------");

								} else {
									System.out.println("Account doesnot exist...");
									System.out.println("-----------------------------------------------------");
								}
							} catch (Exception e) {
								// TODO: handle exception
								System.out.println("Error at line 133 of App class");
							}
						}
						if (x == 5) {
							try {
								System.out.println("------------------------All Customer List------------------");
								Customer customer = ad.viewAllCustomer();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();

							}
						}

						if (x == 6) {
							System.out.println("---------------------------Account Logout Successfully----------");
							y = false;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;

//				CASE 2 Start-------------------------------------------------------

			case 2:
				System.out.println("--------------------Customer LogIn ------------------------------------------");
				System.out.println("------------------------------------------------------------------------------");
				System.out.println("Enter User Name: ");
				String customerUsername = scanner.next();
				System.out.println("Enter Password: ");
				String customerPassword = scanner.next();
				System.out.println("Enter Account Number: ");
				int accountNumber = scanner.nextInt();
				CustomerDao cd = new CustomerDaoImplementation();

				try {
					Customer customer = cd.LoginCustomer(customerUsername, customerPassword, accountNumber);
					System.out.println(
							"------------------- Welcome : " + customer.getCustomerName() + " -----------------------");

					boolean m = true;
					while (m) {
						System.out.println("------------------------------------------------\r\n" + "1.View Balance\r\n"
								+ "2.Deposit\r\n" + "3.Withdraw\r\n"
										+ "4.Transfer\r\n"
										+ "5.Log Out");
						int x = scanner.nextInt();

						try {

							if (x == 1) {
								System.out.println("-----------------------------------Balance--------------------");
								System.out.println("Current Account Balance---------------------------------------");
								System.out.println(cd.viewBalance(accountNumber));
								System.out.println("---------------------------------------------------------------");
							}

						} catch (Exception e) {
							// TODO: handle exception
							System.out
									.println("-----------------------" + e.getMessage() + "--------------------------");
						}
						try {

							if (x == 2) {
								System.out.println("---------------------------Deposit-----------------------------");
								System.out.println("------------Enter the amount to be deposited-------------------");
								int deposit = scanner.nextInt();
								cd.Deposit(accountNumber, deposit);
								System.out.println("Final amount : " + cd.viewBalance(accountNumber));

							}
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.getMessage());
						}
						try {

							if (x == 3) {
								System.out.println("---------------------------Withdraw-----------------------------");
								System.out.println("------------Enter the amount to be withdraw-------------------");
								int deposit = scanner.nextInt();
								cd.withdraw(accountNumber, deposit);
								System.out.println("Final amount : " + cd.viewBalance(accountNumber));

							}
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.getMessage());
						}
						
						try {

							if (x == 4) {
								System.out.println("---------------------------Transfer-----------------------------");
								System.out.println("-------------------Enter receiver Account number-----------------");
								int receiver=scanner.nextInt();
								System.out.println("------------Enter the amount to be Transfer-------------------");
								int transfer = scanner.nextInt();
								cd.transfer(accountNumber, receiver, transfer);
								}
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e.getMessage());
						}
					
						if(x == 5) {
							m=false;
							System.out.println("--------------------------Log Out SuccessFully----------------------------");
						}
						
					}

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Kuch dikkat ho gya hai");
				}
				
				break;
			default:
				break;
			}
		}
	}
}
