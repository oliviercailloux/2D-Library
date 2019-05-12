package io.github.oliviercailloux.twod_library.model;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import io.github.oliviercailloux.twod_library.model.Author;
import io.github.oliviercailloux.twod_library.model.Book;

public class BookTest {

	@Test
	public void testEqual() {
		Author olympie = new Author("Olympie", "Suquet");
		Book x = new Book();
		x.setAuthor(olympie);
		x.setTitle("Titre popo");
		x.setYear(2014);
		x.setHeight(3);
		x.setWidth(3);
		x.setColor(Color.BLUE);
		Book y = new Book();
		y.setAuthor(olympie);
		y.setTitle("Titre popo");
		y.setYear(2014);
		y.setHeight(3);
		y.setWidth(3);
		y.setColor(Color.BLUE);

		assertTrue(x.isEqualTo(y) && y.isEqualTo(x));
		assertTrue(x.hashCode() == y.hashCode());
	}

	@Test
	public void compareBooksTest() {
		Author olympie = new Author("Olympie", "Suquet");
		Book x = new Book();
		x.setAuthor(olympie);
		x.setTitle("Titre popo");
		x.setYear(2014);
		x.setHeight(3);
		x.setWidth(3);
		x.setColor(Color.BLUE);
		Book y = new Book();
		y.setAuthor(olympie);
		y.setTitle("Titre popo");
		y.setYear(2015);
		y.setHeight(3);
		y.setWidth(3);
		y.setColor(Color.BLUE);

		assertTrue(x.compareYear(y));
	}
}
