import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BackOfficeTest {

    @Test
    void createAccount_t1()throws Exception{
        System.out.println("create account test case 1");
        BackOffice bo = new BackOffice("MasterFile");
        String start = "createAccount_testfile1";
        try{
            bo.mergeSummaryFile(start);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals("Illegal new account! Account is being used!",e.getMessage());
        }
    }



    @Test
    void createAccount_t2()throws Exception{
        System.out.println("create account test case 2");
        BackOffice bo = new BackOffice("MasterFile");
        String start = "createAccount_testfile2";
        try{
            bo.mergeSummaryFile(start);
            int balance = bo.checkBalance(1111111);
            String name = bo.checkName(1111111);
            System.out.println(name);
            System.out.println(balance);
            assertEquals(balance, 0);
            assertEquals(name, "valid123");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    void createAccount_t3()throws Exception{
        System.out.println("create account test case 3");
        BackOffice bo = new BackOffice("MasterFile");
        String start = "non-exist_name";
        try{
            bo.mergeSummaryFile(start);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals("Illegal IO summary transaction file!",e.getMessage());
        }
    }


    @Test
    void withdraw_t1()throws Exception{
        System.out.println("withdraw test case 1");
        BackOffice bo = new BackOffice("MasterFile");
        String start = "withdraw_testfile1";
        try{
            bo.mergeSummaryFile(start);
            int balance = bo.checkBalance(1234567);
            System.out.println(balance);
            assertEquals(balance, 930000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    void withdraw_t2()throws Exception{
        System.out.println("withdraw test case 2");
        BackOffice bo = new BackOffice("MasterFile");
        String start = "withdraw_testfile2";
        try{
            bo.mergeSummaryFile(start);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals("Illegal Balance!",e.getMessage());
        }
    }


    @Test
    void withdraw_t3()throws Exception{
        System.out.println("withdraw test case 3");
        BackOffice bo = new BackOffice("MasterFile");
        String start = "non-exist_name";
        try{
            bo.mergeSummaryFile(start);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals("Illegal IO summary transaction file!",e.getMessage());
        }
    }






}