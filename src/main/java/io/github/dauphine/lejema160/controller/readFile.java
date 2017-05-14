package io.github.dauphine.lejema160.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.dauphine.lejema160.model.Author;
import io.github.dauphine.lejema160.model.Book;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class readFile {
	
	/**
	 * Read the file in resources to get a list of the books
	 * @return the books in the file
	 * @throws IOException Exception launched under file or scanner
	 */
	public static List<Book> read() {
		List<Book> books = new ArrayList<Book>();
		
		
		String csvFile = "Book1.csv";
        BufferedReader br = null;
        String cvsSplitBy = ",";
        String line = "";
        
		/*InputStream inputStream = readFile.class.getResourceAsStream("LibraryBooks.txt");
		if(inputStream == null){
			return books;
			}*/
        
			// -> exception 
		
		// -> bytes

		
		 try {
			 	
		
	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                Book book = new Book();
					Author author = new Author("", "");
					book.setAuthor(author);
					String[] words = line.split(cvsSplitBy);
					
					for(int k = 0; k < words.length; k++){
						if (words.length <= 6 && words[k] != "") {
							words[k]="";
						}
					}
					
					for (int position = 0; position < words.length; position++) {
						
						String word = "";
						if (words[position] != ""){
						word = words[position].trim();
						} else word = words[position];
						
						setBookAttribute(book, position, word);

					}
					books.add(book);
				
					line = br.readLine();
				}
			
	            

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		 return books;
	    }

		/*try (BufferedReader input = new BufferedReader (new InputStreamReader(inputStream, "UTF-8"))){
			int i = 1;
			String line = input.readLine();
			while (line != null) {
				Book book = new Book();
				Author author = new Author("", "");
				book.setAuthor(author);
				String[] words = line.split(";");
				for (int position = 0; position < words.length; position++) {
					
					String word = "";
					word = words[position].trim();
					setBookAttribute(book, position, word);

				}
				books.add(book);
				i++;
				line = input.readLine();
			}
		}
		return books;
	}*/

	/**
	 * Set the right argument of the book depending on position of the word in the file
	 * @param book Book to set
	 * @param position position of the word in the file
	 * @param word word found at the position
	 */
	private static void setBookAttribute(Book book, int position, String word) {
		
		switch (position) {
		case 0:
			book.getAuthor().setLastName(word);
			break;
		case 1:
			book.getAuthor().setFirstName(word);
			break;
		case 2:
			book.setTitle(word);
			break;
		case 3:
			int year = Integer.parseInt(word);
			book.setYear(year);
			break;
		case 4:
			if (word == ""){
				book.generatesizeX();
			}
			else {
			int dimx = Integer.parseInt(word);
			book.setwidth(dimx);
			}
			break;
		case 5:
			if (word == ""){
				book.generatesizeY();
			}
			else {
			int dimy = Integer.parseInt(word);
			book.setheight(dimy);}
			break;
		case 6:
			switch(word){
			case "":
				book.generatecolor();
				break;
			case "rose":
				book.setColor(Color.pink);
				break;
			case "cyan":
				book.setColor(Color.CYAN);
				break;
			case "bleu":
				book.setColor(Color.BLUE);
				break;
			case "orange":
				book.setColor(Color.ORANGE);
				break;
			case "jaune":
				book.setColor(Color.yellow);
				break;
			}
			break;
		}
	}

}
