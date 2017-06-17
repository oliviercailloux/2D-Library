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

	// final static Logger logger = Logger.getLogger(Wisndow2DLibrary.class);
	private static final long serialVersionUID = 1L;
	private JButton generate, lessBookPerS, moreBookPerS;
	private JTextField presentation;
	private JFormattedTextField numberBooksPerShelfTextField;
	private JPanel pCenter, pBCenter, centre;
	private JPanel pDCenter;
	private JPanel sud;
	private JPanel panBooks;
	private JTabbedPane tabPane;
	private JTextArea options;
	private JTextArea visuG;
	private JTextArea actions;
	private ImageIcon myLibIcon;
	private JLabel libImage;
	private ButtonGroup booksColor, shelvesColor, backgroundColor, leanButtonGroup, sortButtonGroup;
	private JRadioButton bDarkB, bLightB, bAutoB, bLeanS, bNotLeanS, bDarkBk, bLightBk, bAutoBk, bDarkS, bLightS,
			bAutoS, sortAutoButton, sortYearButton, sortAuthorButton, sortTitleButton;
	private JCheckBox sortAscendingYearButton;
	private JLabel backgroundColorTitleJLabel, booksColorTitleJLabel, shelvesColorTitleJLabel, leaningModeTitleJLabel,
			sortTitleJLabel, numberBooksPerShelfTitleJLabel;
	private JLabel se, fn, ln, ti, ye, dx, dy, co;
	private JTextField tse, tfn, tln, tti, tye, tdx, tdy;
	private String bColor = "Auto", bkColor = "Auto", sColor = "Auto", sort = "Auto";
	private boolean leaning = true;
	private int nbBooksPerShelf = 10;
	private DataFile dataFile = new DataFile();
	private SVGLibrary svgLibrary;
	//private JDatePickerImpl datePicker;

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
		Toolkit atk = Toolkit.getDefaultToolkit();
		Dimension dim = atk.getScreenSize();
		int h = dim.height;
		int w = dim.width;
		this.setSize(w, h);

	}

	/**
	 * creates the panels of the window
	 */
	public void initialise() {
		Container c = this.getContentPane();
		c.add(this.getPanelSud(), BorderLayout.SOUTH);
		c.add(this.getPanelNord(), BorderLayout.NORTH);
		c.add(this.getPanelCentre(), BorderLayout.CENTER);
	}

	/**
	 * creates north panel : title
	 * 
	 * @return
	 */
	public JPanel getPanelNord() {
		JPanel nord = new JPanel();
		JPanel head = new JPanel();
		JLabel score = new JLabel("Welcome to your Library :-)  ");
		presentation = new JTextField(10);
		presentation.setEditable(false);
		presentation.setVisible(false);
		nord.add(head);
		head.add(score);
		head.add(presentation);

		return nord;
	}

	/**
	 * creates south panel : button to generate the library
	 * 
	 * @return
	 */
	public JPanel getPanelSud() {

		sud = new JPanel();
		generate = new JButton("Generate my library");
		generate.addActionListener(new GenerateButtonListener());
		sud.add(generate);
		return sud;
	}

	/**
	 * creates center panel : options and layout of the library
	 * 
	 * @return
	 */
	// TODO: modify color after validate ; horrible color only for help to
	// identify space of
	public JTabbedPane getPanelCentre() { // Une tab d'onglet avec 2 onglets, un
		// pour les options et lautre pour
		// afficher sa librairie

		this.tabPane = new JTabbedPane();

		JPanel panOptions = new JPanel(new BorderLayout());
		this.options = new JTextArea();
		panOptions.setBackground(Color.orange);
		options.setFont(new Font("Arial", Font.BOLD, 60));
		panOptions.add(options, "Center");
		tabPane.add("Options for my library", getPanelCentreOptions());
		tabPane.add("Options for my library", new JScrollPane(centre));

		this.panBooks = new JPanel(new BorderLayout());
		this.visuG = new JTextArea();
		visuG.setFont(new Font("Arial", Font.BOLD, 60));
		panBooks.add(visuG, "Center");
		tabPane.add("My Library", getPanelCentreLib());

		JPanel panActions = new JPanel(new BorderLayout());
		this.actions = new JTextArea();
		panActions.setBackground(Color.orange);
		actions.setFont(new Font("Arial", Font.BOLD, 60));
		panActions.add(actions, "Center");
		tabPane.add("Add Books to my Library", getPanelCentreBooks());
		tabPane.add("Add Books to my Library", new JScrollPane(pBCenter));

		JPanel panDelete = new JPanel(new BorderLayout());
		this.actions = new JTextArea();
		panDelete.setBackground(Color.orange);
		actions.setFont(new Font("Arial", Font.BOLD, 60));
		panDelete.add(actions, "Center");
		tabPane.add("Delete Books from my Library", getPanelCentreDelete());
		tabPane.add("Delete Books from my Library",new JScrollPane(pDCenter));
		return tabPane;

	}

	/**
	 * creates panel with the last updates library
	 * 
	 * @return
	 */
	public JPanel getPanelCentreLib() {

		this.pCenter = new JPanel(new BorderLayout());
		this.libImage = new JLabel();
		myLibIcon = new ImageIcon("");
		libImage.setIcon(myLibIcon);
		JScrollPane asc = new JScrollPane(this.libImage);
		pCenter.add(asc);

		return pCenter;

	}

	/**
	 * Update the picture of the library when we change parameters in our IHM
	 * 
	 * @return nothing
	 */

	public void updateSVGLibrary() {
		generate.setText("Reload my library now");

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
			try {
				svgLibrary = new SVGLibrary(new Library(dataFile.read(), nbBooksPerShelf));
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}

		try {
			svgLibrary.generate(leaning, bkColor, bColor, sColor);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			svgLibrary.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}

		pCenter.removeAll();
		pCenter.revalidate();
		libImage = new JLabel();
		myLibIcon = new ImageIcon(svgLibrary.getNewI());
		libImage.setIcon(myLibIcon);
		pCenter.add(libImage);
		JScrollPane asc = new JScrollPane(this.libImage);
		pCenter.add(asc);
		pCenter.updateUI();
		File fichier = new File(svgLibrary.getNewI());
		fichier.delete();

	}

	public JPanel getPanelCentreDelete() {
		List<Book> books = dataFile.read();
		Library lib = new Library(books, 5);
		int nbBooks = 0;
		for (int i = 0; i < lib.getShelves().size(); i++) {
			nbBooks += lib.getShelves().get(i).getBooks().size();
		}

		pDCenter = new JPanel(new BorderLayout());
		JPanel tab = new JPanel(new GridLayout(nbBooks, 2, 10, 10));
		ButtonGroup bg = new ButtonGroup();
		JButton removeBookButton = new JButton("Remove");

		// List<JRadioButton> cbarr = new ArrayList<JRadioButton>();

		/*
		 * ActionListener listener = new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * if(e.getSource() == button) { ButtonGroup group = new ButtonGroup();
		 * //JRadioButton btn = (JRadioButton) e.getSource(); + btn.getName()
		 * group.getSelection().getActionCommand();
		 * JOptionPane.showMessageDialog(pDCenter,"Selected Button = "+group.
		 * getSelection().getActionCommand()); } } };
		 */

		/*
		 * ActionListener listener = new ActionListener() { public void
		 * actionPerformed(ActionEvent event) { if (event.getSource() == button)
		 * { Enumeration<AbstractButton> allRadioButton = bg.getElements();
		 * while (allRadioButton.hasMoreElements()) { JRadioButton temp =
		 * (JRadioButton) allRadioButton.nextElement(); if (temp.isSelected()) {
		 * JOptionPane.showMessageDialog(null, "You selected : " +
		 * temp.getName());
		 * io.github.oliviercailloux.y2017.my_2D_library.controller.deleteBook.
		 * deleteB(temp.getName()); tab.removeAll(); pDCenter.removeAll();
		 * bg.remove(temp);
		 * 
		 * tab.revalidate(); tab.setBackground(Color.BLUE); tab.repaint();
		 * pDCenter.revalidate(); pDCenter.setBackground(Color.BLUE);
		 * pDCenter.repaint(); } } } } };
		 */

		int indexShelf = 0;
		int indexBook = 0;
		for (@SuppressWarnings("unused")
		Book book : books) {
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
			// cbarr.add(cb);
			tab.add(cb);
			bg.add(cb);

			if (indexBook == lib.getShelves().get(indexShelf).getBooks().size() - 1
					&& !(indexShelf == lib.getShelves().size() - 1)) {
				indexShelf++;
				indexBook = 0;
			} else if (!(indexShelf == lib.getShelves().size() - 1
					&& indexBook == lib.getShelves().get(indexShelf).getBooks().size() - 1)) {
				indexBook++;
			}
		}

		removeBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == removeBookButton) {
					Enumeration<AbstractButton> allRadioButton = bg.getElements();
					while (allRadioButton.hasMoreElements()) {
						JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
						if (temp.isSelected()) {
							JOptionPane.showMessageDialog(null, "You selected : " + temp.getName());
							dataFile.deleteLine(temp.getName());
							// tab.removeAll();
							// pDCenter.removeAll();
							bg.remove(temp);
							tab.remove(temp);
							tab.revalidate();
							// tab.setBackground(Color.BLUE);
							tab.repaint();
							pDCenter.revalidate();
							// pDCenter.setBackground(Color.BLUE);
							pDCenter.repaint();
						}
					}
				}
				List<Book> books = dataFile.read();
				Library library = new Library(books, nbBooksPerShelf);
				svgLibrary.setLibrary(library);
			}
		});
		pDCenter.add(tab);
		pDCenter.add(removeBookButton, BorderLayout.SOUTH);

		return pDCenter;
	}

	public JPanel getPanelCentreBooks() {

		pBCenter = new JPanel(new BorderLayout());

		JPanel tab = new JPanel(new GridLayout(10, 2, 10, 10));
		JPanel search = new JPanel();

		pBCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		// JPanel param= new JPanel();
		// JPanel choice= new JPanel();

		JLabel titleFirstColumn = new JLabel("Add Book");
		// param.setOpaque(false);
		titleFirstColumn.setFont(new Font("Arial", Font.ITALIC, 50));

		se = new JLabel("Search: ");
		fn = new JLabel("First Name : ");
		ln = new JLabel("Last Name : ");
		ti = new JLabel("Title : ");
		ye = new JLabel("Year : ");
		dx = new JLabel("DimX : ");
		dy = new JLabel("DimY : ");
		co = new JLabel("Color : ");

		JLabel titleSecondColumn = new JLabel("");
		// choice.setOpaque(false);
		titleSecondColumn.setFont(new Font("Arial", Font.ITALIC, 50));
		
        //UtilDateModel model = new UtilDateModel();
        //JDatePanelImpl datePanel = new JDatePanelImpl(model);
        //datePicker = new JDatePickerImpl(datePanel);

		tse = new JTextField();
		tse.setBounds(10, 10, 200, 200);
		tse.setPreferredSize(new Dimension(160, 40));

		tfn = new JTextField();//first name
		tfn.setBounds(5, 5, 200, 200);
		tln = new JTextField();//last name
		tln.setBounds(5, 5, 50, 25);
		tti = new JTextField();//title
		tti.setBounds(5, 5, 100, 50);
		tye = new JTextField();//year
		tye.setBounds(5, 5, 100, 50);
		tdx = new JTextField();//dimension x
		tdx.setBounds(5, 5, 100, 50);
		tdy = new JTextField();//dimension y
		tdy.setBounds(5, 5, 100, 50);
		String[] choices = { "rose", "violet", "bleu", "orange", "jaune", "vert", "rouge" };
		final JComboBox<String> lco = new JComboBox<String>(choices);//colors

		JButton searchButton = new JButton("Search");

		tab.add(titleFirstColumn);
		tab.add(titleSecondColumn);
		tab.add(se);
		search.add(tse);
		search.add(searchButton);
		tab.add(search);
		tab.add(fn);
		tab.add(tfn);
		tab.add(ln);
		tab.add(tln);
		tab.add(ti);
		tab.add(tti);
		tab.add(ye);
		tab.add(tye);
		tab.add(dx);
		tab.add(tdx);
		tab.add(dy);
		tab.add(tdy);
		tab.add(co);
		tab.add(lco);
		tab.setOpaque(false);
		// tab.add(param);
		// tab.add(choice);
		JButton addBookButton = new JButton("Add");
		tab.add(addBookButton);
		pBCenter.add(tab);
		
		searchButton.addActionListener(new SearchButtonListener(pBCenter));

		addBookButton.addActionListener(new AddBookButtonListener(lco, pBCenter, tab));

		// pBCenter.setBackground(Color.decode("#51DAA8"));
		return pBCenter;

	}

	/**
	 * creates panel with the options for the user
	 * 
	 * @return
	 */
	public JPanel getPanelCentreOptions() {

		centre = new JPanel();
		Image image = null;
		try {
			URL url = new URL("http://www.fsgworkinprogress.com/wp-content/uploads/2013/04/MARKWEINER.png");
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel backG = new JLabel(new ImageIcon(image));
		JPanel optName = new JPanel(new GridLayout(0, 2, 40, 30));
		JPanel param = new JPanel();
		JPanel choice = new JPanel();
		optName.add(param);
		optName.add(choice);
		JLabel titleFirstColumn = new JLabel("Parameters");
		param.setOpaque(false);
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

		backgroundColorTitleJLabel = new JLabel("Background Color : ");
		backgroundColorTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		shelvesColorTitleJLabel = new JLabel("Shelves Color : ");
		shelvesColorTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		booksColorTitleJLabel = new JLabel("Books Color : ");
		booksColorTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		leaningModeTitleJLabel = new JLabel("Position of books : ");
		leaningModeTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		sortTitleJLabel = new JLabel("Sort books by : ");
		sortTitleJLabel.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		numberBooksPerShelfTitleJLabel = new JLabel("Books per shelf:");
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

		backgroundColor = new ButtonGroup();
		shelvesColor = new ButtonGroup();
		booksColor = new ButtonGroup();
		sortButtonGroup = new ButtonGroup();
		leanButtonGroup = new ButtonGroup();

		backgroundColor.add(bAutoBk = new JRadioButton("Auto"));
		backgroundColor.add(bLightBk = new JRadioButton("Light"));
		backgroundColor.add(bDarkBk = new JRadioButton("Dark"));
		bAutoBk.addActionListener(new BackgroundColorButtonListener());
		bLightBk.addActionListener(new BackgroundColorButtonListener());
		bDarkBk.addActionListener(new BackgroundColorButtonListener());
		bAutoBk.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLightBk.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bDarkBk.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bAutoBk.setOpaque(false);
		bLightBk.setOpaque(false);
		bDarkBk.setOpaque(false);
		bAutoBk.setSelected(true);

		shelvesColor.add(bAutoS = new JRadioButton("Auto"));
		shelvesColor.add(bLightS = new JRadioButton("Light"));
		shelvesColor.add(bDarkS = new JRadioButton("Dark"));
		bAutoS.addActionListener(new ShelvesColorButtonListener());
		bLightS.addActionListener(new ShelvesColorButtonListener());
		bDarkS.addActionListener(new ShelvesColorButtonListener());
		bAutoS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLightS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bDarkS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bAutoS.setOpaque(false);
		bLightS.setOpaque(false);
		bDarkS.setOpaque(false);
		bAutoS.setSelected(true);

		booksColor.add(bAutoB = new JRadioButton("Auto"));
		booksColor.add(bLightB = new JRadioButton("Light"));
		booksColor.add(bDarkB = new JRadioButton("Dark"));
		bAutoB.addActionListener(new BooksColorButtonListener());
		bLightB.addActionListener(new BooksColorButtonListener());
		bDarkB.addActionListener(new BooksColorButtonListener());
		bAutoB.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLightB.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bDarkB.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bAutoB.setOpaque(false);
		bLightB.setOpaque(false);
		bDarkB.setOpaque(false);
		bAutoB.setSelected(true);

		leanButtonGroup.add(bLeanS = new JRadioButton("Leaned"));
		leanButtonGroup.add(bNotLeanS = new JRadioButton("Not leaned"));
		bLeanS.addActionListener(new LeaningButtonListener());
		bNotLeanS.addActionListener(new LeaningButtonListener());
		bLeanS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bNotLeanS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLeanS.setOpaque(false);
		bNotLeanS.setOpaque(false);
		bLeanS.setSelected(true);

		sortButtonGroup.add(sortAutoButton = new JRadioButton("Auto"));
		sortButtonGroup.add(sortYearButton = new JRadioButton("Year"));
		sortButtonGroup.add(sortAuthorButton = new JRadioButton("Author"));
		sortButtonGroup.add(sortTitleButton = new JRadioButton("Title"));
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

		lessBookPerS = new JButton("Less");
		lessBookPerS.addActionListener(new LessBookPerShelfListener());
		moreBookPerS = new JButton("More");
		moreBookPerS.addActionListener(new MoreBookPerShelfListener(this.svgLibrary));

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

		param.add(titleFirstColumn);
		choice.add(titleSecondColumn);

		optName.add(backgroundColorTitleJPanel);
		optName.add(backgroundColorJPanel);
		optName.add(shelvesColorTitleJPanel);
		optName.add(shelvesColorJPanel);
		optName.add(booksColorTitleJPanel);
		optName.add(booksColorJPanel);
		optName.add(leaningModeTitleJPanel);
		optName.add(leaningModeJPanel);
		optName.add(sortTitleJPanel);
		optName.add(sortJPanel);
		optName.add(numberBooksPerShelfTitleJPanel);
		optName.add(numberBooksPerShelfJPanel);
		optName.setOpaque(false);
		centre.add(backG);
		// centre.setBackground(Color.decode("#51DAA8"));

		centre.add(optName);

		return centre;

	}

	class GenerateButtonListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			if (s.equals("Generate my library") || s.equals("Reload my library now")) {
				updateSVGLibrary();
			}

		}
	}

	class BackgroundColorButtonListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			if (bAutoBk.isSelected()) {
				bkColor = bAutoBk.getActionCommand();
			} else if (bLightBk.isSelected()) {
				bkColor = bLightBk.getActionCommand();
			} else if (bDarkBk.isSelected()) {
				bkColor = bDarkBk.getActionCommand();
			}
		}
	}

	class ShelvesColorButtonListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			if (bAutoS.isSelected()) {
				sColor = bAutoS.getActionCommand();
			} else if (bLightS.isSelected()) {
				sColor = bLightS.getActionCommand();
			} else if (bDarkS.isSelected()) {
				sColor = bDarkS.getActionCommand();
			}
		}
	}

	class BooksColorButtonListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			if (bAutoB.isSelected()) {
				bColor = bAutoB.getActionCommand();
			} else if (bLightB.isSelected()) {
				bColor = bLightB.getActionCommand();
			} else if (bDarkB.isSelected()) {
				bColor = bDarkB.getActionCommand();
			}
		}
	}

	class LeaningButtonListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			leaning = bLeanS.isSelected();
		}
	}

	class MoreBookPerShelfListener implements ActionListener {
		
		private SVGLibrary svgLibrary;
		
		public MoreBookPerShelfListener(SVGLibrary svgLibrary){
			this.svgLibrary = svgLibrary;
		}
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			//System.out.println("trucbidule : " + svgLibrary.getLibrary().getListOfAllTheBooks().size());
			//if(nbBooksPerShelf != (svgLibrary.getLibrary().getListOfAllTheBooks().size()-1)){
				nbBooksPerShelf++;
				numberBooksPerShelfTextField.setValue(nbBooksPerShelf);
			//}
		}
	}

	class LessBookPerShelfListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			if(nbBooksPerShelf != 1){
				nbBooksPerShelf--;
				numberBooksPerShelfTextField.setValue(nbBooksPerShelf);
			}
		}
	}

	class SearchButtonListener implements ActionListener {

		private JPanel pBCenter;

		public SearchButtonListener(JPanel jpanel) {
			this.pBCenter = jpanel;
		}

		public void actionPerformed(ActionEvent e) {
			String line = tse.getText();
			ConnectionToCongressLibrary connexion = new ConnectionToCongressLibrary(line);

			String tabResult[] = new String[3];
			tabResult = connexion.extractData();
			tti.setText(tabResult[0]);
			String[] np = tabResult[1].split(",");
			tln.setText(np[0]);
			tfn.setText(np[1]);
			//tye.setText(tabResult[2]);
			JOptionPane.showMessageDialog(pBCenter, "Search result");
		}
	}

	class AddBookButtonListener implements ActionListener {

		private JComboBox<String> lco;
		private JPanel pBCenter, tab;

		public AddBookButtonListener(JComboBox<String> j, JPanel c, JPanel t) {
			lco = j;
			pBCenter = c;
			tab = t;
		}
		
		public boolean isInt(String chaine){
			boolean valeur = true;
			char[] tab = chaine.toCharArray();

			for(char carac : tab){
				if(!Character.isDigit(carac) && valeur){ valeur = false; }
			}

			return valeur;
		}

		public void actionPerformed(ActionEvent e) {
			
			//Date selectedDate = (Date) datePicker.getModel().getValue();
		    //DateFormat df = new SimpleDateFormat("y");
		    //String reportDate = df.format(selectedDate);
		    
			String year = tye.getText();
			if(!isInt(year)) year = "2000";
			
			String line = tfn.getText() + ",";
			line = line + tln.getText() + ",";
			line = line + tti.getText() + ",";
			line = line + year + ",";
			line = line + tdx.getText() + ",";
			line = line + tdy.getText() + ",";
			line = line + lco.getSelectedItem().toString() + ",";
			line = line + "End";
			dataFile.addLine(line);
			JOptionPane.showMessageDialog(pBCenter, "Book Added successfully");
			Component[] components = tab.getComponents();
			for (Component component : components) {
				if (component instanceof JTextField || component instanceof JTextArea) {
					JTextComponent specificObject = (JTextComponent) component;
					specificObject.setText("");
				}
			}
			tabPane.remove(3);
			tabPane.add("Delete Books from my Library", getPanelCentreDelete());
			tabPane.add("Delete Books from my Library",new JScrollPane(pDCenter));
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

}