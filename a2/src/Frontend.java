/*
	This is the front end program that contains different feature
	each feature will do differnt job, such that deposit will deposit money from a account number that user entered
*/

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/*

Frontend is the class that contains different feature’s implementation. 
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
		
		Scanner userInput = new Scanner(System.in);
		while (true){
			System.out.println("What transaction do you want to do today(createacct,logout,deposit,withdraw,transfer,deleteacct): ");
			String userChoice = userInput.nextLine();
			if( userChoice.equalsIgnoreCase("createacct") && this.mode.equalsIgnoreCase("agent") ){
				this.createAccount();
				return 0;
			}
			else if(userChoice.equalsIgnoreCase("logout") ){
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
		}
	}
	
	//this is the function that delete a certain exist account number by removing our AccountList Array List
	public void deleteAccount(){
		String number = askExistNumber();
		String name = askName();
		this.AccountList.remove(number);
		this.dailySummary.add("DEL " + number + " 000 " + "0000000 " + name);
	}
	
	//this is the function that create account, we have a ask a account that is not exist from our existed account list 
	public void createAccount(){
		String number = askNumber();
		String name = askName();
		this.AccountList.add(number);
		this.dailySummary.add("NEW " + number + " 000 " + "0000000 " + name);
	}
	
	//this is the function that is the transaction of deposit
	public void depositMoney(){
		String number = askExistNumber();
		int amount = depositAskAmount();
		this.dailySummary.add("DEP " + number + " " + amount + " 0000000 " + "***");
		
	}

	//this is the function that ask amount for deposit, this is the checking function make sure amount is valid and not exceed the limit
	public int depositAskAmount(){
		Scanner userInput = new Scanner(System.in);
		int amountNumber = 0;
		String amount = null;
		while (true){
			System.out.println("Enter your deposit amount: ");
			amount = userInput.nextLine();
			amountNumber = Integer.parseInt(amount);
			
			if(this.mode.equalsIgnoreCase("ATM")){
				if ( (this.depositLimit + amountNumber) <= 500000 && amountNumber <= 200000) {
					this.depositLimit += amountNumber;
					return amountNumber;
				}
			}
			else{
				if ( amountNumber <= 99999999 )
					return amountNumber;
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
		Scanner userInput = new Scanner(System.in);
		while (true){
			System.out.println("Enter your Account number: ");
			String accountNumber = userInput.nextLine();
			if(accountNumber.length() == 7 && accountNumber.charAt(0) != '0' && Frontend.isNumeric(accountNumber) ) {
				if ( this.AccountList.contains(accountNumber) ){
					return accountNumber;
				}
			}
		}
	}

	////this is the function that is the transaction of withdraw, it is has to ask a account number that is exist, otherwise can not withdraw
	public void withdrawMoney(){
		String number = askExistNumber();
		int amount = withdrawAskAmount();
		this.dailySummary.add("WDR " + number +" " + amount + " 0000000 " + "***");
	}

	//this is the function that ask amount for withdraw, this is the checking function make sure amount is valid and not exceed the limit
	public int withdrawAskAmount(){
		Scanner userInput = new Scanner(System.in);
		String amount = null;
		int amountNumber = 0;
		while (true){
			System.out.println("Enter your withdraw amount: ");
			amount = userInput.nextLine();
			amountNumber = Integer.parseInt(amount);
			
			if(this.mode.equalsIgnoreCase("ATM")){
				if ( (this.withDrawLimit + amountNumber) <= 500000 && amountNumber <= 100000) {
					this.withDrawLimit += amountNumber;
					return amountNumber;
				}
			}
			else{
				if ( amountNumber <= 99999999 )
					return amountNumber;
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
	}

	//this is the function that ask amount for transfer, this is the checking function make sure amount is valid and not exceed the limit
	public int transferAskAmount(){
		Scanner userInput = new Scanner(System.in);
		String amount = null;
		int amountNumber = 0;
		while (true){
			System.out.println("Enter your transfer amount: ");
			amount = userInput.nextLine();
			amountNumber = Integer.parseInt(amount);
			
			if(this.mode.equalsIgnoreCase("ATM")){
				if ( (this.transferLimit + amountNumber) <= 1000000 && amountNumber <= 1000000) {
					this.transferLimit += amountNumber;
					return amountNumber;
				}
			}
			else{
				if ( amountNumber <= 99999999 )
					return amountNumber;
			}
		}
	}

	//this is the function that ask for a valid account number and it is not exist in our account number list
	public String askNumber(){
		Scanner userInput = new Scanner(System.in);
		while (true){
			System.out.println("Enter your Account number: ");
			String accountNumber = userInput.nextLine();
			if(accountNumber.length() == 7 && accountNumber.charAt(0) != '0' && Frontend.isNumeric(accountNumber)) {
				if ( ! (this.AccountList.contains(accountNumber) ) ){
					return accountNumber;
				}
			}
		}
	}

	//this is the function that asks name for a user
	public String askName(){
		Scanner userInput = new Scanner(System.in);
		String accountName = null;
		while(true){
			System.out.println("Enter your name: ");
			accountName = userInput.nextLine();	
			if( accountName.length() >= 3 && accountName.length() <= 30)
				return accountName;		
		}
	}	
}

