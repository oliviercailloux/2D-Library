package io.github.oliviercailloux.y2017.my_2D_library.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 */
public class LibraryTest {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LibraryTest.class);
	
	Library library;
	
	@Before
	public void setUp() {
		List<Book> books = new ArrayList<>();
		
		Author a1 = new Author("CHUNG", "Hugo");
		Author a2 = new Author("ROWLING", "JK");
		Author a3 = new Author("BAUDELAIRE", "Charles");

		Book b1 = new Book();
		b1.setAuthor(a1);
		b1.setTitle("Une vie de coccinelle");
		b1.setYear(2015);
		books.add(b1);

		Book b2 = new Book();
		b2.setAuthor(a2);
		b2.setTitle("Harry Poopper");
		b2.setYear(2005);
		books.add(b2);

		Book b3 = new Book();
		b3.setAuthor(a3);
		b3.setTitle("Les misérables");
		b3.setYear(1860);
		books.add(b3);
		
		library = new Library(books, 2);
	}

	@Test
	public void testEqual(){
		Library library2 = new Library(library.getListOfAllTheBooks(), 2);
		double size = 3.0;
		library.setFrameSizeH(size);
		library2.setFrameSizeH(size);
		library.setFrameSizeW(size);
		library2.setFrameSizeW(size);
	    assertTrue(library.isEqual(library2) && library2.isEqual(library));
	    assertTrue(library.hashCode() == library2.hashCode());
	}

	@Test
	public void sortByYear_Should_Return_A_Sorted_By_Year_List_Of_Books() {
		List<Book> actual = library.sortByYear(true);
		boolean actualOk = true;
		List<Book> actual2 = library.sortByYear(false);
		boolean actual2Ok = true;
		
		for(int i = 0; i < actual.size()-1; i++){
			if(!actual.get(i).compareYear(actual.get(i+1))) actualOk=false;
		}
		for(int i = 0; i < actual2.size()-1; i++){
			if(actual2.get(i).compareYear(actual2.get(i+1))) actual2Ok = false;
		}
		assertTrue(actualOk);
		assertTrue(actual2Ok);
		
	}
	
	/**
	 * Test method for BookSort.sortByTitle
	 */
	@Test
	public void sortByTitle_Should_Sort_The_Books_By_Title_Alphabetically(){
		List<Book> expected = library.sortByTitle();
		assertEquals(3, expected.size());
		assertEquals("Harry Poopper", expected.get(0).getTitle());
		assertEquals("Les misérables", expected.get(1).getTitle());
		assertEquals("Une vie de coccinelle", expected.get(2).getTitle());
	}
	
	/**
	 * Test method for BookSort.sortByAuthor
	 */
	@Test
	public void sortByAuthor_Should_Sort_The_Books_By_Author_Alphabetically(){
		List<Book> expected = library.sortByAuthor();
		assertEquals(3, expected.size());
		assertEquals("BAUDELAIRE", expected.get(0).getAuthor().getLastName());
		assertEquals("CHUNG", expected.get(1).getAuthor().getLastName());
		assertEquals("ROWLING", expected.get(2).getAuthor().getLastName());
	}
	
	@After
	public void afterTest(){
		library = null;
	}

}
