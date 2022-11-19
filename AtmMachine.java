package Atm;
import java.sql.*;
import java.util.Scanner;
public class AtmMachine{
	static Scanner scanner=new Scanner(System.in);
	static String accountHolderName = null;
	static String accountPass=null;
	static int option;
	static AccountManager m=new AccountManager();
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/rushikesh","root","root");
		System.out.println();
		System.out.println("Welcome to my ATM");
		System.out.println();
		System.out.println("Enter account holder name");
		accountHolderName=new String(scanner.nextLine());	
		System.out.println("Enter account password");
		accountPass=new String(scanner.nextLine());
		Account customer=m.selectAccount(accountHolderName,accountPass);
		//System.out.print(customer.accountName);
		System.out.println();
		System.out.println("Welcome "+ customer.accountName);
		System.out.println("Options for you are :");
		System.out.println("1.Transition history\n"
				+ "2.Withdrawl\n"
				+ "3.Deposite\n"
				+ "4.Transfer\n"
				+ "5.Exit\n");
		System.out.println("Enter your options");
		option=scanner.nextInt();
		do{	
			switch(option){
			case 1:
				System.out.println("================Transition History=========");
				PreparedStatement ps4=con.prepareStatement("select * from bank where name=? ");
				ps4.setString(1, customer.accountName);
				ResultSet rs1=ps4.executeQuery();
				 while(rs1.next()) {
						System.out.print(rs1.getString(1)+"     ");
						System.out.print(rs1.getString(2)+"     ");
						System.out.print(rs1.getDouble(3)+"     ");	
						System.out.println();
					}
				 break;	
			case 2:
				System.out.println("*********************************************************************************************************************************************");
				System.out.println("Enter the amount to withdrraw");
				double amount=scanner.nextInt();
				double left=customer.withdrawl(amount);
				System.out.println("Left is Rs "+left);
				String moneywithdraw="withdraw";
				PreparedStatement ps=con.prepareStatement("insert into bank(name,money,balance)values(?,?,?) ");
			ps.setString(1, customer.accountName);
			ps.setString(2,moneywithdraw );
			ps.setDouble(3,amount );
			ps.execute();
				break;
			case 3:
				System.out.println("***********************************************************************************************************************************************");
				System.out.println("Enter the amount to Deposite");
				double amount1=scanner.nextInt();
				double left1=customer.deposit(amount1);
				System.out.println("Left is Rs "+left1);
				String moneyde="Deposite";
				PreparedStatement ps1=con.prepareStatement("insert into bank(name,money,balance)values(?,?,?) ");
			ps1.setString(1, customer.accountName);
			ps1.setString(2,moneyde);
			ps1.setDouble(3,amount1);
			ps1.execute();
				break;	
			case 4:
				System.out.println("*************************************************************************************************************************************************");
				System.out.println("Enter account bank account number: ");
				int number=scanner.nextInt();
				System.out.println("Enter the amount to tranfer");
				double amount2=scanner.nextInt();
				double left2=customer.transfer(amount2);
				System.out.println("Left is Rs "+left2);
				String moneyt="Transfer";
				PreparedStatement ps2=con.prepareStatement("insert into bank(name,money,balance)values(?,?,?) ");
			ps2.setString(1, customer.accountName);
			ps2.setString(2,moneyt);
			ps2.setDouble(3,amount2);
			ps2.execute();
				break;
			case 5:
				System.out.println("Transition is end...thank you");
				System.exit(0);
				break;
			}
			System.out.println("*************************************************************************************************************");
			System.out.println("Options for you are :");
			System.out.println("1.Transition history\n"
					+ "2.Withdrawl\n"
					+ "3.Deposite\n"
					+ "4.Transfer\n"
					+ "5.Exit\n");
			System.out.println("Enter your options");
			option=scanner.nextInt();
			
		}while(option!=5);
	
		
		}
	}