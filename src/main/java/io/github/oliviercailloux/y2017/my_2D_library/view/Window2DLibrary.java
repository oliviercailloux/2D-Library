package io.github.oliviercailloux.y2017.my_2D_library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2017.my_2D_library.controller.ConnectionToCongressLibrary;
import io.github.oliviercailloux.y2017.my_2D_library.controller.DataFile;
import io.github.oliviercailloux.y2017.my_2D_library.model.Book;
import io.github.oliviercailloux.y2017.my_2D_library.model.Library;

public class Window2DLibrary extends JFrame {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Window2DLibrary.class);
	private static final long serialVersionUID = 1L;
	private JButton generateButton;
	private JTextField searchTextField, firstNameTextField, lastNameTextField, titleTextField,
			yearTextField, dimXTextField, dimYTextField;
	private JFormattedTextField numberBooksPerShelfTextField;
	private JPanel pCenter, addBookJPanel, optionsJPanel;
	JPanel pDCenter;
	JTabbedPane tabPane;
	private ImageIcon myLibIcon;
	private JRadioButton bDarkB, bLightB, bAutoB, bLeanS, bNotLeanS, bDarkBk, bLightBk, bAutoBk, bDarkS, bLightS,
			bAutoS, sortAutoButton;
	JRadioButton sortYearButton;
	JRadioButton sortAuthorButton;
	JRadioButton sortTitleButton;
	private JCheckBox sortAscendingYearButton;
	String bookColor = "Auto";
	String backgroundColor = "Auto";
	String shelfColor = "Auto";
	String sort = "Auto";
	boolean leaning = true;
	int nbBooksPerShelf = 18;
	DataFile dataFile = new DataFile();
	SVGLibrary svgLibrary;

	/**
	 * constructor of the window
	 *
	 * @param title
	 */
	public Window2DLibrary(String title, SVGLibrary svgLibrary2) {

		super(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.dimension();
		this.initialise();
		this.setVisible(true);
		this.svgLibrary = svgLibrary2;

	}

	/**
	 * function that sets the dimensions of the window takes by default the
	 * dimensions of the screen
	 */
	public void dimension() {
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolKit.getScreenSize();
		int height = dimension.height;
		int width = dimension.width;
		setSize(width, height);

	}

	/**
	 * creates the panels of the window
	 */
	public void initialise() {
		Container container = this.getContentPane();
		container.add(this.getSouthPanel(), BorderLayout.SOUTH);
		container.add(this.getNorthPanel(), BorderLayout.NORTH);
		container.add(this.getCenterPanel(), BorderLayout.CENTER);
	}

	/**
	 * creates north panel : title
	 *
	 * @return
	 */
	public JPanel getNorthPanel() {
		JPanel northPanel = new JPanel();
		JPanel headPanel = new JPanel();
		JLabel welcomeLabel = new JLabel("Welcome to your Library :-)  ");
		JTextField presentation = new JTextField(10);
		presentation.setEditable(false);
		presentation.setVisible(false);
		northPanel.add(headPanel);
		headPanel.add(welcomeLabel);
		headPanel.add(presentation);
		return northPanel;
	}

	/**
	 * creates south panel : button to generateButton the library
	 *
	 * @return
	 */
	public JPanel getSouthPanel() {
		JPanel southPanel = new JPanel();
		generateButton = new JButton("Generate my library");
		generateButton.addActionListener(new GenerateButtonListener());
		southPanel.add(generateButton);
		return southPanel;
	}

	/**
	 * creates center panel : options and layout of the library
	 *
	 * @return
	 */
	public JTabbedPane getCenterPanel() {
		JTabbedPane tabPane = new JTabbedPane();
		JPanel panOptions = new JPanel(new BorderLayout());
		JTextArea options = new JTextArea();
		panOptions.setBackground(Color.orange);
		options.setFont(new Font("Arial", Font.BOLD, 60));
		panOptions.add(options, "Center");
		tabPane.add("Options for my library", getCenterPanelOptions());
		tabPane.add("Options for my library", new JScrollPane(optionsJPanel));

		JPanel panBooks = new JPanel(new BorderLayout());
		JTextArea visuG = new JTextArea();
		visuG.setFont(new Font("Arial", Font.BOLD, 60));
		panBooks.add(visuG, "Center");
		tabPane.add("My Library", getPanelCentreLib());

		JPanel panActions = new JPanel(new BorderLayout());
		JTextArea actions = new JTextArea();
		panActions.setBackground(Color.orange);
		actions.setFont(new Font("Arial", Font.BOLD, 60));
		panActions.add(actions, "Center");
		tabPane.add("Add Books to my Library", getPanelAddBook());
		tabPane.add("Add Books to my Library", new JScrollPane(addBookJPanel));

		JPanel panDelete = new JPanel(new BorderLayout());
		actions = new JTextArea();
		panDelete.setBackground(Color.orange);
		actions.setFont(new Font("Arial", Font.BOLD, 60));
		panDelete.add(actions, "Center");
		tabPane.add("Delete Books from my Library", getPanelCentreDelete());
		tabPane.add("Delete Books from my Library", new JScrollPane(pDCenter));
		return tabPane;

	}

	/**
	 * creates panel with the last updates library
	 *
	 * @return
	 */
	public JPanel getPanelCentreLib() {
		pCenter = new JPanel(new BorderLayout());
		JLabel libImage = new JLabel();
		myLibIcon = new ImageIcon("");
		libImage.setIcon(myLibIcon);
		JScrollPane scrollPane = new JScrollPane(libImage);
		pCenter.add(scrollPane);
		return pCenter;
	}

	/**
	 * Update the picture of the library when we change parameters in our IHM
	 *
	 * @return nothing
	 * @throws ParserConfigurationException
	 */

	public void updateSVGLibrary() throws ParserConfigurationException {
		generateButton.setText("Reload my library now");

		switch (sort) {
		case "Author":
			svgLibrary.setLibrary(new Library(svgLibrary.getLibrary().sortByAuthor(), nbBooksPerShelf));
			break;
		case "Title":
			svgLibrary.setLibrary(new Library(svgLibrary.getLibrary().sortByTitle(), nbBooksPerShelf));
			break;
		case "Year":
			boolean rising = !sortAscendingYearButton.isSelected();
			svgLibrary.setLibrary(new Library(svgLibrary.getLibrary().sortByYear(rising), nbBooksPerShelf));
			break;
		default:
			svgLibrary = new SVGLibrary(new Library(dataFile.read(), nbBooksPerShelf));
			break;
		}

		try {
			svgLibrary.generate(leaning, backgroundColor, bookColor, shelfColor);
		} catch (IOException e) {
			LOGGER.error("Error when we generateButton the library with ordinary field : Some parameters seems npt ok PLEASE CHECK GENERATE METHOD");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			LOGGER.error("Error when we generateButton the library with ordinary field : Some parameters seems npt ok PLEASE CHECK GENERATE METHOD");
			e.printStackTrace();
		}

		try {
			svgLibrary.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}

		pCenter.removeAll();
		pCenter.revalidate();
		JLabel libImage = new JLabel();
		myLibIcon = new ImageIcon(svgLibrary.getNewImage());
		libImage.setIcon(myLibIcon);
		pCenter.add(libImage);
		JScrollPane asc = new JScrollPane(libImage);
		pCenter.add(asc);
		pCenter.updateUI();
		File fichier = new File(svgLibrary.getNewImage());
		fichier.delete();

	}

	@SuppressWarnings("unused")
	public JPanel getPanelCentreDelete() {
		List<Book> books = dataFile.read();
		Library lib = new Library(books, 5);
		int nbBooks = 0;
		for (int i = 0; i < lib.getShelves().size(); i++)
			nbBooks += lib.getShelves().get(i).getBooks().size();
		pDCenter = new JPanel(new BorderLayout());
		JPanel tab = new JPanel(new GridLayout(nbBooks, 2, 10, 10));
		ButtonGroup booksButtonGroup = new ButtonGroup();
		JButton removeBookButton = new JButton("Remove");
		int indexShelf = 0;
		int indexBook = 0;
		for (Book book : books) {
			String bookTitle = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getTitle();
			String authorFirstName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor()
					.getFirstName();
			String authorLastName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor()
					.getLastName();
			int bookyear = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getYear();
			String bookString = authorLastName + "," + authorFirstName + "," + bookTitle + ","
					+ String.valueOf(bookyear);

			JRadioButton cb = new JRadioButton(bookString);
			cb.setName(bookString);
			tab.add(cb);
			booksButtonGroup.add(cb);

			if (indexBook == lib.getShelves().get(indexShelf).getBooks().size() - 1
					&& !(indexShelf == lib.getShelves().size() - 1)) {
				indexShelf++;
				indexBook = 0;
			} else if (!(indexShelf == lib.getShelves().size() - 1
					&& indexBook == lib.getShelves().get(indexShelf).getBooks().size() - 1))
				indexBook++;
		}

		removeBookButton.addActionListener(new removeBookButtonListener(booksButtonGroup, tab));
		pDCenter.add(tab);
		pDCenter.add(removeBookButton, BorderLayout.SOUTH);

		return pDCenter;
	}

	public JPanel getPanelAddBook() {

		addBookJPanel = new JPanel(new BorderLayout());

		JPanel bookFormJPanel = new JPanel(new GridLayout(10, 2, 10, 10));
		JPanel searchJPanel = new JPanel();

		addBookJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		JLabel titleFirstColumn = new JLabel("Add Book");
		titleFirstColumn.setFont(new Font("Arial", Font.ITALIC, 50));

		JLabel searchJLabel = new JLabel("Search: ");
		JLabel firstNameJLabel = new JLabel("First Name : ");
		JLabel lastNameJLabel = new JLabel("Last Name : ");
		JLabel titleJLabel = new JLabel("Title : ");
		JLabel yearJLabel = new JLabel("Year : ");
		JLabel dimXJLabel = new JLabel("DimX : ");
		JLabel dimYJLabel = new JLabel("DimY : ");
		JLabel colorJLabel = new JLabel("Color : ");

		JLabel titleSecondColumn = new JLabel("");
		titleSecondColumn.setFont(new Font("Arial", Font.ITALIC, 50));
		searchTextField = new JTextField();
		searchTextField.setBounds(10, 10, 200, 200);
		searchTextField.setPreferredSize(new Dimension(160, 40));

		JButton searchButton = new JButton("Search");

		firstNameTextField = new JTextField();
		firstNameTextField.setBounds(5, 5, 200, 200);
		lastNameTextField = new JTextField();
		lastNameTextField.setBounds(5, 5, 50, 25);
		titleTextField = new JTextField();
		titleTextField.setBounds(5, 5, 100, 50);
		yearTextField = new JTextField();
		yearTextField.setBounds(5, 5, 100, 50);
		dimXTextField = new JTextField();
		dimXTextField.setBounds(5, 5, 100, 50);
		dimYTextField = new JTextField();
		dimYTextField.setBounds(5, 5, 100, 50);
		String[] colors = { "rose", "violet", "bleu", "orange", "jaune", "vert", "rouge" };
		final JComboBox<String> colorComboBox = new JComboBox<String>(colors);

		bookFormJPanel.add(titleFirstColumn);
		bookFormJPanel.add(titleSecondColumn);
		bookFormJPanel.add(searchJLabel);
		searchJPanel.add(searchTextField);
		searchJPanel.add(searchButton);
		bookFormJPanel.add(searchJPanel);
		bookFormJPanel.add(firstNameJLabel);
		bookFormJPanel.add(firstNameTextField);
		bookFormJPanel.add(lastNameJLabel);
		bookFormJPanel.add(lastNameTextField);
		bookFormJPanel.add(titleJLabel);
		bookFormJPanel.add(titleTextField);
		bookFormJPanel.add(yearJLabel);
		bookFormJPanel.add(yearTextField);
		bookFormJPanel.add(dimXJLabel);
		bookFormJPanel.add(dimXTextField);
		bookFormJPanel.add(dimYJLabel);
		bookFormJPanel.add(dimYTextField);
		bookFormJPanel.add(colorJLabel);
		bookFormJPanel.add(colorComboBox);
		bookFormJPanel.setOpaque(false);
		JButton addBookButton = new JButton("Add");
		bookFormJPanel.add(addBookButton);
		addBookJPanel.add(bookFormJPanel);
		searchButton.addActionListener(new SearchButtonListener(addBookJPanel, searchTextField, titleTextField, lastNameTextField, firstNameTextField));
		addBookButton.addActionListener(new AddBookButtonListener(colorComboBox, addBookJPanel, bookFormJPanel, firstNameTextField, lastNameTextField, titleTextField, yearTextField, dimXTextField, dimYTextField));
		return addBookJPanel;

	}

	/**
	 * creates panel with the options for the user
	 *
	 * @return
	 */
	public JPanel getCenterPanelOptions() {

		optionsJPanel = new JPanel();
		Image image = null;
		try {
			URL url = new URL("http://www.fsgworkinprogress.com/wp-content/uploads/2013/04/MARKWEINER.png");
			image = ImageIO.read(url);
		} catch (IOException e) {
			LOGGER.error("Impossible to find the picture ok menu jpanel via url http://www.fsgworkinprogress.com/wp-content/uploads/2013/04/MARKWEINER.png ");
			e.printStackTrace();
		}
		JLabel booksImage = new JLabel(new ImageIcon(image));
		JPanel optionsNames = new JPanel(new GridLayout(0, 2, 40, 30));
		JPanel parameters = new JPanel();
		JPanel choice = new JPanel();
		optionsNames.add(parameters);
		optionsNames.add(choice);
		JLabel titleFirstColumn = new JLabel("Parameters");
		parameters.setOpaque(false);
		titleFirstColumn.setFont(new Font("Book Antiqua", Font.ITALIC, 55));
		titleFirstColumn.setForeground(Color.decode("#b32d00"));
		JPanel backgroundColorTitleJPanel = new JPanel();
		backgroundColorTitleJPanel.setOpaque(false);
		JPanel shelvesColorTitleJPanel = new JPanel();
		shelvesColorTitleJPanel.setOpaque(false);
		JPanel booksColorTitleJPanel = new JPanel();
		booksColorTitleJPanel.setOpaque(false);
		JPanel leaningModeTitleJPanel = new JPanel();
		leaningModeTitleJPanel.setOpaque(false);
		JPanel sortTitleJPanel = new JPanel();
		sortTitleJPanel.setOpaque(false);
		JPanel numberBooksPerShelfTitleJPanel = new JPanel();
		numberBooksPerShelfTitleJPanel.setOpaque(false);

		JLabel backgroundColorTitleJLabel = new JLabel("Background Color : ");
		backgroundColorTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		JLabel shelvesColorTitleJLabel = new JLabel("Shelves Color : ");
		shelvesColorTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		JLabel booksColorTitleJLabel = new JLabel("Books Color : ");
		booksColorTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		JLabel leaningModeTitleJLabel = new JLabel("Position of books : ");
		leaningModeTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		JLabel sortTitleJLabel = new JLabel("Sort books by : ");
		sortTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		JLabel numberBooksPerShelfTitleJLabel = new JLabel("Books per shelf:");
		numberBooksPerShelfTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		numberBooksPerShelfTextField = new JFormattedTextField(nbBooksPerShelf);

		JLabel titleSecondColumn = new JLabel("Choices");
		choice.setOpaque(false);
		titleSecondColumn.setFont(new Font("Book Antiqua", Font.ITALIC, 55));
		titleSecondColumn.setForeground(Color.decode("#b32d00"));

		JPanel backgroundColorJPanel = new JPanel();
		backgroundColorJPanel.setOpaque(false);
		JPanel shelvesColorJPanel = new JPanel();
		shelvesColorJPanel.setOpaque(false);
		JPanel booksColorJPanel = new JPanel();
		booksColorJPanel.setOpaque(false);
		JPanel leaningModeJPanel = new JPanel();
		leaningModeJPanel.setOpaque(false);
		JPanel sortJPanel = new JPanel();
		sortJPanel.setOpaque(false);
		JPanel numberBooksPerShelfJPanel = new JPanel();
		numberBooksPerShelfJPanel.setOpaque(false);

		ButtonGroup backgroundColorButtonGroup = new ButtonGroup();
		ButtonGroup shelvesColor = new ButtonGroup();
		ButtonGroup booksColor = new ButtonGroup();
		ButtonGroup sortButtonGroup = new ButtonGroup();
		ButtonGroup leanButtonGroup = new ButtonGroup();

		bAutoBk = new JRadioButton("Auto");
		bLightBk = new JRadioButton("Light");
		bDarkBk = new JRadioButton("Dark");
		bAutoBk.addActionListener(new BackgroundColorButtonListener(bAutoBk, bLightBk, bDarkBk));
		bLightBk.addActionListener(new BackgroundColorButtonListener(bAutoBk, bLightBk, bDarkBk));
		bDarkBk.addActionListener(new BackgroundColorButtonListener(bAutoBk, bLightBk, bDarkBk));
		bAutoBk.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLightBk.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bDarkBk.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bAutoBk.setOpaque(false);
		bLightBk.setOpaque(false);
		bDarkBk.setOpaque(false);
		bAutoBk.setSelected(true);
		backgroundColorButtonGroup.add(bAutoBk);
		backgroundColorButtonGroup.add(bLightBk);
		backgroundColorButtonGroup.add(bDarkBk);

		bAutoS = new JRadioButton("Auto");
		bLightS = new JRadioButton("Light");
		bDarkS = new JRadioButton("Dark");
		bAutoS.addActionListener(new ShelvesColorButtonListener(bAutoS, bLightS, bDarkS));
		bLightS.addActionListener(new ShelvesColorButtonListener(bAutoS, bLightS, bDarkS));
		bDarkS.addActionListener(new ShelvesColorButtonListener(bAutoS, bLightS, bDarkS));
		bAutoS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLightS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bDarkS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bAutoS.setOpaque(false);
		bLightS.setOpaque(false);
		bDarkS.setOpaque(false);
		bAutoS.setSelected(true);
		shelvesColor.add(bAutoS);
		shelvesColor.add(bLightS);
		shelvesColor.add(bDarkS);

		bAutoB = new JRadioButton("Auto");
		bLightB = new JRadioButton("Light");
		bDarkB = new JRadioButton("Dark");
		booksColor.add(bAutoB);
		booksColor.add(bLightB);
		booksColor.add(bDarkB);
		bAutoB.addActionListener(new BooksColorButtonListener(bAutoB, bLightB, bDarkB));
		bLightB.addActionListener(new BooksColorButtonListener(bAutoB, bLightB, bDarkB));
		bDarkB.addActionListener(new BooksColorButtonListener(bAutoB, bLightB, bDarkB));
		bAutoB.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLightB.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bDarkB.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bAutoB.setOpaque(false);
		bLightB.setOpaque(false);
		bDarkB.setOpaque(false);
		bAutoB.setSelected(true);

		bLeanS = new JRadioButton("Leaned");
		bNotLeanS = new JRadioButton("Not leaned");
		bLeanS.addActionListener(new LeaningButtonListener(bLeanS));
		bNotLeanS.addActionListener(new LeaningButtonListener(bLeanS));
		bLeanS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bNotLeanS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLeanS.setOpaque(false);
		bNotLeanS.setOpaque(false);
		bLeanS.setSelected(true);
		leanButtonGroup.add(bLeanS);
		leanButtonGroup.add(bNotLeanS);
		
		sortAutoButton = new JRadioButton("Auto");
		sortYearButton = new JRadioButton("Year");
		sortAuthorButton = new JRadioButton("Author");
		sortTitleButton = new JRadioButton("Title");
		sortButtonGroup.add(sortAutoButton);
		sortButtonGroup.add(sortYearButton);
		sortButtonGroup.add(sortAuthorButton);
		sortButtonGroup.add(sortTitleButton);
		sortAutoButton.addActionListener(new SortButtonListener());
		sortYearButton.addActionListener(new SortButtonListener());
		sortAuthorButton.addActionListener(new SortButtonListener());
		sortTitleButton.addActionListener(new SortButtonListener());
		sortAutoButton.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		sortYearButton.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		sortAuthorButton.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		sortTitleButton.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		sortAutoButton.setOpaque(false);
		sortYearButton.setOpaque(false);
		sortAuthorButton.setOpaque(false);
		sortTitleButton.setOpaque(false);
		sortAutoButton.setSelected(true);
		sortAscendingYearButton = new JCheckBox("Most recent first (sort by year)");
		sortAscendingYearButton.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		sortAscendingYearButton.setOpaque(false);

		JButton lessBookPerS = new JButton("Less");
		lessBookPerS.addActionListener(new LessBookPerShelfListener(nbBooksPerShelf, numberBooksPerShelfTextField));
		JButton moreBookPerS = new JButton("More");
		moreBookPerS.addActionListener(new MoreBookPerShelfListener(nbBooksPerShelf, numberBooksPerShelfTextField));

		backgroundColorTitleJPanel.add(backgroundColorTitleJLabel);
		shelvesColorTitleJPanel.add(shelvesColorTitleJLabel);
		booksColorTitleJPanel.add(booksColorTitleJLabel);
		leaningModeTitleJPanel.add(leaningModeTitleJLabel);
		sortTitleJPanel.add(sortTitleJLabel);
		numberBooksPerShelfTitleJPanel.add(numberBooksPerShelfTitleJLabel);
		backgroundColorJPanel.add(bAutoBk);
		backgroundColorJPanel.add(bLightBk);
		backgroundColorJPanel.add(bDarkBk);
		shelvesColorJPanel.add(bAutoS);
		shelvesColorJPanel.add(bLightS);
		shelvesColorJPanel.add(bDarkS);
		booksColorJPanel.add(bAutoB);
		booksColorJPanel.add(bLightB);
		booksColorJPanel.add(bDarkB);
		leaningModeJPanel.add(bLeanS);
		leaningModeJPanel.add(bNotLeanS);
		sortJPanel.add(sortAutoButton);
		sortJPanel.add(sortYearButton);
		sortJPanel.add(sortAscendingYearButton);
		sortJPanel.add(sortAuthorButton);
		sortJPanel.add(sortTitleButton);
		numberBooksPerShelfJPanel.add(lessBookPerS);
		numberBooksPerShelfJPanel.add(numberBooksPerShelfTextField);
		numberBooksPerShelfJPanel.add(moreBookPerS);

		parameters.add(titleFirstColumn);
		choice.add(titleSecondColumn);

		optionsNames.add(backgroundColorTitleJPanel);
		optionsNames.add(backgroundColorJPanel);
		optionsNames.add(shelvesColorTitleJPanel);
		optionsNames.add(shelvesColorJPanel);
		optionsNames.add(booksColorTitleJPanel);
		optionsNames.add(booksColorJPanel);
		optionsNames.add(leaningModeTitleJPanel);
		optionsNames.add(leaningModeJPanel);
		optionsNames.add(sortTitleJPanel);
		optionsNames.add(sortJPanel);
		optionsNames.add(numberBooksPerShelfTitleJPanel);
		optionsNames.add(numberBooksPerShelfJPanel);
		optionsNames.setOpaque(false);
		optionsJPanel.add(booksImage);
		optionsJPanel.add(optionsNames);

		return optionsJPanel;
	}

	class GenerateButtonListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			if (s.equals("Generate my library") || s.equals("Reload my library now"))
				try {
					updateSVGLibrary();
				} catch (ParserConfigurationException ex) {
					LOGGER.error("Impossible to refresh the button after the last update of library");
					ex.printStackTrace();
				}

		}
	}

	class BackgroundColorButtonListener implements ActionListener {
		
		private JRadioButton bAutoBk, bLightBk, bDarkBk;
		
		public BackgroundColorButtonListener(JRadioButton bAutoBk, JRadioButton bLightBk, JRadioButton bDarkBk){
			this.bAutoBk = bAutoBk;
			this.bLightBk = bLightBk;
			this.bDarkBk = bDarkBk;
		}
		
		/**
		 * function launched when the user performs an action
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			if (bAutoBk.isSelected())
				backgroundColor = bAutoBk.getActionCommand();
			else if (bLightBk.isSelected())
				backgroundColor = bLightBk.getActionCommand();
			else if (bDarkBk.isSelected())
				backgroundColor = bDarkBk.getActionCommand();
		}
	}

	class ShelvesColorButtonListener implements ActionListener {
		
		private JRadioButton bAutoS, bLightS, bDarkS;
		
		public ShelvesColorButtonListener(JRadioButton bAutoS, JRadioButton bLightS, JRadioButton bDarkS){
			this.bAutoS = bAutoS;
			this.bLightS = bLightS;
			this.bDarkS = bDarkS;
		}
		
		/**
		 * function launched when the user performs an action
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			if (bAutoS.isSelected())
				shelfColor = bAutoS.getActionCommand();
			else if (bLightS.isSelected())
				shelfColor = bLightS.getActionCommand();
			else if (bDarkS.isSelected())
				shelfColor = bDarkS.getActionCommand();
		}
	}

	class BooksColorButtonListener implements ActionListener {
		
		private JRadioButton bAutoB, bLightB, bDarkB;
		
		public BooksColorButtonListener(JRadioButton bAutoB,JRadioButton bLightB,JRadioButton bDarkB){
			this.bAutoB = bAutoB;
			this.bLightB = bLightB;
			this.bDarkB = bDarkB;
		}
		
		/**
		 * function launched when the user performs an action
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			if (bAutoB.isSelected())
				bookColor = bAutoB.getActionCommand();
			else if (bLightB.isSelected())
				bookColor = bLightB.getActionCommand();
			else if (bDarkB.isSelected())
				bookColor = bDarkB.getActionCommand();
		}
	}

	class LeaningButtonListener implements ActionListener {
		
		private JRadioButton bLeanS;
		
		public LeaningButtonListener(JRadioButton bLeanS){
			this.bLeanS = bLeanS;
		}
		
		/**
		 * function launched when the user performs an action
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			leaning = bLeanS.isSelected();
		}
	}

	class MoreBookPerShelfListener implements ActionListener {
		
		private int nbBooksPerShelf;
		private JFormattedTextField numberBooksPerShelfTextField;
		
		public MoreBookPerShelfListener(int nbBooksPerShelf, JFormattedTextField numberBooksPerShelfTextField){
			this.nbBooksPerShelf = nbBooksPerShelf;
			this.numberBooksPerShelfTextField = numberBooksPerShelfTextField;
		}
		
		/**
		 * function launched when the user performs an action
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			if (nbBooksPerShelf != 18){
				nbBooksPerShelf++;
				numberBooksPerShelfTextField.setValue(nbBooksPerShelf);
			}
		}
	}

	class LessBookPerShelfListener implements ActionListener {
		
		private int nbBooksPerShelf;
		private JFormattedTextField numberBooksPerShelfTextField;
		
		public LessBookPerShelfListener(int nbBooksPerShelf, JFormattedTextField numberBooksPerShelfTextField){
			this.nbBooksPerShelf = nbBooksPerShelf;
			this.numberBooksPerShelfTextField = numberBooksPerShelfTextField;
		}
		
		/**
		 * function launched when the user performs an action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (nbBooksPerShelf != 1) {
				nbBooksPerShelf--;
				numberBooksPerShelfTextField.setValue(nbBooksPerShelf);
			}
		}
	}

	class SearchButtonListener implements ActionListener {

		private JPanel pBCenter;
		private JTextField searchTextField, titleTextField, lastNameTextField, firstNameTextField;

		public SearchButtonListener(JPanel jpanel, JTextField searchTextField, JTextField titleTextField, JTextField lastNameTextField, JTextField firstNameTextField) {
			this.pBCenter = jpanel;
			this.searchTextField = searchTextField;
			this.titleTextField = titleTextField;
			this.lastNameTextField = lastNameTextField;
			this.firstNameTextField = firstNameTextField;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String line = searchTextField.getText();
			ConnectionToCongressLibrary connexion = new ConnectionToCongressLibrary(line);

			String tabResult[] = new String[3];
			tabResult = connexion.extractData();
			titleTextField.setText(tabResult[0]);
			String[] np = tabResult[1].split(",");
			lastNameTextField.setText(np[0]);
			firstNameTextField.setText(np[1]);
			JOptionPane.showMessageDialog(pBCenter, "Search result");
		}
	}

	class AddBookButtonListener implements ActionListener {

		private JComboBox<String> colorComboBox;
		private JPanel pBCenter, tab;
		private JTextField firstNameTextField, lastNameTextField, titleTextField, yearTextField, dimXTextField, dimYTextField;

		public AddBookButtonListener(JComboBox<String> j, JPanel c, JPanel t, JTextField firstNameTextField, JTextField lastNameTextField, JTextField titleTextField, JTextField yearTextField, JTextField dimXTextField, JTextField dimYTextField) {
			colorComboBox = j;
			pBCenter = c;
			tab = t;
			this.firstNameTextField = firstNameTextField;
			this.lastNameTextField = lastNameTextField;
			this.titleTextField = titleTextField;
			this.yearTextField = yearTextField;
			this.dimXTextField = dimXTextField;
			this.dimYTextField = dimYTextField;
		}

		public boolean isInt(String chaine) {
			boolean valeur = true;
			char[] tab = chaine.toCharArray();

			for (char carac : tab)
				if (!Character.isDigit(carac) && valeur)
					valeur = false;

			return valeur;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (firstNameTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(pBCenter, "You must enter a first name.");
				return;
			}
			if (lastNameTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(pBCenter, "You must enter a last name.");
				return;
			}
			if (titleTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(pBCenter, "You must enter a title.");
				return;
			}
			String year = yearTextField.getText();
			if (!isInt(year))
				year = "2000";
			if (year.isEmpty())
				year = "2000";
			String firstName = firstNameTextField.getText();
			String lastName = lastNameTextField.getText();
			String title = titleTextField.getText();
			String dimX = dimXTextField.getText();
			if (!isInt(dimX))
				dimX = "200";
			if (dimX.isEmpty())
				dimX = "200";
			String dimY = dimYTextField.getText();
			if (!isInt(dimY))
				dimY = "200";
			if (dimY.isEmpty())
				dimY = "200";
			String line = firstName + ",";
			line = line + lastName + ",";
			line = line + title + ",";
			line = line + year + ",";
			line = line + dimX + ",";
			line = line + dimY + ",";
			line = line + colorComboBox.getSelectedItem().toString() + ",";
			line = line + "End";
			dataFile.addLine(line);
			JOptionPane.showMessageDialog(pBCenter, "Book added successfully");
			Component[] components = tab.getComponents();
			for (Component component : components)
				if (component instanceof JTextField || component instanceof JTextArea) {
					JTextComponent specificObject = (JTextComponent) component;
					specificObject.setText("");
				}
			tabPane.remove(3);
			tabPane.add("Delete Books from my Library", getPanelCentreDelete());
			tabPane.add("Delete Books from my Library", new JScrollPane(pDCenter));
			tab.revalidate();
			tab.repaint();
			pBCenter.revalidate();
			pBCenter.repaint();
			List<Book> books = dataFile.read();
			Library library = new Library(books, nbBooksPerShelf);
			svgLibrary.setLibrary(library);
		}
	}

	class SortButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (sortAuthorButton.isSelected())
				sort = "Author";
			else if (sortTitleButton.isSelected())
				sort = "Title";
			else if (sortYearButton.isSelected())
				sort = "Year";
			else
				sort = "Auto";
		}

	}

	class removeBookButtonListener implements ActionListener {

		private ButtonGroup booksButtonGroup;

		private JPanel jPanel;

		public removeBookButtonListener(ButtonGroup bg, JPanel jp) {
			this.booksButtonGroup = bg;
			this.jPanel = jp;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			Enumeration<AbstractButton> allRadioButton = booksButtonGroup.getElements();
			while (allRadioButton.hasMoreElements()) {
				JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
				if (temp.isSelected()) {
					JOptionPane.showMessageDialog(null, "You selected : " + temp.getName());
					dataFile.deleteLine(temp.getName());
					booksButtonGroup.remove(temp);
					jPanel.remove(temp);
					jPanel.revalidate();
					jPanel.repaint();
					pDCenter.revalidate();
					pDCenter.repaint();
				}
			}
			List<Book> books = dataFile.read();
			Library library = new Library(books, nbBooksPerShelf);
			svgLibrary.setLibrary(library);
		}
	}

}