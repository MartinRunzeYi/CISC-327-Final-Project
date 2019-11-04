/*

CISC 327 Course Project 
Assignment #3
Front End Rapid

Company:

	Canada Trust

Author:

	Yudong Zhou (20083467)
	Yitong Liu (20028039)
	Runze Yi (20073329)
	Tong Bu (20079649)
	
Date:

	2019/Nov/2

This is the program that perform front end banking system after finish all testing that we did on assignment 1

This is the main program that runs, this is because the login session is the start of the any transaction

This main class of login will ask two command arguments, one is the input file of valid account list

second one is the output file name that you want to have, it is the output file of summary transaction file that 

record different transaction from user

Login class also ask user for what kind of transaction mode they want, since this is the first step after login

After we asked mode, we can run the frontend transaction session and keep running until logout

We have used a while loop, this is because that if logout, asktTransaction() function will return -1 and loops stop

Otherwise the Transaction will be keep processing

*/

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
/*

Login is the main class that you need two arguments to run, first argument is the valid account list that you need to have
second argument is a non-exist file name that you want to write your summary transaction file on.

*/

public class Start{

	public static Scanner userInput;

	enum STATE {
		LOGGED_OUT,
		LOGGED_IN
	}

	static class IllegalInputException extends Exception {
		public IllegalInputException() {
			super();
		}

		public IllegalInputException(String msg) {
			super(msg);
		}

		public IllegalInputException(String msg, Throwable e) {
			super(msg, e);
		}
	}

	public static void main(String[] args) throws Exception{
		userInput = new Scanner(System.in);
		
		// if the arguments number is not 2, we can not start a front end session
		if(  ! (args.length  == 2) ){
			throw new Exception("Your do not have two command-line arguments!");
		}


		STATE current_state = STATE.LOGGED_OUT;
		String choice = "";
		do {
			try {
				if (current_state == STATE.LOGGED_OUT) {
					System.out.println("Enter login to login the front end system: ");
					if (!userInput.hasNextLine()) {
						break;
					}

					choice = userInput.nextLine();
					if (choice.equalsIgnoreCase("login")) {
						current_state = STATE.LOGGED_IN;
					}else if (choice.equalsIgnoreCase("")) {
						break;
					} else {
						throw new IllegalInputException();
					}
				} else if (current_state == STATE.LOGGED_IN) {
					do {
						System.out.println("Enter your mode for this login (machine or agent): ");
						choice = userInput.nextLine();

						if (choice.equalsIgnoreCase("machine") ||
								choice.equalsIgnoreCase("agent")) {

							//create a Frontend object and use this object to runs the front end session
							Frontend testDemo = new Frontend(args[0], args[1], choice);

							while ( testDemo.asktTransaction() != -1 ){
								System.out.println("Transaction is still processing...");
							}

							current_state = STATE.LOGGED_OUT;
							break;
						}
						else if (choice.equalsIgnoreCase("logout")) {
							current_state = STATE.LOGGED_OUT;
							break;
						} else {
							String msg;
							if (choice.equalsIgnoreCase("login"))
								msg = "No subsequent login should be accepted after a login";
							else
								msg = "";
							throw new IllegalInputException(msg);
						}
					} while(true);
				}
			} catch (IllegalInputException e) {
				String msg = "Illegal input " + choice + " at state " + current_state;
				System.out.println(msg);
				if (e.getMessage()==null || e.getMessage().isEmpty()) {
					IllegalInputException ex = new IllegalInputException(msg);
					ex.setStackTrace(e.getStackTrace());
					throw ex;
				} else {
					throw e;
				}
				//throw new IllegalInputException(msg, (Throwable)e);
			}
		} while (true);

		userInput.close();
	}
}


/*
This is the front end program that contains different feature
each feature will do different job, such that deposit will deposit money from a account number that user entered
*/



