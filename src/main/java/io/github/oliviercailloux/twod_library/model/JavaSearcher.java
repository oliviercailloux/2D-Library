package io.github.oliviercailloux.twod_library.model;

import java.util.ArrayList;
import java.util.List;

public interface JavaSearcher {
	/**
	 * 
	 * @param s user search filter
	 * @return list of book who correspond to the search.
	 */
	public List<Book> getResultSearchData(SearchData s);

	/**
	 * 
	 * @param s       user search filter
	 * @param nbLivre limit the serach to nbLivre
	 * @return list of book who correspond to the search <= nbLivre.
	 */
	public ArrayList<Book> getResultSearchDataLimited(SearchData s, String nbLivre);
}
