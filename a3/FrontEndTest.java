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
	
	This is all of the test case for our assignment 1, and we passed all of them
	
	We delete transfer R5T5,R5T6,because these two are the wrong cases and it is duplicate
	
*/

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)

/**
 * throw NoSuchElementException with error message "No Line Found" to check out the when the source code
 * get a invalid input it can still run and print a error message to the console
 * check out the content on the console to get the output
 * assertTrue is to check out if the accountList file is correctly update
 * assertTrue is to check out if the SummaryTransactionsFile is correctly update
 *
 *
 */
class FrontEndTest {

	//public static ByteArrayInputStream in;

	public static final InputStream sysInBackup = System.in;
	
	//these two are the arguments for the Start program, first one is the accoountList name(input file), second one is the output file name, summary transaction file
	public static final String[] ValidArgs = new String[] {new String("accountList"),new String("SummaryTransactionFile")};


	static {
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
	}

	public String readInfo(String fileName){
		//use try catch IOexception e
		String content = "";
		String data = null;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream( fileName + ".txt")));

			data = br.readLine();
			while(data != null){
				content += data + " ";
				data = br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return content.substring(0,content.length()-1);
	}


	@Test
	void loginR1T1() throws Exception {
		System.out.println("Login R1T1");
		ByteArrayInputStream in = new ByteArrayInputStream("machine".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T2() throws Exception {
		System.out.println("Login R1T2");
		ByteArrayInputStream in = new ByteArrayInputStream("agent".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T3() throws Exception{
		System.out.println("Login R1T3");
		ByteArrayInputStream in = new ByteArrayInputStream("logout".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T4() throws Exception{
		System.out.println("Login R1T4");
		ByteArrayInputStream in = new ByteArrayInputStream("createacct".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T5() throws Exception{
		System.out.println("Login R1T5");
		ByteArrayInputStream in = new ByteArrayInputStream("deleteacct".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T6() throws Exception{
		System.out.println("Login R1T6");
		ByteArrayInputStream in = new ByteArrayInputStream("deposit".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T7() throws Exception{
		System.out.println("Login R1T7");
		ByteArrayInputStream in = new ByteArrayInputStream("withdraw".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T8() throws Exception{
		System.out.println("Login R1T8");
		ByteArrayInputStream in = new ByteArrayInputStream("transfer".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR1T9() throws Exception{
		System.out.println("Login R1T9");
		ByteArrayInputStream in = new ByteArrayInputStream("unrecognized".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR2T1() throws Exception{
		System.out.println("Login R2T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals( "EOS 0000000 000 0000000 ***"));
	}

	@Test
	void loginR2T2() throws Exception{
		System.out.println("Login R2T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals( "EOS 0000000 000 0000000 ***"));
	}

	@Test
	void loginR2T3() throws Exception{
		System.out.println("Login R2T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nunrecognized".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (Start.IllegalInputException e){
			assertEquals("Illegal input unrecognized at state LOGGED_IN", e.getMessage());
		}
	}

	@Test
	void loginR3T1() throws Exception{
		System.out.println("Login R3T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nlogin".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (Start.IllegalInputException e){
			assertEquals("No subsequent login should be accepted after a login", e.getMessage());
		}

	}

	@Test
	void loginR3T2() throws Exception{
		System.out.println("Login R3T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nlogin".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No subsequent login should be accepted after a login", e.getMessage());
		}
	}

	@Test
	void loginR3T3() throws Exception{
		System.out.println("Login R3T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nlogin".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No subsequent login should be accepted after a login", e.getMessage());
		}

	}


	@Test
	void loginR3T4() throws Exception{
		System.out.println("Login R3T4");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\nlogin".getBytes());
		System.setIn(in);
		assertThrows(NoSuchElementException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void loginR4T1() throws Exception{
		System.out.println("Login R4T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeleteacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("unprivileged transaction", e.getMessage());
		}
	}

	@Test
	void loginR4T2() throws Exception{
		System.out.println("Login R4T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ncreateacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("unprivileged transaction", e.getMessage());
		}
	}

	@Test
	void loginR4T3() throws Exception{
		System.out.println("Login R4T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void loginR4T4() throws Exception{
		System.out.println("Login R4T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void loginR4T5() throws Exception{
		System.out.println("Login R4T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void loginR4T6() throws Exception{
		System.out.println("Login R4T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nunrecognized".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("unrecognized transaction", e.getMessage());
		}
	}

	@Test
	void loginR5T1() throws Exception{
		System.out.println("Login R5T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}



	@Test
	void loginR5T2() throws Exception{
		System.out.println("Login R5T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void loginR5T3() throws Exception{
		System.out.println("Login R5T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void loginR5T4() throws Exception{
		System.out.println("Login R5T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void loginR5T5() throws Exception{
		System.out.println("Login R5T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}
	@Test
	void loginR5T6() throws Exception{
		System.out.println("Login R5T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nunrecognized".getBytes());


			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("unrecognized transaction", e.getMessage());
		}
	}


	@Test
	void logoutR1T1() throws Exception {
		try {
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		} catch (IOException e) {
			System.out.println("Logout R1T1");
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n500\nlogout".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
			assertTrue(readInfo("SummaryTransactionFile").equals("DEP 1234567 500 0000000 *** EOS 0000000 000 0000000 ***"));
		}
	}

	@Test
	void logoutR1T2() throws Exception{
		System.out.println("Logout R1T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("WDR 1234567 500 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void logoutR1T3() throws Exception{
		System.out.println("Logout R1T3");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("XFR 1234567 500 7654321 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void logoutR1T4() throws Exception{
		System.out.println("Logout R1T4");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n1111111\nvalid3\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321 1111111"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 1111111 000 0000000 valid3 EOS 0000000 000 0000000 ***"));

	}
	@Test
	void logoutR1T5() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e) {
			System.out.println("Logout R1T5");
		}
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct\n1234567\nvalid1\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("7654321"));
		assertTrue(readInfo("SummaryTransactionFile").equals("DEL 1234567 000 0000000 valid1 EOS 0000000 000 0000000 ***"));
	}

	@Test
	void logoutR1T6() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e) {
			System.out.println("Logout R1T5");
		}
		System.out.println("Logout R1T6");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("DEP 1234567 500 0000000 *** EOS 0000000 000 0000000 ***"));

	}

	@Test
	void logoutR1T7() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e) {
			System.out.println("Logout R1T5");
		}
		System.out.println("Logout R1T7");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		System.out.println(readInfo("SummaryTransactionFile"));
		assertTrue(readInfo("SummaryTransactionFile").equals("WDR 1234567 500 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void logoutR1T8() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e) {
			System.out.println("Logout R1T5");
		}
		System.out.println("Logout R1T8");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n7654321\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("XFR 1234567 500 7654321 *** EOS 0000000 000 0000000 ***"));

	}
	@Test
	void logoutR2T1() throws Exception{
		System.out.println("Logout R2T1");
		ByteArrayInputStream in = new ByteArrayInputStream("logout".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR2T2() throws Exception{
		System.out.println("Logout R2T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T1() throws Exception{
		System.out.println("Logout R3T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\nagent".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T2() throws Exception{
		System.out.println("Logout R3T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\nmachine".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T3() throws Exception{
		System.out.println("Logout R3T3");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\ndeposit".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T4() throws Exception{
		System.out.println("Logout R3T4");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\ndeposit".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T5() throws Exception{
		System.out.println("Logout R3T5");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\ntransfer".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T6() throws Exception{
		System.out.println("Logout R3T6");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\ncreateacct".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T7() throws Exception{
		System.out.println("Logout R3T7");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\ndeleteacct".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void logoutR3T8() throws Exception{
		System.out.println("Logout R3T8");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nlogout\nlogin".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR1T1() throws Exception{
		System.out.println("Create account R1T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ncreateacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("unprivileged transaction", e.getMessage());
		}
	}

	@Test
	void createacctR1T2() throws Exception{
		System.out.println("Create account R1T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR2T1() throws Exception{
		System.out.println("Create account R2T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR2T2() throws Exception{
		System.out.println("Create account R2T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR2T3() throws Exception{
		System.out.println("Create account R2T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR2T4() throws Exception{
		System.out.println("Create account R2T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\nabcdefg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR3T1() throws Exception{
		System.out.println("Create account R3T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n1234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void createacctR3T2() throws Exception{
		System.out.println("Create account R3T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR4T1() throws Exception{
		System.out.println("Create account R4T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nab".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR4T2() throws Exception{
		System.out.println("Create account R4T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nabbdefghijklmnopqrstuvwxyzabcde".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR5T1() throws Exception{
		System.out.println("Create account R5T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\n!!!???\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321 2345678"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 2345678 000 0000000 !!!??? EOS 0000000 000 0000000 ***"));
	}



	@Test
	void createacctR5T2() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R5T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\n123valid123\nlogout".getBytes());
		System.setIn(in);
		System.out.println(readInfo("accountList"));
		Start.main(ValidArgs);


		assertTrue(readInfo("accountList").equals("1234567 7654321 2345678"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 2345678 000 0000000 123valid123 EOS 0000000 000 0000000 ***"));
	}

	@Test
	void createacctR5T3() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("create account R5T3");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\n1234\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321 2345678"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 2345678 000 0000000 1234 EOS 0000000 000 0000000 ***"));
	}

	@Test
	void createacctR5T4() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("create account R5T4");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nvalid name\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321 2345678"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 2345678 000 0000000 valid name EOS 0000000 000 0000000 ***"));
	}

	@Test
	void createacctR5T5() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R5T5");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nVALID NAME\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321 2345678"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 2345678 000 0000000 VALID NAME EOS 0000000 000 0000000 ***"));
	}

	@Test
	void createacctR6T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R6T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\n name".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR6T2() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R6T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nname ".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR7T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R7T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nvalid name\ndeposit\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR7T2() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R7T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nvalid name\nwithdraw\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR7T3() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Create account R7T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nvalid name\ntransfer\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void createacctR8T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("create account R8T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ncreateacct\n2345678\nvalid name\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321 2345678"));
		assertTrue(readInfo("SummaryTransactionFile").equals("NEW 2345678 000 0000000 valid name EOS 0000000 000 0000000 ***"));
	}

	@Test
	void deleteacctR1T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Delete account R1T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeleteacct".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("unprivileged transaction", e.getMessage());
		}
	}

	@Test
	void deleteacctR2T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Delete account R2T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct\n1234\nvalid1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void deleteacctR2T2() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Delete account R2T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct\n1234567\ninvalid1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void deleteacctR2T3() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Delete account R2T3");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct\n1234567\nvalid1\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("7654321"));
		assertTrue(readInfo("SummaryTransactionFile").equals("DEL 1234567 000 0000000 valid1 EOS 0000000 000 0000000 ***"));
	}

	@Test
	void deleteacctR3T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Delete account R3T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct\n1234567\nvalid1\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("7654321"));
		assertTrue(readInfo("SummaryTransactionFile").equals("DEL 1234567 000 0000000 valid1 EOS 0000000 000 0000000 ***"));
	}

	@Test
	void deleteacctR4T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("Delete account R4T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeleteacct\n1234567\nvalid1\ndeposit\n1234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);
		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR1T1() throws Exception{
		System.out.println("deposit R1T1");
		ByteArrayInputStream in = new ByteArrayInputStream("deposit".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void depositR2T1() throws Exception{
		try{
			BufferedWriter aWriter = new BufferedWriter(new FileWriter(ValidArgs[0] + ".txt"));
			aWriter.flush();
			aWriter.write("1234567\n7654321");
			aWriter.newLine();
			aWriter.close();
		}catch(IOException e){

		}
		System.out.println("deposit R2T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321"));
		assertTrue(readInfo("SummaryTransactionFile").equals("DEP 1234567 500 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void depositR2T2() throws Exception{
		System.out.println("deposit R2T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n500\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("accountList").equals("1234567 7654321"));
		assertTrue(readInfo("SummaryTransactionFile").equals("DEP 1234567 500 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void depositR3T1() throws Exception{
		System.out.println("deposit R3T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T2() throws Exception{
		System.out.println("deposit R3T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n012345".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T3() throws Exception{
		System.out.println("deposit R3T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n01234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T4() throws Exception{
		System.out.println("deposit R3T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n123456a".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T5() throws Exception{
		System.out.println("deposit R3T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T6() throws Exception{
		System.out.println("deposit R3T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n012345".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T7() throws Exception{
		System.out.println("deposit R3T7");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n01234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void depositR3T8() throws Exception{
		System.out.println("deposit R3T8");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n123456a".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void depositR3T9() throws Exception{
		System.out.println("deposit R3T9");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T10() throws Exception{
		System.out.println("deposit R3T10");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T11() throws Exception{
		System.out.println("deposit R3T11");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n500a".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T12() throws Exception{
		System.out.println("deposit R3T12");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n500a".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T13() throws Exception{
		System.out.println("deposit R3T13");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n-1000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T14() throws Exception{
		System.out.println("deposit R3T14");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n-1000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T15() throws Exception{
		System.out.println("deposit R3T15");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n123.05".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR3T16() throws Exception{
		System.out.println("deposit R3T16");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n123.05".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR4T1() throws Exception{
		System.out.println("deposit R4T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n200000\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("DEP 1234567 200000 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void depositR4T2() throws Exception{
		System.out.println("deposit R4T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n200001".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR4T3() throws Exception{
		System.out.println("deposit R4T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ndeposit\n1234567\n100000\ndeposit\n1234567\n100000\ndeposit\n1234567\n10000\ndeposit\n1234567\n100000\ndeposit\n1234567\n100000\ndeposit\n1234567\n100000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void depositR4T4() throws Exception{
		System.out.println("deposit R4T4");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n99999999\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		System.out.println(readInfo("accountList"));
		assertTrue(readInfo("SummaryTransactionFile").equals("DEP 1234567 99999999 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void depositR4T5() throws Exception{
		System.out.println("deposit R4T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ndeposit\n1234567\n100000000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR1T1() throws Exception{
		System.out.println("withdraw R1T1");
		ByteArrayInputStream in = new ByteArrayInputStream("withdraw".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}


	@Test
	void withdrawR2T1() throws Exception{
		System.out.println("withdraw R2T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR2T2() throws Exception {
		System.out.println("withdraw R2T2");
		try {
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		} catch (NoSuchElementException e) {
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T1() throws Exception{
		System.out.println("withdraw R3T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T2() throws Exception{
		System.out.println("withdraw R3T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T3() throws Exception{
		System.out.println("withdraw R3T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}
	@Test
	void withdrawR3T4() throws Exception{
		System.out.println("withdraw R3T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n123defg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T5() throws Exception{
		System.out.println("withdraw R3T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T6() throws Exception{
		System.out.println("withdraw R3T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T7() throws Exception{
		System.out.println("withdraw R3T7");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void withdrawR3T8() throws Exception{
		System.out.println("withdraw R3T8");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n123defg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T9() throws Exception{
		System.out.println("withdraw R3T9");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T10() throws Exception{
		System.out.println("withdraw R3T10");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T11() throws Exception{
		System.out.println("withdraw R3T11");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR3T12() throws Exception{
		System.out.println("withdraw R3T12");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T1() throws Exception{
		System.out.println("withdraw R4T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n0.1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T2() throws Exception{
		System.out.println("withdraw R4T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567\n0.1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T3() throws Exception{
		System.out.println("withdraw R4T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n-1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T4() throws Exception{
		System.out.println("withdraw R4T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567\n-1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T5() throws Exception{
		System.out.println("withdraw R4T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n100000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T6() throws Exception{
		System.out.println("withdraw R4T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567\n100000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T7() throws Exception{
		System.out.println("withdraw R4T7");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n200000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR4T8() throws Exception{
		System.out.println("withdraw R4T8");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567\n100000000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void withdrawR4T9() throws Exception{
		System.out.println("withdraw R4T9");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void withdrawR5T1() throws Exception{
		System.out.println("withdraw R5T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n100000\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("WDR 1234567 100000 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void withdrawR5T2() throws Exception{
		System.out.println("withdraw R5T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nwithdraw\n1234567\n100000\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("WDR 1234567 100000 0000000 *** " +
				"WDR 1234567 100000 0000000 *** " +
				"WDR 1234567 100000 0000000 *** " +
				"WDR 1234567 100000 0000000 *** " +
				"WDR 1234567 100000 0000000 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void withdrawR5T3() throws Exception{
		System.out.println("withdraw R5T3");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\nwithdraw\n1234567\n99999999\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("WDR 1234567 99999999 0000000 *** EOS 0000000 000 0000000 ***"));
	}


	@Test
	void transferR1T1() throws Exception{
		System.out.println("transfer R1T1");
		ByteArrayInputStream in = new ByteArrayInputStream("transfer".getBytes());
		System.setIn(in);
		assertThrows(Start.IllegalInputException.class, () ->
				Start.main(ValidArgs));
		System.setIn(sysInBackup);
	}

	@Test
	void transferR2T1() throws Exception{
		System.out.println("transfer R2T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR2T2() throws Exception{
		System.out.println("transfer R2T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T1() throws Exception{
		System.out.println("transfer R3T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T2() throws Exception{
		System.out.println("transfer R3T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T3() throws Exception{
		System.out.println("transfer R3T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T4() throws Exception{
		System.out.println("transfer R3T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n123defg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T5() throws Exception{
		System.out.println("transfer R3T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T6() throws Exception{
		System.out.println("transfer R3T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T7() throws Exception{
		System.out.println("transfer R3T7");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T8() throws Exception{
		System.out.println("transfer R3T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n123defg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T9() throws Exception{
		System.out.println("transfer R3T9");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T10() throws Exception{
		System.out.println("transfer R3T10");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n2345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T11() throws Exception{
		System.out.println("transfer R3T11");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR3T12() throws Exception{
		System.out.println("transfer R3T12");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void transferR4T1() throws Exception{
		System.out.println("transfer R4T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T2() throws Exception{
		System.out.println("transfer R4T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T3() throws Exception{
		System.out.println("transfer R4T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T4() throws Exception{
		System.out.println("transfer R4T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n123defg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T5() throws Exception{
		System.out.println("transfer R4T5");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n12345678".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T6() throws Exception{
		System.out.println("transfer R4T6");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T7() throws Exception{
		System.out.println("transfer R4T7");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n0123456".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T8() throws Exception{
		System.out.println("transfer R4T8");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n123defg".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T9() throws Exception{
		System.out.println("transfer R4T9");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n8765432".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR4T10() throws Exception{
		System.out.println("transfer R4T10");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n8765432".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T1() throws Exception{
		System.out.println("transfer R5T1");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n0.1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T2() throws Exception{
		System.out.println("transfer R5T2");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n7654321\n0.1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T3() throws Exception{
		System.out.println("transfer R5T3");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n-1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T4() throws Exception{
		System.out.println("transfer R5T4");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n7654321\n-1".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T7() throws Exception{
		System.out.println("transfer R5T7");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n1000001".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T8() throws Exception{
		System.out.println("transfer R5T8");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n7654321\n100000000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}

	@Test
	void transferR5T10() throws Exception{
		System.out.println("transfer R5T10");
		try{
			ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n1000000\ntransfer\n1234567\n7654321\n1000000".getBytes());
			System.setIn(in);
			Start.main(ValidArgs);

		}catch (NoSuchElementException e){
			assertEquals("No line found", e.getMessage());
		}
	}


	@Test
	void transferR6T1() throws Exception{
		System.out.println("transfer R6T1");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n1000000\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("XFR 1234567 1000000 7654321 *** EOS 0000000 000 0000000 ***"));
	}

	@Test
	void transferR6T2() throws Exception{
		System.out.println("transfer R6T2");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nmachine\ntransfer\n1234567\n7654321\n500000\ntransfer\n1234567\n7654321\n500000\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		assertTrue(readInfo("SummaryTransactionFile").equals("XFR 1234567 500000 7654321 *** " +
				"XFR 1234567 500000 7654321 *** " +
				"EOS 0000000 000 0000000 ***"));
	}

	@Test
	void transferR6T3() throws Exception{
		System.out.println("transfer R6T3");
		ByteArrayInputStream in = new ByteArrayInputStream("login\nagent\ntransfer\n1234567\n7654321\n99999999\nlogout".getBytes());
		System.setIn(in);
		Start.main(ValidArgs);
		System.out.println(readInfo("SummaryTransactionFile"));
		assertTrue(readInfo("SummaryTransactionFile").equals("XFR 1234567 99999999 7654321 *** EOS 0000000 000 0000000 ***"));
	}



}

