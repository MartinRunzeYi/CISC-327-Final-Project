/*

CISC 327 Course Project 
Assignment #6


Company:

	Canada Trust

Author:

	Yudong Zhou (20083467)
	Yitong Liu (20028039)
	Runze Yi (20073329)
	Tong Bu (20079649)
	
Date:

	2019/Nov/22


This is the program that have both front end and back end, basically front end will ask different transactions and write different summary transaction files

And back office will reads master account file and different summary transaction files and write new master file.

The back office will have additional information to front end for example about account name and account balance, and make system more complete.

*/

import java.io.*;
import java.util.*;
import java.nio.file.Files;

/*

Login is the main class that you need two arguments to run, first argument is the valid account list that you need to have
second argument is a non-exist file name that you want to write your summary transaction file on.

*/

public class Start{

	public static Scanner userInput;

	public static void main(String[] args) throws Exception{
		
		userInput = new Scanner(System.in);
		
		String dayNum = args[3];
		// if the arguments number is not 2, we can not start a front end session
		if(  ! (args.length  == 4) ){
			System.out.println("Your do not have three command-line arguments!");
		}
		String choice = "";
		int gene = Integer.parseInt(dayNum) - 1;
		
		String gName = "";
		if (gene == 0){
			gName = args[2];
		}
		else{
			gName = args[2] + (Integer.parseInt(dayNum) - 1);
		}
		BackOffice officeDemo = new BackOffice(gName);
		for (int x = 0; x< 3; x++) {
			while (!(choice.equals("login"))) {
				System.out.println("Enter login to login the front end system: ");
				choice = userInput.nextLine();
			}
			while  ( !  (choice.equalsIgnoreCase("machine") || choice.equalsIgnoreCase("agent") )  ) {
				System.out.println("Enter your mode for this login (machine or agent): ");
				choice = userInput.nextLine();
			}
				//create a Frontend object and use this object to runs the front end session
				Frontend testDemo = new Frontend(args[0], args[1], choice);
				while ( testDemo.asktTransaction() != -1 ){		
					System.out.println("Transaction is still processing...");
				}
		}
		System.out.println("One day is passed!");
		officeDemo.mergeSummaryFile(args[1]);
		officeDemo.setMaster(args[2] + dayNum + ".txt");
		officeDemo.writeMasterFile(args[0]+dayNum);
		}
	
}
	

/*
 * 
 * This is the back office that will reads master file and different summary transaction file and will generate new master file
 * 
 * This Class will have 3 attributes, one is the masterFileName as String, 
 * 
 * One is Hashmap that link each available account number to each account balance,
 * 
 * One is Hashmap that link each account number to each account name.
 */
class BackOffice{
	
	private String masterFileName;
	
	private HashMap<Integer, Integer> accountBalance = new HashMap<>();
	
	private HashMap<Integer, String> accountName = new HashMap<>();
	
	//This is non-parameter constructor
	public BackOffice(){
		
	}
	public void setMaster(String masterFile){
		this.masterFileName = masterFile;
	}
	public String getMaster(){
		return this.masterFileName;
	}
	
	//This is the constructor that create BackOffice object by taking the master account file as the parameter and starts to read the master file. 
	public BackOffice(String masterFileName) throws Exception{
		this.masterFileName = masterFileName + ".txt";
		readMasterFile();
	}
	//This method will takes a accountNumber as parameter, and return that account balance by this account number
	public int checkBalance(int accountNumber) {
		return this.accountBalance.get(accountNumber);
	}
	
	//This method takes an accountNumber as parameter, this method will return an account name by certain account number.
	public String checkName(int accountNumber) {
		
		return this.accountName.get(accountNumber);
	}
	
