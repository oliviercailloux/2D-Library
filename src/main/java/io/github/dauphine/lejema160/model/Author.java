package io.github.dauphine.lejema160.model;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Author is a class that describes the author of a book.
 */
public class Author {

	/**
	 * The author's first name.
	 */
	private String firstName;
	
	/**
	 * The author's last name.
	 */
	private String lastName;
	
	/**
	 * Author's constructor.
	 * @param author's first name
	 * @param author's last name
	 */
	public Author(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	/**
	 * Author's first name getter
	 * @return author's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Author's first name setter
	 * @param author's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Author's last name getter
	 * @return author's last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Author's last name setter
	 * @param author's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}