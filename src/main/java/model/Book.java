package model;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Book is a class that describes a book. It has a title and an author.
 */
public class Book {

	/**
	 * The title of the book.
	 */
	private String title;
	
	/**
	 * The author of the book.
	 */
	private Author author;
	
	/**
	 * Book's constructor with the title and the author of the book.
	 * @param the title of the book
	 * @param the author of the book
	 */
	public Book(String title, Author author) {
		this.title = title;
		this.author = author;
	}
	
	/**
	 * Getter of the title of the book
	 * @return the title of the book
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter of the title of the book
	 * @param the title of the book
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter of the author of the book
	 * @return the author of the book
	 */
	public Author getAuthor() {
		return author;
	}
	
	/**
	 * Setter of the author of the book
	 * @param the author of the book
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

}