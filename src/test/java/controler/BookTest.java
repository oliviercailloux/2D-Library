package controler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Book;

import org.junit.Test;

public class BookTest {

	@Test
	public void sortByYear_Should_Return_A_Sorted_By_Year_List_Of_Books() {
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
		
		// act
		List<Book> actual = new ArrayList<>();
		actual = BookC.sortByYear(actual, true);
		
		List<Book> actual2 = new ArrayList<>();
		actual2 = BookC.sortByYear(actual2, false);
		
		//assert
		for(int i = 0; i < actual.size(); i++){
			if(i != actual.size()-1){
				if(actual.get(i).getYear() <= actual.get(i+1).getYear()){
					// good
				} else {
					fail("Not sorted as expected");
				}
			}
		}
		for(int i = 0; i < actual2.size(); i++){
			if(i != actual.size()-1){
				if(actual.get(i).getYear() >= actual.get(i+1).getYear()){
					// good
				} else {
					fail("Not sorted as expected");
				}
			}
		}
	}

}
