package io.github.dauphine.lejema160.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.dauphine.lejema160.controller.ConnectionToCongressLibrary;
import io.github.dauphine.lejema160.model.Book;
import io.github.dauphine.lejema160.model.Library;

public class Window2DLibrary extends JFrame {

	public static final Logger LOGGER = LoggerFactory.getLogger(Window2DLibrary.class);

	// final static Logger logger = Logger.getLogger(Wisndow2DLibrary.class);
	private static final long serialVersionUID = 1L;
	private JButton generate, lessBookPerS, moreBookPerS;
	private JTextField presentation;
	private JFormattedTextField nbBooksPerSText;
	private JPanel pCenter;
	private JPanel sud;
	private JPanel panBooks;
	private JTabbedPane tabPane;
	private JTextArea options;
	private JTextArea visuG;
	private JTextArea actions;
	private ImageIcon myLibIcon;
	private JLabel libImage;
	private ButtonGroup booksColor;
	private ButtonGroup shelvesColor;
	private ButtonGroup backgroundColor;
	private ButtonGroup lean;
	private JRadioButton bDarkB, bLightB, bAutoB, bLeanS, bNotLeanS, bDarkBk, bLightBk, bAutoBk, bDarkS, bLightS,
			bAutoS;
	private JLabel bkL, bL, sL, lL, etiqNb;
	private JLabel se, fn, ln, ti, ye, dx, dy, co;
	private JTextField tse, tfn, tln, tti, tye, tdx, tdy;
	private String bColor = "Auto", bkColor = "Auto", sColor = "Auto";
	private boolean leaning = true;
	private int nbBooksPerShelf = 10;

	/**
	 * constructor of the window
	 * 
	 * @param title
	 */
	public Window2DLibrary(String title) {

		super(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.dimension();
		this.initialise();
		this.setVisible(true);

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

		JPanel panDelete = new JPanel(new BorderLayout());
		this.actions = new JTextArea();
		panDelete.setBackground(Color.orange);
		actions.setFont(new Font("Arial", Font.BOLD, 60));
		panDelete.add(actions, "Center");
		tabPane.add("Delete Books from my Library", getPanelCentreDelete());
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
		myLibIcon = new ImageIcon("library.png");
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

	public void updateLibrary() {
		// methode genere image librairie

		generate.setText("Reload my library now");

		List<Book> books = io.github.dauphine.lejema160.controller.readFile.read();
		Library Lib = new Library(books, nbBooksPerShelf);

		try {
			io.github.dauphine.lejema160.view.SVGDrawable.generate(Lib, leaning, bkColor, bColor, sColor);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Svg2jpg.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * pCenter.removeAll(); pCenter.revalidate(); libImage = new JLabel();
		 * myLibIcon= new ImageIcon("library.png"); libImage.setIcon(myLibIcon);
		 * pCenter.add(libImage); JScrollPane asc = new
		 * JScrollPane(this.libImage); pCenter.add(asc); pCenter.updateUI();
		 */

	}

	public JPanel getPanelCentreDelete() {
		List<Book> books = io.github.dauphine.lejema160.controller.readFile.read();
		Library lib = new Library(books, 5);
		int nbBooks = 0;
		for (int i = 0; i < lib.getShelves().size(); i++) {
			nbBooks += lib.getShelves().get(i).getBooks().size();
		}

		JPanel pDCenter = new JPanel(new BorderLayout());
		JPanel tab = new JPanel(new GridLayout(nbBooks, 2, 10, 10));
		ButtonGroup bg = new ButtonGroup();
		JButton button = new JButton("Remove");

		List<JRadioButton> cbarr = new ArrayList<JRadioButton>();

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

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == button) {
					Enumeration<AbstractButton> allRadioButton = bg.getElements();
					while (allRadioButton.hasMoreElements()) {
						JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
						if (temp.isSelected()) {
							JOptionPane.showMessageDialog(null, "You selected : " + temp.getName());
							io.github.dauphine.lejema160.controller.deleteBook.deleteB(temp.getName());
						}
					}
				}
			}
		};

		int indexShelf = 0;
		int indexBook = 0;
		for (@SuppressWarnings("unused") Book book : books) {
			String bookTitle = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getTitle();
			String authorFirstName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor()
					.getFirstName();
			String authorLastName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor()
					.getLastName();
			int bookyear = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getYear();
			String bookString = authorLastName + "," + authorFirstName + "," + bookTitle + ","
					+ String.valueOf(bookyear);

			JRadioButton cb = new JRadioButton(bookString);
			cbarr.add(cb);
			tab.add(cb);
			cb.setName(bookString);
			bg.add(cb);
			button.addActionListener(listener);

			if (indexBook == lib.getShelves().get(indexShelf).getBooks().size() - 1
					&& !(indexShelf == lib.getShelves().size() - 1)) {
				indexShelf++;
				indexBook = 0;
			} else if (!(indexShelf == lib.getShelves().size() - 1
					&& indexBook == lib.getShelves().get(indexShelf).getBooks().size() - 1)) {
				indexBook++;
			}
		}

		pDCenter.add(tab);
		pDCenter.add(button, BorderLayout.SOUTH);

		return pDCenter;
	}