	//This method takes the transaction summary file name prefix as the parameter, starts to read the all summary transaction files in the current directory
	@SuppressWarnings("resource")
	public void mergeSummaryFile(String startFileName) throws Exception{


		File dir = new File(".");
		File [] summaryPathList = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(startFileName);
			}
		});
		if (summaryPathList.length == 0){
			throw new IOException("Illegal IO summary transaction file!");
		}

		for(File summaryPath: summaryPathList) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(summaryPath)));
			//data will be each line of a summary transaction file
			String data = null;
			data = br.readLine();
			String dataList[] = new String[5];

			String command = null;

			int number = 0;

			int balance = 0;

			int number2 = 0;

			String name = null;

			while(data != null){
				// DataList will split each line of command into different part, CCC AAAA MMMM BBBB NNNN
				dataList = data.split(" ");
				command = dataList[0];
				number = Integer.parseInt(dataList[1]);
				balance = Integer.parseInt(dataList[2]);
				number2 = Integer.parseInt(dataList[3]);
				name = dataList[4];

				if (this.accountName.containsKey(number)) {

					if (this.accountBalance.get(number) < 0) {
						throw new Exception("Illegal Balance!");
					}

				}
				else{
					if (!(command.equalsIgnoreCase("NEW") || (command.equalsIgnoreCase("EOS") ))){
						System.out.println(data);
						throw new Exception("Illegal Transaction!");
						
					}
				}

				if (this.accountName.containsKey(number2)) {	

					if (this.accountBalance.get(number2) < 0) {
						throw new Exception("Illegal Balance!");
					}

				}
				else{
					if ((command.equalsIgnoreCase("XFR"))){
						throw new Exception("Illegal Transaction!");
					}
				}

				if (command.equalsIgnoreCase("DEP")){

					balance += this.accountBalance.get(number).intValue();
					this.accountBalance.put(number,balance);
				}

				else if (command.equalsIgnoreCase("WDR")){
					int curMoney = this.accountBalance.get(number).intValue();
					if (curMoney < balance) {
						throw new Exception("Illegal Balance!");
					}
					balance = curMoney - balance;
					this.accountBalance.put(number,balance);

				}

				else if (command.equalsIgnoreCase("NEW")){

					if(this.accountName.containsKey(number)) {
						throw new Exception("Illegal new account! Account is being used!");
					}

					this.accountBalance.put(number,balance);
					this.accountName.put(number,name);
				}

				else if (command.equalsIgnoreCase("DEL")){

					if (this.accountBalance.get(number) != 0) {
						throw new Exception("Illegal Balance for delete account!");
					}

					if (!(name.equalsIgnoreCase(this.accountName.get(number)))) {
						throw new Exception("Illegal delete account! Account name is not matched!");
					}


					this.accountBalance.remove(number);
					this.accountName.remove(number);
				}

				else if (command.equalsIgnoreCase("XFR")){


					int pre = this.accountBalance.get(number2);

					if(pre < balance) {
						throw new Exception("Illegal Balance for transfer!");
					}

					this.accountBalance.put(number2,pre - balance);

					pre = this.accountBalance.get(number);
					this.accountBalance.put(number,pre + balance);


				}

				data = br.readLine();
			}
			br.close();

			File replaceSummary = new File("finished " + startFileName + 1 +  ".txt");
			
			boolean existed = replaceSummary.exists();
			int counter = 2;
			while (existed) {
				replaceSummary = new File("finished " + startFileName + counter + ".txt");
				counter ++;
				existed = replaceSummary.exists();
			}
			

			summaryPath.renameTo(replaceSummary);


		}

		
	}
	

	
	//This method writes out the new master accounts file, and also write the new updated valid account list file.
	public void writeMasterFile(String accountFile) throws Exception{
		
		//collectionNumber is the array of all account number
		int[] collectionNumber = new int[this.accountName.entrySet().size()];
		
		int counter = 0;
		for(Map.Entry<Integer,String> entry : this.accountName.entrySet()){
			collectionNumber[counter] = entry.getKey();
			counter++;
		}
		
		//sort the account number
		Arrays.sort(collectionNumber);
		
		int[] tempList = new int[collectionNumber.length];
		
		int k = tempList.length - 1;
		for (int i = 0; i < tempList.length; i ++){
			tempList[i] = collectionNumber[k];
			k --;
		}
		
		collectionNumber = tempList;
		
		try{
			File masterFileFlow = new File(this.masterFileName);

			@SuppressWarnings("resource")
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(masterFileFlow));
			
			File accountFileFlow = new File(accountFile + ".txt");

			@SuppressWarnings("resource")
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(accountFileFlow));

			for(int accountNumber : collectionNumber){
				
					String name = this.accountName.get(accountNumber);
					
					int balance = this.accountBalance.get(accountNumber);
					
					if (balance < 0) {
						throw new Exception("Illegal Balance!");
					}
					
					String info = accountNumber + " " + balance + " " + name;
					
					
					
					if (info.length() > 47) {
						throw new Exception("Master file information is too long!(over 47 chars)");
					}
					
					//check if the accountNumber is in 7 digit
					if ( ! (9999999 >= accountNumber && accountNumber >= 1000000) ) {
						throw new Exception("Illegal Balance!");
					}
					
					bWriter.flush();
					bWriter.write(info);
					bWriter.newLine();
					
					aWriter.flush();
					aWriter.write(Integer.toString(accountNumber));
					aWriter.newLine();
			}
			aWriter.write(0000000);
			bWriter.close();
			aWriter.close();
		}catch(IOException e){
			throw new IOException("Illegal IO master file!");
		}
	}
	
	//This method reads the master accounts file, and will update two Hashmap attribute

	public void readMasterFile() throws Exception{
		
		try{
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream( this.masterFileName )));
			String data = null;
			data = br.readLine();
			String dataList[] = new String[3];
			int number = 0;
			
			int balance = 0;
			
			String name = null;
			
			while(data != null){
				dataList = data.split(" ");
				//Account Number, Account Balance, Account Name
				number = Integer.parseInt(dataList[0]);
				balance = Integer.parseInt(dataList[1]);
				name = dataList[2];
				if (balance < 0) {
					throw new Exception("Illegal Account Balance!");
				}
				
				this.accountBalance.put(number,balance);
				this.accountName.put(number,name);
				data = br.readLine();
			}
			br.close();
		}catch(IOException e){
			throw new IOException("Illegal IO master file!");
		}
		
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
			System.out.println("Illegal IO account list file!");
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
	public void writeSummary(){
		

		int counter = 1;
		
		File summaryFileFlow = new File(this.transaction_summary_path + ".txt");
		
		boolean newLine = summaryFileFlow.exists();
		
		while (newLine) {
			summaryFileFlow = new File(this.transaction_summary_path + counter + ".txt");
			counter ++;
			newLine = summaryFileFlow.exists();
		}
		
		
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(summaryFileFlow,true));
			for(String info : this.dailySummary){
					bWriter.flush();
					if(info.length() > 61) {
						System.out.println("Transaction file is over 61 chars!");
						break;
					}
					
					String moneyS = info.split(" ")[2];
					if(moneyS.length() < 3 || moneyS.length() > 8) {
						System.out.println("Transaction balance length is wrong!");
					}
					
					int money = Integer.parseInt(moneyS);
					if(money > 99999999 || money < 0) {
						System.out.println("Transaction balance is over the limit!");
					}

					bWriter.write(info);
					bWriter.newLine();
			}
			bWriter.write(new String("EOS 0000000 000 0000000 ***"));
			bWriter.close();
		}catch(IOException e) {
			System.out.println("IO Error!");
		}
	}
	
	//This is the function that asks different transactions from user
	//This function use scanner to scan user input, this function also controls the whole front end transaction
	//return -1 means logout and exit, return 0 is continue the transaction
	
	public int asktTransaction(){
		
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
			}

			else{
				System.out.println("Unrecognized transaction");
			}
		}
	}
	
	//this is the function that delete a certain exist account number by removing our AccountList Array List
	public void deleteAccount(){
		String number = askNumber();
		String name = askName();
		this.AccountList.remove(number);
		this.dailySummary.add("DEL " + number + " 000 " + "0000000 " + name);
		System.out.println("Delete Transaction Complete");
			

		}

		
	
	
	//this is the function that create account, we have a ask a account that is not exist from our existed account list 
	public void createAccount(){
		String number =  askNumber();
		String name = askName();
		this.NewAccountList.add(number);
		this.dailySummary.add("NEW " + number + " 000 " + "0000000 " + name);
		System.out.println("Createacct Transaction Complete");
	}
	
	//this is the function that is the transaction of deposit
	public void depositMoney(){
		String number = askNumber();
		int amount = depositAskAmount();
		this.dailySummary.add("DEP " + number + " " + amount + " 0000000 " + "***");
		System.out.println("Deposit Transaction Complete");
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
						System.out.println("You are exceed the deposit limit");
					}

				}
				else{
					if ( amountNumber <= 99999999 )
						return amountNumber;
					else{
						System.out.println("You are exceed the deposit limit");
					}

				}
			}
			catch(Exception e){
				System.out.println("Deposit Amount is invalid");
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
	
	
	////this is the function that is the transaction of withdraw, it is has to ask a account number that is exist, otherwise can not withdraw
	public void withdrawMoney(){
		String number = askNumber();
		int amount = withdrawAskAmount();
		this.dailySummary.add("WDR " + number +" " + amount + " 0000000 " + "***");
		System.out.println("Withdraw Transaction Complete");
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
						System.out.println("You are exceed the withdraw limit");
					}
				} else {
					if (amountNumber <= 99999999) {
						return amountNumber;
					}
					else{
						System.out.println("You are exceed the withdraw limit");
					}
				}

			}
			catch(Exception e){
				System.out.println("Withdraw Amount is invalid");
			}
		}
	}
	
	//this is the function that is the transaction of the transfer, it it has to ask two account number
	//it is has to ask account number that is exist, otherwise can not transfer
	public void transferMoney(){
		String number = askNumber();
		String number2 = askNumber();
		int amount = transferAskAmount();
		this.dailySummary.add("XFR " + number2 + " " + amount + " " + number + " ***");
		System.out.println("Transfer Transaction Complete");
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
						System.out.println("You are exceed the transfer limit");
					}
				}
				else{
					if ( amountNumber <= 99999999 ) {
						return amountNumber;
					}
					else{
						System.out.println("You are exceed the transfer limit");
					}
				}
			}
			catch(Exception e){
				System.out.println("Transfer Amount is invalid");
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
					System.out.println("The Account Number is already exist in the system");
				}
			} else {
				System.out.println("Account Number is invalid");
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
				return accountName;
			}
			else{
				System.out.println("Account Name is invalid");
			}

		}
	}
}
