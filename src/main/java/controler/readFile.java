package controler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;


import model.Book;

public class readFile {

	public static List<Book> read() throws IOException{
		List<Book> books = new ArrayList<Book>();
		
		URL resourceURL = readFile.class.getResource("LibraryBooks.txt");
		
		if(resourceURL == null){
			return books;
		}

		File file = new File("");
		
		FileUtils.copyURLToFile(resourceURL, file);

		try (Scanner input = new Scanner(file)){

			while (input.hasNextLine()) {
				String line = input.nextLine();
				Book book = new Book();

				String[] words = line.split(";");
				for (int position = 0; position < words.length; position++) {
					String word = "";
					word = words[position].trim();
					setBookAttribute(book, position, word);
				}
				books.add(book);
			}
		}
		return books;
	}

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
		}
	}

}