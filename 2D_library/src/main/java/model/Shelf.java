/**
 * 
 */
package model;

import java.util.List;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Shelf is the class that describes  a shelf of books in the library
 */
public class Shelf {

	/**
	 * The list of books in the shelf
	 */
	private List<Book> books;
	
	/**
	 * Shelf constructor with a list of books
	 * @param a list of books
	 */
	public Shelf(List<Book> books) {
		this.books = books;
	}

	/**
	 * Getter of the list of books in the shelf
	 * @return the list of books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Setter of the list of books in the shelf
	 * @param a list of books
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

}