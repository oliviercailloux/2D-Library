package io.github.oliviercailloux.y2017.my_2D_library.main;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2017.my_2D_library.view.SVGLibrary;
import io.github.oliviercailloux.y2017.my_2D_library.view.Window2DLibrary;

public class Main {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, ParserConfigurationException {
		SVGLibrary svgLibrary = null;	
		new Window2DLibrary("2D_LIBRARY PROJECT", svgLibrary);
	}

}