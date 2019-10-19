/*

CISC 327 Course Project 
Assignment #2
Front End Rapid Prototype

Company:

	Canada Trust

Author:

	Yudong Zhou (20083467)
	Yitong Liu (20028039)
	Runze Yi (20073329)
	Tong Bu (20079649)
	
Date:

	2019/Oct/18

This is the program that perform front end banking system

This is the main program that runs, this is because the login session is the start of the any transaction

This main class of login will ask two command arguments, one is the input file of valid account list

second one is the output file name that you want to have, it is the output file of summary transaction file that 

record different transaction from user

Login class also ask user for what kind of transaction mode they want, since this is the first step after login

After we asked mode, we can run the frontend transaction session and keep running until logout

We have used a while loop, this is because that if logout, asktTransaction() function will return -1 and loops stop

Otherwise the Transaction will be keep processing

*/

import java.util.Scanner;

/*

Login is the main class that you need two arguments to run, first argument is the valid account list that you need to have
second argument is a non-exist file name that you want to write your summary transaction file on.

*/

public class Login{
	
	public static void main(String[] args) throws Exception{
		
		// if the arguments number is not 2, we can not start a front end session
		if(  ! (args.length  == 2) ){
			throw new Exception("Your do not have two command-line arguments!");
		}
		
		String mode = askMode();
		//create a Frontend object and use this object to runs the front end session 
		Frontend testDemo = new Frontend(args[0], args[1],mode);
		
		while ( testDemo.asktTransaction() != -1 ){
			System.out.println("Transaction is still processing...");
		}

	}
	
	//this is the function that use scanner to ask user for mode
	public static String askMode(){
		Scanner userInput = new Scanner(System.in);
		while (true){
			System.out.println("Enter your mode for this processing day(machine or agent): ");
			String userChoice = userInput.nextLine();
			if( userChoice.equalsIgnoreCase("machine") || userChoice.equalsIgnoreCase("agent") )
				return userChoice;
			System.out.println("You have entered wrong!");
		}
	}
	
}