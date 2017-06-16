package io.github.oliviercailloux.y2017.my_2D_library.model;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2017.my_2D_library.model.Author;
import io.github.oliviercailloux.y2017.my_2D_library.model.Book;

public class BookTest {
	@Test
	public void testEqual(){
	    Author olympie = new Author("Olympie", "Suquet");
		Book x = new Book();
		x.setAuthor(olympie);
		x.setTitle("Titre popo");
		x.setYear(2014);
		x.setheight(3);
		x.setwidth(3);
		x.setColor(Color.BLUE);
		Book y = new Book();
		y.setAuthor(olympie);
		y.setTitle("Titre popo");
		y.setYear(2014);
		y.setheight(3);
		y.setwidth(3);
		y.setColor(Color.BLUE);

	    Assert.assertTrue(x.isEqualTo(y) && y.isEqualTo(x));
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}
}
