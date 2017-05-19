package io.github.dauphine.lejema160.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.dauphine.lejema160.model.Author;
import io.github.dauphine.lejema160.model.Book;

public class readFile {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(readFile.class);

    public static void main(String[] args) {
    	read();
    }
    
    	public static List<Book> read() {
        String fileName= "/users/suquol13/git/2D_library/src/main/resources/controller/Book1.csv";
        File file= new File(fileName);
        
        List<Book> liste = new ArrayList<>();
        
        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // the following code lets you iterate through the 2-dimensional array
        int lineNo = 0;
        for(List<String> line: lines) {
            int columnNo = 0;
            if (lineNo == 0){
            	lineNo++;
            	continue;
            }
            Book book = new Book();
			Author author = new Author("", "");
			book.setAuthor(author);
            
            for (String value: line) {
            	if (value == "" || value.isEmpty()){
            		value = "";
            	}
            	setBookAttribute(book, columnNo, value);
                System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                columnNo++;
                
            }liste.add(book);
            lineNo++;
        }
        return liste;
    }
private static void setBookAttribute(Book book, int position, String word) {
		
		switch (position) {
		case 0:
			book.getAuthor().setLastName(word);
			break;
		case 1:
			book.getAuthor().setFirstName(word);
			break;
		case 2:
			book.setTitle(word);
			break;
		case 3:
			int year = Integer.parseInt(word);
			book.setYear(year);
			break;
		case 4:
			if (word == ""){
				book.generatesizeX();
			}
			else {
			int dimx = Integer.parseInt(word);
			book.setwidth(dimx);
			}
			break;
		case 5:
			if (word == ""){
				book.generatesizeY();
			}
			else {
			int dimy = Integer.parseInt(word);
			book.setheight(dimy);}
			break;
		case 6:
			switch(word){
			case "":
				book.generatecolor();
				break;
			case "rose":
				book.setColor(Color.pink);
				break;
			case "cyan":
				book.setColor(Color.CYAN);
				break;
			case "bleu":
				book.setColor(Color.BLUE);
				break;
			case "orange":
				book.setColor(Color.ORANGE);
				break;
			case "jaune":
				book.setColor(Color.yellow);
				break;
			}break;
		case 7 :
			if (word == "End"){
				break;
			}
			
		}
	}


}