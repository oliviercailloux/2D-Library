package io.github.oliviercailloux.y2017.my_2D_library.model;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2017.my_2D_library.model.Author;

public class AuthorTest {

	@Test
	public void testEqual(){
	    Author x = new Author("Victor", "HugoChung");
		Author y = new Author("Victor", "HugoChung");
	    Assert.assertTrue(x.equals(y) && y.equals(x));
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}
}
