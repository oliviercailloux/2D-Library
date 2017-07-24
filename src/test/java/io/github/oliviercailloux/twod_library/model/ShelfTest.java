package io.github.oliviercailloux.twod_library.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.twod_library.model.Author;
import io.github.oliviercailloux.twod_library.model.Book;
import io.github.oliviercailloux.twod_library.model.Shelf;

public class ShelfTest {
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
		Shelf x = new Shelf(books);
		Shelf y = new Shelf(books);
	    Assert.assertTrue(x.isEqualTo(y) && y.isEqualTo(x));
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}
}
