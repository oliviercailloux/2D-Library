package io.github.oliviercailloux.twod_library.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class SearchDataTest {

	@Test
	public void test() {
		ArrayList<String> filter = new ArrayList<String>();
		filter.add("mis");
		filter.add("2");
		SearchData s = SearchData.createSearchDataFilter(filter, "tout");

		assertEquals(s.getSearchCriteria(), filter);
		assertEquals(s.getTypeOfSearch(), "tout");
	}

}
