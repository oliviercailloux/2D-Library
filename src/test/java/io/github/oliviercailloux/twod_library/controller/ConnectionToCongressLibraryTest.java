package io.github.oliviercailloux.twod_library.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.github.oliviercailloux.twod_library.controller.ConnectionToCongressLibrary;

/**
 * @author lejema160
 *
 */
public class ConnectionToCongressLibraryTest {

	/***
	 * The REST connection under test
	 */
	ConnectionToCongressLibrary connection;
	
	@Before
	public void setUp(){
		String code = "70108638";
		connection = new ConnectionToCongressLibrary(code);
	}
	
	@Test
	public void getMarcXML_Should_Return_The_Marcxml_In_A_String() {
		String actual = connection.getMarcXML();
		CharSequence s1 = "French profiles.";
		boolean b1 = actual.contains(s1);
		assertTrue(b1);
		CharSequence s2 = "Gosse, Edmund,";
		boolean b2 = actual.contains(s2);
		assertTrue(b2);		
		CharSequence s3 = "[1970]";
		boolean b3 = actual.contains(s3);
		assertTrue(b3);
	}
	
	@Test 
	public void extractData_Should_Returntitle_Author_and_Year_In_A_Tab_Of_String(){
		String[] actual = connection.extractData();
		assertEquals("French profiles.", actual[0]);
		assertEquals("Gosse, Edmund,", actual[1]);
		assertEquals("[1970", actual[2]);
	}

	@After
	public void afterTest(){
		connection = null;
	}

}
