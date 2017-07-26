package io.github.oliviercailloux.twod_library.controller;

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

import io.github.oliviercailloux.twod_library.model.Author;
import io.github.oliviercailloux.twod_library.model.Book;

public class DataFile {

	public static final Logger LOGGER = LoggerFactory.getLogger(DataFile.class);

	private String booksFilePath;

	public DataFile() {
		String path = new File("").getAbsolutePath();
		booksFilePath = path + "/src/main/resources/controller/Books.csv";
	}

	/***
	 * Add a line to the csv file
	 * 
	 * @param line
	 */
	public void addLine(String line) {
		try (FileWriter wr = new FileWriter(booksFilePath.trim().toString(), true)) {
			// FileWriter wr = new FileWriter(booksFilePath.trim().toString(), true);
			wr.append(line);
			wr.append("\r\n");
			wr.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Delete he line in the csv file
	 * 
	 * @param lineToDelete
	 */
	public void deleteLine(String lineToDelete) {

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
				LOGGER.debug(lines.toString());
			}

			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		addNotDeletedLines(lines);
	}

	public String getBooksFilePath() {
		return booksFilePath;
	}

	/***
	 *
	 * @return the list of books include in the csv file
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
				// LOGGER.info("bla : " + value);
				book.setBookAttribute(columnNo, value);
				columnNo++;

			}
			liste.add(book);
			lineNo++;
		}
		return liste;
	}

	/**
	 * Add all the lines in the csv file (these are the books that were not deleted)
	 * 
	 * @param lines
	 */
	private void addNotDeletedLines(List<List<String>> lines) {
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
				LOGGER.debug(li);
				fw.write(li);
				fw.write("\r\n");

			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}