package controler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class readFile {
	public static void main(String[] args) {
		read();
	}

	private static void read() {
		ArrayList<Book> books = new ArrayList<Book>();

		try {

			String path = "/users/charel16/workspace-mars-2015/Library_Books";

			File file = new File(path);

			Scanner input = new Scanner(file);

			while (input.hasNextLine()) {
				String line = input.nextLine();
				Book book = new Book();

				//System.out.println(line);
			
				String[] words = line.split(";");
				for (int position = 0; position < words.length ; position++) {
					String word= "";
					word = words[position].trim();
					setBookAttribute(book, position, word);
					
				}
				books.add(book);
			}
			input.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		for(Book book : books){
			System.out.println(book.toString());
		}
	}
	private static void setBookAttribute(Book book, int position, String word){
		switch(position){
			case 0:
				book.setLastName(word);
				break;
			case 1:
				book.setFirstName(word);
				break;
			case 2:
				book.setTitle(word);
				break;
			case 3:
				book.setYear(word);
				break;
			}
	}

}
class Book{
	private String lastName;
	private String firstName;
	private String title;
	private int year;
	
	public Book(){
		lastName = "";
		firstName = "";
		title = "";
		year = 0;
	}
	public void setLastName(String word){
		lastName = word;
	}
	public void setFirstName(String word){
		firstName = word;
	}
	public void setTitle(String word){
		title = word;
	}
	public void setYear(String word){
		year = Integer.parseInt(word);
	}
	public String toString(){
		String res = lastName + " " + firstName + " : " + title + "   " + year; 
		return res;
	}
}