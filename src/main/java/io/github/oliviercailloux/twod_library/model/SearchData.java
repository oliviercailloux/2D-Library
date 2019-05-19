package io.github.oliviercailloux.twod_library.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

public class SearchData {
	@SuppressWarnings("rawtypes")
	private Set searchCriteria; // containing whole informations entered by the user( the title of the book,
								// name of the author ...)
	private String typeOfSearch; // If we search by author or by title or by date...

	private <E> SearchData(ArrayList<E> searchCriteria, String TypeOfSearch) {
		Preconditions.checkNotNull(searchCriteria);
		this.setSearchCriteria(new HashSet<E>(searchCriteria));
		this.setTypeOfSearch(TypeOfSearch);

	}

	/**
	 * 
	 * @param searchCriteria the list of user criteria for the search
	 * @param typeOfSearch   the type of the research wanted by the user
	 * @return a new SearchData object
	 */
	public static SearchData createSearchDataFilter(ArrayList<String> searchCriteria, String typeOfSearch) {
		return new SearchData(searchCriteria, typeOfSearch);
	}

	@SuppressWarnings("rawtypes")
	public <E> Set getSearchCriteria() {
		return searchCriteria;
	}

	public <E> void setSearchCriteria(Set<E> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String toString() {
		String message = "User search filter : \n";
		for (Object entry : getSearchCriteria()) {
			message += entry + "\n";
		}
		message += "Type of search : \n" + getTypeOfSearch();
		return message;
	}

	public String getTypeOfSearch() {
		return typeOfSearch;
	}

	public void setTypeOfSearch(String typeOfSearch) {
		this.typeOfSearch = typeOfSearch;
	}

}
