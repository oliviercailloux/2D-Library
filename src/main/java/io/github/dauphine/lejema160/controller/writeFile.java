package io.github.dauphine.lejema160.controller;

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
					
					System.out.print("Enter " + Tabl[i] + " :\n");
					x = input.nextLine();
					if (x=="\n"){
						x = ",";
					}
					if (tr != null && !tr.trim().isEmpty()){
					tr = tr + ","+ x;
					}
					else {tr = x;}
				}
				input.close();
				tr = tr + ",End";
				System.out.print(tr);
			
		
		//String fil = "/users/charel16/workspace-mars-2015/Library_Books";
		
		String fil = "/users/charel16/git/2D_library/src/main/resources/controller/Book1.csv";
		FileWriter wr = new FileWriter(fil.trim().toString() , true);
		wr.append(tr);
		wr.append("\r\n");
		wr.close();
	}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
}