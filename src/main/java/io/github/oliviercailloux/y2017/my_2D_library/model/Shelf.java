/**
 * 
 */
package io.github.oliviercailloux.y2017.my_2D_library.model;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shelf is the class that describes  a shelf of books in the library
 */
public class Shelf {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Shelf.class);

	/**
	 * The list of books in the shelf
	 */
	private List<Book> books;
	/**
	 * The height of the shelf
	 */
	private double heightSize; 
	
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
	
	/**
	 * Getter of the height of the shelf
	 * @return the size
	 */
	public double getHeightSize() {
		return heightSize;
	}
	/**
	 * Setter of the height of the shelf
	 * @param a height
	 */
	public void setHeightSize(double heightSize) {
		this.heightSize = heightSize;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Shelf [books=" + books.toString() + "]";
	}
	
	public int hashCode(){
		return Objects.hash(books, heightSize);
	}
	
	public boolean isEqualTo(Object shelf){
		if (shelf == null) return false;
		if (shelf == this) return true;
		if (!(shelf instanceof Shelf)) return false;
		Shelf castedShelf = (Shelf) shelf;
		return this.books.equals(castedShelf.books) && this.heightSize == castedShelf.heightSize;
	}


}