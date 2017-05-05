package controler;

import java.io.FileWriter;
import java.util.Scanner;

public class writeFile {
	
	public static void main(String[] args) {
		write();
	}

	private static void write() {
		try{
			String Tabl[] = {"Last Name", "First Name","Title","Year","DimX","DimY","Color"};
			String tr = "";
			String x = "";
			
			Scanner input = new Scanner(System.in);
			
				for ( int i = 0; i < Tabl.length; i++ ) {
					
					System.out.print("Enter" + Tabl[i] + " :");
					x = input.nextLine();
					if (tr != null && !tr.trim().isEmpty()){
					tr = tr + ";"+ x;
					}
					else {tr = x;}
				}
				input.close();
				System.out.print(tr);
			
		
		//String fil = "/users/charel16/workspace-mars-2015/Library_Books";
		String fil = "C:/Users/HP/workspace/re.txt";
		FileWriter wr = new FileWriter(fil.trim().toString() , true);
		wr.write(tr); 
		wr.write("\r\n");
		wr.close();
	}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
}