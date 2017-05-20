package io.github.dauphine.lejema160.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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

	    Assert.assertTrue(x.equals(y) && y.equals(x));
	    //Assert.assertTrue(x.hashCode() == y.hashCode());
	}
}
