package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Library is a class that describes the library, with several shelves and books.
 */
public class Library {

	/**
	 * The list of the shelves of the library
	 */
	private List<Shelf> shelves;
	
	/**
	 * Constructor of a library with a list of shelves
	 */
	public Library(List<Shelf> shelves) {
		this.shelves = shelves;
	}
	
	/**
	 * Constructor of a library, filled from a list of books and a number of books per shelf
	 * @param a list of books
	 * @param booksPerShelf, the maximum number of books per shelf in the library
	 */
	public Library(List<Book> books, int booksPerShelf){
		 List<Shelf> newShelves= new ArrayList<Shelf>();
		 List<Book> list = new ArrayList<Book>();
		 for(int index = 1; index <= books.size(); index++){
			 list.add(books.get(index-1));
			 if ( index%(booksPerShelf) == 0 || index == books.size()){
				 newShelves.add(new Shelf(list));
				 list = new ArrayList<Book>();
			 }
		 }
		 this.shelves = newShelves;
	}

	/**
	 * Getter of the list of shelves
	 * @return the list of shelves of the library
	 */
	public List<Shelf> getShelves() {
		return shelves;
	}
	
	/**
	 * Setter of the list of shelves
	 * @param a list of shelves
	 */
	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}

}