package controler;

import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookC {
	
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
}
