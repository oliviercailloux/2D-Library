package io.github.dauphine.lejema160.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AuthorTest {

	@Test
	public void testEqual(){
	    Author x = new Author("Victor", "HugoChung");
		Author y = new Author("Victor", "HugoChung");
	    Assert.assertTrue(x.equals(y) && y.equals(x));
	    System.out.println(x.hashCode());
	    System.out.println(y.hashCode());
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}
}
