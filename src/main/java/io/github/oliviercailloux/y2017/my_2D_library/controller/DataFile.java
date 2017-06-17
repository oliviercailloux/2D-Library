package io.github.oliviercailloux.y2017.my_2D_library.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2017.my_2D_library.model.Author;
import io.github.oliviercailloux.y2017.my_2D_library.model.Book;

public class DataFile {

	public static final Logger LOGGER = LoggerFactory.getLogger(DataFile.class);

	private String booksFilePath;
	
	public DataFile(){
		String path = new File("").getAbsolutePath();
		booksFilePath = path + "/src/main/resources/controller/Books.csv";
	}
	
	public String getBooksFilePath() {
		return booksFilePath;
	}

	/***
	 * 
	 * @return
	 */
	public List<Book> read() {

		File booksFile = new File(booksFilePath);
		
		List<Book> liste = new ArrayList<>();

		// this gives you a 2-dimensional array of strings
		List<List<String>> lines = new ArrayList<>();
		Scanner inputStream;

		try {
			inputStream = new Scanner(booksFile);

			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				// inputStream.useDelimiter(",");
				String[] values = line.split(",");
				// this adds the currently parsed line to the 2-dimensional
				// string array
				if (!line.isEmpty()) {
					lines.add(Arrays.asList(values));
				}
			}

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// the following code lets you iterate through the 2-dimensional array
		int lineNo = 0;
		for (List<String> line : lines) {

			int columnNo = 0;
			if (lineNo == 0) {
				lineNo++;
				continue;
			}
			Book book = new Book();
			Author author = new Author("", "");
			book.setAuthor(author);

			for (String value : line) {

				if (value == "" || value.isEmpty()) {
					value = "";
				}
				System.out.println(value);
				book.setBookAttribute(columnNo, value);
				columnNo++;

			}
			liste.add(book);
			lineNo++;
		}
		return liste;
	}
	
	/***
	 * 
	 * @param line
	 */
	public void addNewBook(String line) {
		try {
			FileWriter wr = new FileWriter(booksFilePath.trim().toString(), true);
			wr.append(line);
			wr.append("\r\n");
			wr.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param lineToDelete
	 */
	public void deleteBook(String lineToDelete) {

		File file = new File(booksFilePath);

		List<List<String>> lines = new ArrayList<>();
		Scanner inputStream;

		try {
			inputStream = new Scanner(file);

			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();

				String[] values = line.split(",");
				String[] las = lineToDelete.split(",");
				if (values[0].equals(las[0]) && values[1].equals(las[1]) && values[2].equals(las[2])
						&& values[3].equals(las[3])) {
					continue;
				} else {
					lines.add(Arrays.asList(values));
				}
				System.out.println(lines);
			}

			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		add(lines);
	}
	
	/**
	 * 
	 * @param lines
	 */
	private void add(List<List<String>> lines) {
		FileWriter fw;
		try {
			fw = new FileWriter(booksFilePath, false);

			for (List<String> line : lines) {
				String li = "";
				for (String value : line) {
					if (li != null && !li.trim().isEmpty()) {
						li = li + "," + value;
					} else {
						li = value;
					}
				}
				System.out.println(li);
				fw.write(li);
				fw.write("\r\n");

			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}