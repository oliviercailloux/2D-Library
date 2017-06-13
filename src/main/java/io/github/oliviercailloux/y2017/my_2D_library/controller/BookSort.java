package io.github.oliviercailloux.y2017.my_2D_library.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2017.my_2D_library.model.Book;

public class BookSort{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BookSort.class);
	
	/**
	 * Return the list of books sorted according to the boolean rising.
	 * if rising is true, then the first Book in the returned list is the older.
	 * @param toSort
	 * @param rising
	 * @return
	 */
	public static List<Book> sortByYear(List<Book> toSort, boolean rising){
		List<Book> res = new ArrayList<Book>();
		for(Book b : toSort){
			if(res.size() != 0){
				for (int count = 0; count <= res.size(); count++) {
					if(count != res.size()){
						Book bRes = res.get(count);
						if (rising) {
							if (compareYear(b, bRes)) {
								res.add(count, b);
							}
						} else {
							if (compareYear(bRes, b)) {
								res.add(count, b);
							}
						}
					} else {
						res.add(b);
					}
				}
			} else {
				res.add(b);
			}
		}
		return res;
	}
	
	/***
	 * Return true if the book 1 is older than the book 2.
	 * @param b1 Book 1 to compare.
	 * @param b2 Book 2 to compare.
	 * @return true if the book 1 is older than the book 2.
	 */
	private static boolean compareYear(Book b1, Book b2){
		return b1.getYear() <= b2.getYear();
	}
	
	/**
	 * Return the list of books sorted by the title (alphabetical order).
	 * @param toSort
	 * @return the list of books sorted
	 */
	public static List<Book> sortByTitle(List<Book> toSort){
		Collections.sort(toSort, new BookCompareByTitle());
		return toSort;
	}
	
	/**
	 * Return the list of books sorted by the last name of their author (alphabetical order).
	 * @param toSort
	 * @return the list of books sorted
	 */
	public static List<Book> sortByAuthor(List<Book> toSort){
		Collections.sort(toSort, new BookCompareByAuthor());
		return toSort;
	}
}

/**
 * A comparator of books by title
 * @author lejema160
 *
 */
class BookCompareByTitle implements Comparator<Book>{
	
	/**
	 * implementation of the compare method of Comparator<>
	 * @param b1 b2 the books we compare
	 * @return an int, less than 0 if b1 is lexically after b2
	 * 					more than 0 if b1 is lexically before b2
	 * 					equal to 0 if b1 is equal to b2
	 */
	@Override
	public int compare(Book b1, Book b2){
		return (b1.getTitle().compareTo(b2.getTitle()));
	}
}

class BookCompareByAuthor implements Comparator<Book>{
	
	/**
	 * implementation of the compare method of Comparator<>
	 * @param b1 b2 the books we compare
	 * @return an int, less than 0 if b1 is lexically after b2
	 * 					more than 0 if b1 is lexically before b2
	 * 					equal to 0 if b1 is equal to b2
	 */
	@Override
	public int compare(Book b1, Book b2){
		return (b1.getAuthor().getLastName().compareTo(b2.getAuthor().getLastName()));
	}
}
