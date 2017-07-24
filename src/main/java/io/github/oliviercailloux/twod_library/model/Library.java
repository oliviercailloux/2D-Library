

package io.github.oliviercailloux.twod_library.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Library is a class that describes the library, with several shelves and books.
 */
public class Library {

	public static final Logger LOGGER = LoggerFactory.getLogger(Library.class);

	/**
	 * The list of the shelves of the library
	 */
	private List<Shelf> shelves;
	/**
	 * The Height of the library is define by the size of users's frame
	 */
	private double frameSizeH;
	/**
	 * The Weigth of the library is define by the size of users's frame
	 */
	private double frameSizeW;

	/**
	 * Constructor of a library with a list of shelves
	 * @param books
	 * @param nbBooksPerShelf
	 */

	public Library(List<Book> books, int nbBooksPerShelf) {
		this.shelves = createLibrary(books, nbBooksPerShelf);
		Toolkit atk = Toolkit.getDefaultToolkit();
		Dimension dim = atk.getScreenSize();
		int w = dim.width;
		this.frameSizeW = w;
	}

	public Library() {
		super();
	}

	public double getFrameSizeH() {
		return frameSizeH;
	}

	public void setFrameSizeH(double frameSizeH) {
		this.frameSizeH = frameSizeH;
	}

	public double getFrameSizeW() {
		return frameSizeW;
	}

	public void setFrameSizeW(double frameSizeW) {
		this.frameSizeW = frameSizeW;
	}

	/**
	 * Getter of the list of shelves
	 * 
	 * @return the list of shelves of the library
	 */
	public List<Shelf> getShelves() {
		return shelves;
	}

	/**
	 * Setter of the list of shelves
	 * 
	 * @param a
	 *            list of shelves
	 */
	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}

	/**
	 * 
	 * @param books
	 * @param nbBooksPerShelf
	 * @return a list of shelves using the list of Books in argument
	 */
	public static List<Shelf> createLibrary(List<Book> books, int nbBooksPerShelf) {
		List<Shelf> newShelves = new ArrayList<>();
		List<Book> list = new ArrayList<>();
		for (int index = 0; index < books.size(); index++) {
			list.add(books.get(index));
			if ((index + 1) % nbBooksPerShelf == 0 || index + 1 == books.size()) {
				newShelves.add(new Shelf(list));
				list = new ArrayList<Book>();
			}

		}
		return newShelves;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Library [shelves=" + shelves.toString() + "]";
	}

	public int hashCode() {
		return Objects.hash(frameSizeH, frameSizeW, shelves);
	}

	public boolean isEqual(Library library) {
		return this.frameSizeH == library.frameSizeH && this.frameSizeW == library.frameSizeW;
		// return this.shelves.equals(library.shelves);
	}

	/**
	 * Return the list of books sorted according to the boolean rising. if
	 * rising is true, then the first Book in the returned list is the older.
	 * 
	 * @param toSort
	 * @param rising
	 * @return
	 */
	public List<Book> sortByYear(boolean rising) {
		List<Book> toSort = this.getListOfAllTheBooks();
		List<Book> sortedBooks = new ArrayList<Book>();
		for (Book book : toSort) {
			if (sortedBooks.size() == 0)
				sortedBooks.add(book);
			else {
				int size = sortedBooks.size();
				boolean alreadyAdded = false;
				for (int index = 0; index <= size; index++) {
					if (!alreadyAdded) {
						if (index != size) {
							Book bookToCompare = sortedBooks.get(index);
							if (rising) {
								if (book.compareYear(bookToCompare)) {
									sortedBooks.add(index, book);
									alreadyAdded = true;
								}
							} else {
								if (bookToCompare.compareYear(book)) {
									sortedBooks.add(index, book);
									alreadyAdded = true;
								}
							}
						} else {
							sortedBooks.add(book);
							alreadyAdded = true;
						}
					}
				}
			}
		}
		return sortedBooks;
	}

	/**
	 * Return the list of books sorted by the title (alphabetical order).
	 * 
	 * @param toSort
	 * @return the list of books sorted
	 */
	public List<Book> sortByTitle() {
		List<Book> toSort = this.getListOfAllTheBooks();
		Collections.sort(toSort, new BookCompareByTitle());
		return toSort;
	}

	/**
	 * Return the list of books sorted by the last name of their author
	 * (alphabetical order).
	 * 
	 * @param toSort
	 * @return the list of books sorted
	 */
	public List<Book> sortByAuthor() {
		List<Book> toSort = this.getListOfAllTheBooks();
		Collections.sort(toSort, new BookCompareByAuthor());
		return toSort;
	}

	/***
	 * Return the list of all the books of the library
	 * @return the list of all the books
	 */
	public List<Book> getListOfAllTheBooks() {
		List<Book> result = new ArrayList<>();
		for (Shelf shelf : shelves) {
			for (Book book : this.getShelves().get(shelves.indexOf(shelf)).getBooks()) {
				result.add(book);
			}
		}
		return result;
	}

}