	public JPanel getPanelCentreBooks() {

		JPanel pBCenter = new JPanel(new BorderLayout());

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

		tse = new JTextField();
		tse.setBounds(10, 10, 200, 200);
		tse.setPreferredSize(new Dimension(160, 40));

		tfn = new JTextField();
		tfn.setBounds(5, 5, 200, 200);
		tln = new JTextField();
		tln.setBounds(5, 5, 50, 25);
		tti = new JTextField();
		tti.setBounds(5, 5, 100, 50);
		tye = new JTextField();
		tye.setBounds(5, 5, 100, 50);
		tdx = new JTextField();
		tdx.setBounds(5, 5, 100, 50);
		tdy = new JTextField();
		tdy.setBounds(5, 5, 100, 50);
		String[] choices = { "rose", "violet", "bleu", "orange", "jaune"};
		final JComboBox<String> lco = new JComboBox<String>(choices);

		JButton buttonS = new JButton("Search");

		tab.add(titleFirstColumn);
		tab.add(titleSecondColumn);
		tab.add(se);
		search.add(tse);
		search.add(buttonS);
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
		JButton button = new JButton("ADD");
		tab.add(button);
		pBCenter.add(tab);

		buttonS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonS) {

					String line = tse.getText();
					ConnectionToCongressLibrary connexion = new ConnectionToCongressLibrary(line); 
					
					String fileTest = connexion.getResult();
					
					String tabResult[] = new String[3];
					tabResult = io.github.dauphine.lejema160.controller.ExtractBookData.ExtractData(fileTest);
					for (int i = 0; i<3; i++){
						
						System.out.println(tabResult[i]);
					}
					tti.setText(tabResult[0]);
					String[] np = tabResult[1].split(",");
					tln.setText(np[0]);
					tfn.setText(np[1]);
					tye.setText(tabResult[2]);
					JOptionPane.showMessageDialog(pBCenter, "Search result");
				}
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button) {
					String line = tfn.getText() + ",";
					line = line + tln.getText() + ",";
					line = line + tti.getText() + ",";
					line = line + tye.getText() + ",";
					line = line + tdx.getText() + ",";
					line = line + tdy.getText() + ",";
					line = line + lco.getSelectedItem().toString() + ",";
					line = line + "End";
					io.github.dauphine.lejema160.controller.writeFile.AddBook(line);
					JOptionPane.showMessageDialog(pBCenter, "Book Added succesfully");
				}
			}
		});

		// pBCenter.setBackground(Color.decode("#51DAA8"));
		return pBCenter;

	}

	/**
	 * creates panel with the options for the user
	 * 
	 * @return
	 */
	public JPanel getPanelCentreOptions() {

		JPanel centre = new JPanel();
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
		JPanel bkCT = new JPanel();
		bkCT.setOpaque(false);
		JPanel sCT = new JPanel();
		sCT.setOpaque(false);
		JPanel bCT = new JPanel();
		bCT.setOpaque(false);
		JPanel lT = new JPanel();
		lT.setOpaque(false);
		JPanel nbB = new JPanel();
		nbB.setOpaque(false);

		bkL = new JLabel("Background Color : ");
		bkL.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		sL = new JLabel("Shelves Color : ");
		sL.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		bL = new JLabel("Books Color : ");
		bL.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		lL = new JLabel("Position of books : ");
		lL.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		etiqNb = new JLabel("Books per shelf:");
		etiqNb.setFont(new Font("Book Antiqua", Font.ITALIC, 25));
		nbBooksPerSText = new JFormattedTextField(nbBooksPerShelf);

		JLabel titleSecondColumn = new JLabel("Choices");
		choice.setOpaque(false);
		titleSecondColumn.setFont(new Font("Book Antiqua", Font.ITALIC, 55));
		titleSecondColumn.setForeground(Color.decode("#b32d00"));
		
		JPanel bkC = new JPanel();
		bkC.setOpaque(false);
		JPanel sC = new JPanel();
		sC.setOpaque(false);
		JPanel bC = new JPanel();
		bC.setOpaque(false);
		JPanel l = new JPanel();
		l.setOpaque(false);
		JPanel textNB = new JPanel();
		textNB.setOpaque(false);
		
		backgroundColor = new ButtonGroup();
		shelvesColor = new ButtonGroup();
		booksColor = new ButtonGroup();
		lean = new ButtonGroup();
		
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
		
		lean.add(bLeanS = new JRadioButton("Leaned"));
		lean.add(bNotLeanS = new JRadioButton("Not leaned"));
		bLeanS.addActionListener(new LeaningButtonListener());
		bNotLeanS.addActionListener(new LeaningButtonListener());
		bLeanS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bNotLeanS.setFont(new Font("Book Antiqua", Font.ITALIC, 20));
		bLeanS.setOpaque(false);
		bNotLeanS.setOpaque(false);
		bLeanS.setSelected(true);
		
		lessBookPerS = new JButton("Less");
		lessBookPerS.addActionListener(new lessBookPerShelfListener());
		moreBookPerS = new JButton("More");
		moreBookPerS.addActionListener(new moreBookPerShelfListener());

		bkCT.add(bkL);
		sCT.add(sL);
		bCT.add(bL);
		lT.add(lL);
		nbB.add(etiqNb);
		nbB.add(nbBooksPerSText);
		bkC.add(bAutoBk);
		bkC.add(bLightBk);
		bkC.add(bDarkBk);
		sC.add(bAutoS);
		sC.add(bLightS);
		sC.add(bDarkS);
		bC.add(bAutoB);
		bC.add(bLightB);
		bC.add(bDarkB);
		l.add(bLeanS);
		l.add(bNotLeanS);
		textNB.add(lessBookPerS);
		textNB.add(moreBookPerS);
		
		param.add(titleFirstColumn);
		choice.add(titleSecondColumn);
		
		optName.add(bkCT);
		optName.add(bkC);
		optName.add(sCT);
		optName.add(sC);
		optName.add(bCT);
		optName.add(bC);
		optName.add(lT);
		optName.add(l);
		optName.add(nbB);
		optName.add(textNB);
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
				updateLibrary();
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
	
	class moreBookPerShelfListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			nbBooksPerShelf++;
			nbBooksPerSText.setValue(nbBooksPerShelf);
		}
	}
	
	class lessBookPerShelfListener implements ActionListener {
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e) {
			nbBooksPerShelf--;
			nbBooksPerSText.setValue(nbBooksPerShelf);
		}
	}

}
