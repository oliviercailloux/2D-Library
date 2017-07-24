package io.github.oliviercailloux.twod_library.model;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.twod_library.model.Author;

public class AuthorTest {

	@Test
	public void testEqual(){
	    Author x = new Author("Victor", "HugoChung");
		Author y = new Author("Victor", "HugoChung");
	    Assert.assertTrue(x.equals(y) && y.equals(x));
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}
}
