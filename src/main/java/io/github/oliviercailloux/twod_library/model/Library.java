
package io.github.oliviercailloux.twod_library.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Library is a class that describes the library, with several shelves and
 * books.
 */
public class Library implements JavaSearcher {

	public static final Logger LOGGER = LoggerFactory.getLogger(Library.class);

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
				list = new ArrayList<>();
			}

		}
		return newShelves;
	}

	/**
	 * The Height of the library is define by the size of users's frame
	 */
	private double frameSizeH;

	/**
	 * The Weigth of the library is define by the size of users's frame
	 */
	private double frameSizeW;

	/**
	 * The list of the shelves of the library
	 */
	private List<Shelf> shelves;

	public Library() {
		super();
	}

	/**
	 * Constructor of a library with a list of shelves
	 * 
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

	public double getFrameSizeH() {
		return frameSizeH;
	}

	public double getFrameSizeW() {
		return frameSizeW;
	}

	/***
	 * Return the list of all the books of the library
	 * 
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

	/**
	 * Getter of the list of shelves
	 *
	 * @return the list of shelves of the library
	 */
	public List<Shelf> getShelves() {
		return shelves;
	}

	@Override
	public int hashCode() {
		return Objects.hash(frameSizeH, frameSizeW, shelves);
	}

	public boolean isEqual(Library library) {
		return this.frameSizeH == library.frameSizeH && this.frameSizeW == library.frameSizeW;
		// return this.shelves.equals(library.shelves);
	}

	public void setFrameSizeH(double frameSizeH) {
		this.frameSizeH = frameSizeH;
	}

	public void setFrameSizeW(double frameSizeW) {
		this.frameSizeW = frameSizeW;
	}

	/**
	 * Setter of the list of shelves
	 *
	 * @param a list of shelves
	 */
	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
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
	 * Return the list of books sorted according to the boolean rising. if rising is
	 * true, then the first Book in the returned list is the older.
	 *
	 * @param toSort
	 * @param rising
	 * @return
	 */
	public List<Book> sortByYear(boolean rising) {
		List<Book> toSort = this.getListOfAllTheBooks();
		List<Book> sortedBooks = new ArrayList<>();
		for (Book book : toSort) {
			if (sortedBooks.size() == 0) {
				sortedBooks.add(book);
			} else {
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
	 *
	 * @param s <code>not null</code> set of key word entered by the user.
	 * @return all book which have for author a person who contained at least one or
	 *         several word containing by s.
	 */
	public ArrayList<Book> searchByAuthor(Set<String> s) {
		Preconditions.checkArgument(!s.isEmpty());// We can't do a search if there is no criteria
		ArrayList<Book> bookSearched = new ArrayList<Book>();
		for (int i = 0; i < getListOfAllTheBooks().size(); i++) {
			for (@SuppressWarnings("rawtypes")
			Iterator it = s.iterator(); it.hasNext();) {
				String t = (String) it.next(); // the current key word which is compared to all the firstname and
												// and surename of every author
				if (getListOfAllTheBooks().get(i).getAuthor().getFirstName().toLowerCase().contains(t.toLowerCase())
						|| getListOfAllTheBooks().get(i).getAuthor().getLastName().toLowerCase()
								.contains(t.toLowerCase())) {
					// If firstname or surname contain t add the book
					bookSearched.add(getListOfAllTheBooks().get(i));
				}

			}
		}
		return bookSearched;

	}

	/**
	 *
	 * @param s <code>not null</code> set of key word entered by the user.
	 * @return all book which have for title letter contained in s.
	 */
	public ArrayList<Book> searchByTitle(Set<String> s) {
		Preconditions.checkArgument(!s.isEmpty());// We can't do a search if there is no criteria
		ArrayList<Book> bookSearched = new ArrayList<Book>();
		for (int i = 0; i < getListOfAllTheBooks().size(); i++) {
			for (@SuppressWarnings("rawtypes")
			Iterator it = s.iterator(); it.hasNext();) {
				String t = (String) it.next(); // the current key word which is compared to all the firstname and
				// and surename of every author
				if (getListOfAllTheBooks().get(i).getTitle().toLowerCase().contains(t.toLowerCase())) {
					bookSearched.add(getListOfAllTheBooks().get(i));// If firstname or surname contain t add the book
				}

			}
		}
		return bookSearched;

	}

	/**
	 *
	 * @param s <code>not null</code> set of key word entered by the user.
	 * @return all book which have for date number contained in s.
	 */
	public ArrayList<Book> searchByDate(Set<String> s) {
		Preconditions.checkArgument(!s.isEmpty());// We can't do a search if there is no criteria
		ArrayList<Book> bookSearched = new ArrayList<Book>();
		for (int i = 0; i < getListOfAllTheBooks().size(); i++) {
			for (@SuppressWarnings("rawtypes")
			Iterator it = s.iterator(); it.hasNext();) {
				String t = (String) it.next();
				if (String.valueOf(getListOfAllTheBooks().get(i).getYear()).toLowerCase().contains(t.toLowerCase())) {
					bookSearched.add(getListOfAllTheBooks().get(i));
				}

			}
		}
		return bookSearched;

	}

	@SuppressWarnings("unchecked")
	/**
	 * This function call the good search function according to the user's choice
	 */
	public List<Book> getResultSearchData(SearchData s) {
		Preconditions.checkArgument(!s.getSearchCriteria().isEmpty());// We can't do a search if there is no criteria
		ArrayList<Book> resultSearching = new ArrayList<Book>();
		switch (s.getTypeOfSearch()) {
		case "auteur":
			resultSearching = searchByAuthor(s.getSearchCriteria());
			break;

		case "titre":
			resultSearching = searchByTitle(s.getSearchCriteria());
			break;

		case "date":
			resultSearching = searchByDate(s.getSearchCriteria());
			break;
		case "tout":
			resultSearching = searchByAuthor(s.getSearchCriteria());
			resultSearching.addAll(searchByTitle(s.getSearchCriteria()));
			resultSearching.addAll(searchByDate(s.getSearchCriteria()));
			break;
		}
		return resultSearching;
	}

	@Override
	/**
	 * This function will call getResultSearchData but instead of return every books
	 * find, we will just return the number of book according to user'choice
	 */
	public ArrayList<Book> getResultSearchDataLimited(SearchData s, String nbLivre) {
		Preconditions.checkArgument(!s.getSearchCriteria().isEmpty());
		Preconditions.checkArgument(Integer.parseInt(nbLivre) > 0);
		ArrayList<Book> resultSearching = (ArrayList<Book>) getResultSearchData(s);
		if (Integer.valueOf(nbLivre) > resultSearching.size()) { // We can't dispaly to much book because the search
																	// find less book.
			return resultSearching;
		} else {
			ArrayList<Book> resultSearchingLimited = new ArrayList<Book>();// We use nbLivre to know how much book will
																			// we return
			for (int i = 0; i < Integer.valueOf(nbLivre); i++) {
				resultSearchingLimited.add(resultSearching.get(i));
			}
			return resultSearchingLimited;
		}

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

}