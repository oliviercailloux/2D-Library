package controler;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import model.Author;
import model.Book;

public class readFileTest {

	@Test
	public void readFile_Should_Return_A_List_Of_Books_When_Called() {
		//arrange
		List<Book> expected = new ArrayList<>();
		
		Author a1 = new Author("CHUNG", "Hugo");
		Author a2 = new Author("ROWLING", "JK");
		Author a3 = new Author("BAUDELAIRE", "Charles");
		
		Book b1 = new Book();
		b1.setAuthor(a1);
		b1.setTitle("Une vie de coccinelle");
		b1.setYear(2015);
		expected.add(b1);
		
		Book b2 = new Book();
		b2.setAuthor(a2);
		b2.setTitle("Harry Poopper");
		b2.setYear(2005);
		expected.add(b2);
		
		Book b3 = new Book();
		b3.setAuthor(a3);
		b3.setTitle("Les misérables");
		b3.setYear(1860);
		expected.add(b3);
		
		//act
		List<Book> actual = new ArrayList<>();
		try {
			 actual = readFile.read();
		} catch (IOException e) {
			fail("IOException "+e);
		}
		
		//assert
		Assertions
		.assertThat(actual)
		.hasSize(3)
		.contains(b1, b2, b3);
	}

}
