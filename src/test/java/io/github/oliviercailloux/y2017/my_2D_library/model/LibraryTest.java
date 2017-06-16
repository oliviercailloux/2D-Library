package io.github.oliviercailloux.y2017.my_2D_library.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2017.my_2D_library.model.Author;
import io.github.oliviercailloux.y2017.my_2D_library.model.Book;
import io.github.oliviercailloux.y2017.my_2D_library.model.Library;

/**
 * @author lejema160
 * @author OlympieSuquet
 *
 */
public class LibraryTest {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LibraryTest.class);
	
	Library library;
	
	public LibraryTest() {
		library = new Library();
	}

	@Test
	public void testEqual(){
	    Author olympie = new Author("Olympie", "Suquet");
		Author merlene = new Author("Merlène", "Lejeune");
		Book book1 = new Book();
		book1.setAuthor(olympie);
		book1.setTitle("Titre popo");
		book1.setYear(2014);
		Book book2 = new Book();
		book2.setAuthor(olympie);
		book2.setTitle("tutitre pam");
		book2.setYear(202);
		Book book3 = new Book();
		book3.setAuthor(merlene);
		book3.setTitle("Tuto beauté");
		book3.setYear(2506);
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		books.add(book3);
		int nbBooksPerShelf = 2;
		Library x = new Library(books, nbBooksPerShelf);
		Library y = new Library(books, nbBooksPerShelf);
		double size = 3.0;
		x.setFrameSizeH(size);
		y.setFrameSizeH(size);
		x.setFrameSizeW(size);
		y.setFrameSizeW(size);
		System.out.println(x.toString() + x.getFrameSizeH() + x.getFrameSizeW());
		System.out.println(y.toString());
	    Assert.assertTrue(x.isEqual(y) && y.isEqual(x));
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}



}
