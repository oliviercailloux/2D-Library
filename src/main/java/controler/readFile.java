package controler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import model.Author;
import model.Book;

public class readFile {
	
	/**
	 * Read the file in resources to get a list of the books
	 * @return the books in the file
	 * @throws IOException Exception launched under file or scanner
	 */
	public static List<Book> read() throws IOException{
		List<Book> books = new ArrayList<Book>();
		
		InputStream inputStream = readFile.class.getResourceAsStream("LibraryBooks.txt");
		if(inputStream == null){
			return books;
			// -> exception 
		}
		// -> bytes
		
//		int justRead = inputStream.available();
//		System.out.println(justRead);
		try (BufferedReader input = new BufferedReader (new InputStreamReader(inputStream, "UTF-8"))){
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
	}

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
		}
	}

}