/*

Frontend is the class that contains different feature�s implementation. 
In this class, Frontend will ask user for different transaction and checking if user prompt correctly, 
and after logout, the summary transaction file will be written.

*/

class Frontend {
	//this is the attribute that contains the account list file name, we can then use this to update account list file
	private String account_list_path;
	
	//this is the transaction summary file name, we can use this to write summary transaction file after log out
	private String transaction_summary_path;
	
	//this is the string attribute that contains what mode user have choose
	private String mode;
	
	//this is the array list of account List attribute, which contains different account number
	private ArrayList<String> AccountList = new ArrayList<String>();
	private ArrayList<String> NewAccountList = new ArrayList<String>();
	
	//this is the daily summary array list attribute, which contains different transaction for a transaction day
	private ArrayList<String> dailySummary = new ArrayList<String>();
	
	//those attribute are the limit for the day, we need to record each transaction in order to check if it is exceed the limit
	private int withDrawLimit = 0;
	private int depositLimit = 0;
	private int transferLimit = 0;
	
	//this is the setter that set the mode
	public void setMode(String mode){
		this.mode = mode;
	}
	
	//this is the function that read account list file and read it into out attribute of AccountList
	public void updateArrayList(){
		//use try catch IOexception e
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream( this.account_list_path + ".txt")));
			String data = null;
			
			data = br.readLine();
			while(data != null){
				this.AccountList.add(data);
				data = br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//this is the setter that set account list file name attribute
	public void setAccount(String account_list_path){
		this.account_list_path = account_list_path;
	}
	
	//this is the setter that set transaction_summary_path file name attribute
	public void setSummary(String transaction_summary_path){
		this.transaction_summary_path = transaction_summary_path;
	}
	
	//this is the getter that return the account_list_path file name
	public String getAccount(){
		
		return this.account_list_path;
	}
	
	//this is the getter that return the transaction_summary_path file name
	public String getSummary(){
		return this.transaction_summary_path;
	}
	
	//this is the getter that return mode name
	public String getMode(){
		return this.mode;
	}
	
	//This is the non-parameter constructor for Frontend class
	public Frontend(){
			
	}
	
	/*
	This is the Frontend 3 parameter constructor, it is taking 3 parameters. 
	The paths of accountList file, the path of summary file and the string mode. 
	Calls 4 functions within it. Stores the paths as attribute and updates information in accountlist file to attribute
	*/
	
	public Frontend(String accountFile,String summaryFile,String mode){
		setSummary(summaryFile);
		setAccount(accountFile);
		setMode(mode);
		//read the account file and update our attribute of AccountList array list
		updateArrayList();
		
	}
	
	/*
	Function that writes information stored in the temporary summary file to the one that's to be output. 
	Also write valid account list file from updated AccountList array list attribute
	*/
	public void writeSummary() throws IOException{
		
		try{
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.transaction_summary_path + ".txt"));
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(this.account_list_path+ ".txt"));
			for(String info: this.AccountList){
					aWriter.flush();
					aWriter.write(info);
					aWriter.newLine();
			}
	
			for(String info : this.dailySummary){
					bWriter.flush();
					bWriter.write(info);
					bWriter.newLine();
			}
			bWriter.write(new String("EOS 0000000 000 0000000 ***"));
			bWriter.close();
			aWriter.close();
			
		}catch(IOException e){
			throw e;
		}
	}
	
	//This is the function that asks different transactions from user
	//This function use scanner to scan user input, this function also controls the whole front end transaction
	//return -1 means logout and exit, return 0 is continue the transaction
	
	public int asktTransaction() throws IOException{
		


		String userChoice = null;
		
		while (true){
			System.out.println("What transaction do you want to do(createacct,logout,deposit,withdraw,transfer,deleteacct): ");
			userChoice = Start.userInput.nextLine();
			if (userChoice.equalsIgnoreCase("login"))
				throw new NoSuchElementException("No subsequent login should be accepted after a login");

			if( userChoice.equalsIgnoreCase("createacct") && this.mode.equalsIgnoreCase("agent") ){
				this.createAccount();
				return 0;
			}
			else if( userChoice.equalsIgnoreCase("createacct")) {
				System.out.println("Account can only be created in agent mode");
				throw new NoSuchElementException("unprivileged transaction");
			}
			else if(userChoice.equalsIgnoreCase("logout") ){
				this.AccountList.addAll(this.NewAccountList);
				this.NewAccountList.clear();
				writeSummary();
				return -1;
			}
			else if( userChoice.equalsIgnoreCase("deposit") ){
				depositMoney();
				return 0;
			}
			else if( userChoice.equalsIgnoreCase("withdraw") ){
				withdrawMoney();
				return 0;
			}
			else if( userChoice.equalsIgnoreCase("transfer") ){
				transferMoney();
				return 0;
			}
			else if( userChoice.equalsIgnoreCase("deleteacct") && this.mode.equalsIgnoreCase("agent") ){
				deleteAccount();
				return 0;
			}
			else if( userChoice.equalsIgnoreCase("deleteacct") ){
				System.out.println("Can not delete account when not agent mode");
				throw new NoSuchElementException("unprivileged transaction");
			}

			else{
				throw new NoSuchElementException("unrecognized transaction");
			}
		}
	}
	
	//this is the function that delete a certain exist account number by removing our AccountList Array List
	public void deleteAccount(){
		String number = askExistNumber();
		String name = askName();
		this.AccountList.remove(number);
		this.dailySummary.add("DEL " + number + " 000 " + "0000000 " + name);
		System.out.println("Delete successful");
	}
	
	//this is the function that create account, we have a ask a account that is not exist from our existed account list 
	public void createAccount(){
		String number;
		do {
			number = askNumber();
		} while (this.AccountList.contains(number));

		String name = askName();
		this.NewAccountList.add(number);
		this.dailySummary.add("NEW " + number + " 000 " + "0000000 " + name);
		System.out.println("createacct Transaction complete");
	}
	
	//this is the function that is the transaction of deposit
	public void depositMoney(){
		String number = askExistNumber();
		int amount = depositAskAmount();
		this.dailySummary.add("DEP " + number + " " + amount + " 0000000 " + "***");
		System.out.println("Deposit successful");
	}
	
	//this is the function that ask amount for deposit, this is the checking function make sure amount is valid and not exceed the limit
	public int depositAskAmount(){

		int amountNumber = 0;
		String amount = null;
		while (true){
			System.out.println("Enter your deposit amount: ");
			amount = Start.userInput.nextLine();
			try {
				amountNumber = Integer.parseInt(amount);
				if(this.mode.equalsIgnoreCase("machine")){
					if ( (this.depositLimit + amountNumber) <= 500000 && amountNumber <= 200000) {
						this.depositLimit += amountNumber;
						return amountNumber;
					}
					else{
						System.out.println("You are exceed the limit");
					}

				}
				else{
					if ( amountNumber <= 99999999 )
						return amountNumber;
					else{
						System.out.println("You are exceed the limit");
					}

				}
			}
			catch(Exception e){
				System.out.println("Amount is not pure digit");
			}
			

			}
	}
	
	//this is the function that checks if the string parameter is pure digit
	public static boolean isNumeric(String str)
	{
		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
	
	//this is the function that ask a account number that account number is exist in our account number list
	public String askExistNumber(){

		while (true){
			System.out.println("Enter your Account number: ");
			String accountNumber = Start.userInput.nextLine();
			if(accountNumber.length() == 7 && accountNumber.charAt(0) != '0' && Frontend.isNumeric(accountNumber) ) {
				if (this.NewAccountList.contains(accountNumber)){
					System.out.println("Cannot accept the transaction for the new account in this session");
				}
				if ( this.AccountList.contains(accountNumber) ){
					return accountNumber;
				}
				else{
					System.out.println("Account not exist");
				}
			}
			else{
				System.out.println("You should enter correct account number");
			}
		}
	}
	
	////this is the function that is the transaction of withdraw, it is has to ask a account number that is exist, otherwise can not withdraw
	public void withdrawMoney(){
		String number = askExistNumber();
		int amount = withdrawAskAmount();
		this.dailySummary.add("WDR " + number +" " + amount + " 0000000 " + "***");
		System.out.println("withdraw transaction complete");
	}
	
	//this is the function that ask amount for withdraw, this is the checking function make sure amount is valid and not exceed the limit
	public int withdrawAskAmount() {

		String amount = null;
		int amountNumber = 0;
		while (true) {
			System.out.println("Enter your withdraw amount: ");
			amount = Start.userInput.nextLine();

			try {
				amountNumber = Integer.parseInt(amount);

				if (this.mode.equalsIgnoreCase("machine")) {
					if ((this.withDrawLimit + amountNumber) <= 500000 && amountNumber <= 100000) {
						this.withDrawLimit += amountNumber;
						return amountNumber;
					}
					else{
						System.out.println("Exceed single-transaction limit");
					}
				} else {
					if (amountNumber <= 99999999) {
						return amountNumber;
					}
					else{
						System.out.println("Exceed single-transaction limit");
					}
				}


			}
			catch(Exception e){
				System.out.println("Withdraw amount invalid");
			}
		}
	}
	
	//this is the function that is the transaction of the transfer, it it has to ask two account number
	//it is has to ask account number that is exist, otherwise can not transfer
	public void transferMoney(){
		String number = askExistNumber();
		String number2 = askExistNumber();
		int amount = transferAskAmount();
		this.dailySummary.add("XFR " + number + " " + amount + " " + number2 + " ***");
		System.out.println("Transfer transaction complete");
	}
	
	//this is the function that ask amount for transfer, this is the checking function make sure amount is valid and not exceed the limit
	public int transferAskAmount(){

		String amount = null;
		int amountNumber = 0;
		while (true){
			System.out.println("Enter your transfer amount: ");
			amount = Start.userInput.nextLine();
			try {
				amountNumber = Integer.parseInt(amount);
				if(this.mode.equalsIgnoreCase("machine")){
					if ( (this.transferLimit + amountNumber) <= 1000000 && amountNumber <= 1000000) {
						this.transferLimit += amountNumber;
						return amountNumber;
					}
					else{
						System.out.println("Exceed single-transaction limit");
					}
				}
				else{
					if ( amountNumber <= 99999999 ) {
						return amountNumber;
					}
					else{
						System.out.println("Exceed single-transaction limit");
					}
				}
			}
			catch(Exception e){
				System.out.println("Transfer amount invalid");
			}

		}
	}
	
	//this is the function that ask for a valid account number and it is not exist in our account number list
	public String askNumber() {

		while (true) {
			System.out.println("Enter your Account number: ");
			String accountNumber = Start.userInput.nextLine();
			if (accountNumber.length() == 7 && accountNumber.charAt(0) != '0' && Frontend.isNumeric(accountNumber)) {
				if (!(this.AccountList.contains(accountNumber))) {
					return accountNumber;
				} else {
					System.out.println("The account number is already exist in the system");
				}
			} else {
				System.out.println("The account number is invalid.");
			}
		}
	}



	
	//this is the function that asks name for a user
	public String askName(){

		String accountName = null;
		while(true){
			System.out.println("Enter your name: ");
			accountName = Start.userInput.nextLine();	
			if( accountName.length() >= 3 && accountName.length() <= 30 && (! accountName.substring(0,1).equals(" ") && ! accountName.substring(accountName.length()-1).equals(" "))){
				System.out.println("The account name is valid");
				return accountName;
			}
			else{
				System.out.println("The account name is invalid");
			}

		}
	}
}