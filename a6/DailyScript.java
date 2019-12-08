import java.io.*;

public class DailyScript{
	
	
	public static final InputStream backUp = System.in;
	public static final String[] validArgs = new String[] {"AccountList","SummaryFile","MasterFile",""};
	
	public static void main(String args[]){
		
		
		int days = Integer.parseInt(args[0]);
		
		for (int i = 1; i < (days + 1); i++){
			String fileName = "inputFile"+ i + ".txt";
			ByteArrayInputStream in = new ByteArrayInputStream(readInfo(fileName).getBytes());
			System.setIn(in);
			
			try{
				validArgs[3] = Integer.toString(i);
				Start.main(validArgs);
			}catch (Exception e){
				System.out.println(e);
			}
			System.setIn(backUp);
		}
	}
	
	
	public static String readInfo(String name){

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream( name )));
			String temp = br.readLine();
			String info = "";
			while (temp != null){
				
				info += temp + "\n";
				temp = br.readLine();
			}
			System.out.println(info);
			return info;
		}catch (Exception e){
			System.out.println(e);
		}
			
		return "";
	}
	
	
}