package io.github.dauphine.lejema160.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import javax.swing.*;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.dauphine.lejema160.controller.BookSort;



public class Window2DLibrary extends JFrame {

	public static final Logger LOGGER = LoggerFactory.getLogger(Window2DLibrary.class);

	//final static Logger logger = Logger.getLogger(Wisndow2DLibrary.class);
	private static final long serialVersionUID = 1L;
	private JButton generate;
	private JTextField presentation;

	private JTextArea options;
	private JTextArea visuG;
	private JTextArea actions;
	private ImageIcon myLibIcon;
	private JLabel libImage;
	private ButtonGroup bookColor;
	private ButtonGroup shelveColor;
	private ButtonGroup backgroundColor;
	private ButtonGroup lean;
	private JRadioButton bDarkB, bLightB, bAutoB, bLeanS, bNotLeanS, bDarkBk, bLightBk, bAutoBk,bDarkS, bLightS, bAutoS;
	private JLabel bkL, bL, sL, lL;
	private JLabel fn,ln,ti,ye,dx,dy,co;
	private JTextField tfn,tln,tti,tye,tdx,tdy,tco;
	/**
	 * constructor of the window
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
	 * function that sets the dimensions of the window
	 * takes by default the dimensions of the screen
	 */
	public void dimension(){
		Toolkit atk= Toolkit.getDefaultToolkit();
		Dimension dim =atk.getScreenSize();
		int h=dim.height;
		int w=dim.width;
		this.setSize(w,h);

	}

	/**
	 * creates the panels of the window
	 */
	public void initialise(){
		Container c=this.getContentPane();
		c.add(this.getPanelSud(),BorderLayout.SOUTH);
		c.add(this.getPanelNord(),BorderLayout.NORTH);
		c.add(this.getPanelCentre(),BorderLayout.CENTER);
	}

	/**
	 * creates north panel : title
	 * @return
	 */
	public JPanel getPanelNord(){
		JPanel nord= new JPanel();
		JPanel head= new JPanel();
		JLabel score= new JLabel("Welcome to your Library :-)  ");
		presentation= new JTextField(10);
		presentation.setEditable(false);
		presentation.setVisible(false);
		nord.add(head);
		head.add(score);
		head.add(presentation);

		return nord;
	}

	/**
	 * creates south panel : button to generate the library
	 * @return
	 */
	public JPanel getPanelSud(){

		JPanel sud= new JPanel();
		generate = new JButton("Generate my library");
		generate.addActionListener(new BoutonListener());
		sud.add(generate);
		return sud;
	}

	/**
	 * creates center panel : options and layout of the library
	 * @return
	 */
	//TODO: modify color after validate ; horrible color only for help to identify space of
	public JTabbedPane getPanelCentre(){ // Une tab d'onglet avec 2 onglets, un pour les options et lautre pour afficher sa librairie

		JTabbedPane tabPane = new JTabbedPane();

		JPanel panOptions = new JPanel(new BorderLayout());
		this.options=new JTextArea();
		panOptions.setBackground(Color.orange);
		options.setFont(new Font("Arial", Font.BOLD, 60));
		panOptions.add(options,"Center");
		tabPane.add("Options for my library",getPanelCentreOptions());

		JPanel panBooks= new JPanel(new BorderLayout());
		this.visuG=new JTextArea();
		visuG.setFont(new Font("Arial", Font.BOLD, 60));
		panBooks.add(visuG,"Center");
		//tabPane.add("My Library",new PanelLibrary());
		tabPane.add("My Library",getPanelCentreLib());
		 
		
		
		JPanel panActions= new JPanel(new BorderLayout());
		this.actions=new JTextArea();
		panActions.setBackground(Color.orange);
		actions.setFont(new Font("Arial", Font.BOLD, 60));
		panActions.add(actions,"Center");
		tabPane.add("Books in my Library",getPanelCentreBooks());
		return tabPane;

	}



	/**
	 * creates panel with the last updates library
	 * @return
	 */
	public JPanel getPanelCentreLib(){ 

		JPanel pCenter = new JPanel(new BorderLayout());
		this.libImage = new JLabel();
		myLibIcon = new ImageIcon("library.png"); 
		libImage.setIcon(myLibIcon);
		JScrollPane asc = new JScrollPane(this.libImage);
		pCenter.add(asc);

		return pCenter;

	}


	public JPanel getPanelCentreBooks(){ 

		JPanel pBCenter = new JPanel(new BorderLayout());
		
		JPanel tab= new JPanel(new GridLayout(9,2,10,10));

		pBCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		//JPanel param= new JPanel();
		//JPanel choice= new JPanel();
		
		JLabel titleFirstColumn= new JLabel("Add Book");
		//param.setOpaque(false);
		titleFirstColumn.setFont(new Font("Arial", Font.ITALIC, 50));
		

		fn = new JLabel("First Name : ");
		ln = new JLabel("Last Name : ");
		ti = new JLabel("Title : ");
		ye = new JLabel("Year : ");
		dx = new JLabel("DimX : ");
		dy = new JLabel("DimY : ");
		co = new JLabel("Color : ");
		
		JLabel titleSecondColumn= new JLabel("");
		//choice.setOpaque(false);
		titleSecondColumn.setFont(new Font("Arial", Font.ITALIC, 50));
		
		
	    tfn=new JTextField();
	    tfn.setBounds(5, 5, 50, 25);
	    tln=new JTextField();
	    tln.setBounds(5, 5, 50, 25);
	    tti=new JTextField();
	    tti.setBounds(5, 5, 100, 50);
	    tye=new JTextField();
	    tye.setBounds(5, 5, 100, 50);
	    tdx=new JTextField();
	    tdx.setBounds(5, 5, 100, 50);
	    tdy=new JTextField();
	    tdy.setBounds(5, 5, 100, 50);
	    tco=new JTextField();
	    tco.setBounds(5, 5, 100, 50);
	   
	    

		tab.add(titleFirstColumn);
		tab.add(titleSecondColumn);
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
		tab.add(tco);
		tab.setOpaque(false);
		//tab.add(param);
		//tab.add(choice);
		JButton button = new JButton("ADD");
		tab.add(button);
		pBCenter.add(tab);
		
		pBCenter.setBackground(Color.decode("#51DAA8"));
		return pBCenter;

	}

	/**
	 * creates panel with the options for the user
	 * @return
	 */
	public JPanel getPanelCentreOptions(){ 

		JPanel centre= new JPanel();

		JPanel optName= new JPanel(new GridLayout(0,2,40,30));

		JPanel param= new JPanel();
		JPanel choice= new JPanel();
		optName.add(param);
		optName.add(choice);
		JLabel titleFirstColumn= new JLabel("Parameters");
		param.setOpaque(false);
		titleFirstColumn.setFont(new Font("Arial", Font.ITALIC, 50));
		JPanel bkCT= new JPanel();
		bkCT.setOpaque(false);
		JPanel sCT= new JPanel();
		sCT.setOpaque(false);
		JPanel bCT= new JPanel();
		bCT.setOpaque(false);
		JPanel lT= new JPanel();
		lT.setOpaque(false);
		bL = new JLabel("Books Color : ");
		bkL = new JLabel("Background Color : ");
		sL = new JLabel("Shelve Color : ");
		lL = new JLabel("Position of books : ");

		JLabel titleSecondColumn= new JLabel("Choices");
		choice.setOpaque(false);
		titleSecondColumn.setFont(new Font("Arial", Font.ITALIC, 50));
		JPanel bkC= new JPanel();
		bkC.setOpaque(false);
		JPanel sC= new JPanel();
		sC.setOpaque(false);
		JPanel bC= new JPanel();
		bC.setOpaque(false);
		JPanel l= new JPanel();
		l.setOpaque(false);
		backgroundColor=new ButtonGroup();
		lean=new ButtonGroup();
		bookColor=new ButtonGroup();
		shelveColor=new ButtonGroup();
		backgroundColor.add(bAutoBk=new JRadioButton("Auto"));
		backgroundColor.add(bLightBk=new JRadioButton("Light"));
		backgroundColor.add(bDarkBk=new JRadioButton("Dark"));
		bAutoBk.setOpaque(false);
		bLightBk.setOpaque(false);
		bDarkBk.setOpaque(false);
		bAutoBk.setSelected(true);
		shelveColor.add(bAutoS=new JRadioButton("Auto"));
		shelveColor.add(bLightS=new JRadioButton("Light"));
		shelveColor.add(bDarkS=new JRadioButton("Dark"));
		bAutoS.setOpaque(false);
		bLightS.setOpaque(false);
		bDarkS.setOpaque(false);
		bAutoS.setSelected(true);
		bookColor.add(bAutoB=new JRadioButton("Auto"));
		bookColor.add(bLightB=new JRadioButton("Light"));
		bookColor.add(bDarkB=new JRadioButton("Dark"));
		bAutoB=new JRadioButton("Auto");
		bAutoB.setOpaque(false);
		bLightB.setOpaque(false);
		bDarkB.setOpaque(false);
		bAutoB.setSelected(true);
		lean.add(bLeanS=new JRadioButton("Leaned"));
		lean.add(bNotLeanS=new JRadioButton("Not leaned"));
		bLeanS.setOpaque(false);
		bNotLeanS.setOpaque(false);
		bLeanS.setSelected(true);
		bkCT.add(bkL);
		sCT.add(sL);
		bCT.add(bL);
		lT.add(lL);
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
		optName.setOpaque(false);
		centre.add(optName);
		centre.setBackground(Color.decode("#51DAA8"));


		return centre;

	}
	
	



	class BoutonListener implements ActionListener{
		/**
		 * function launched when the user performs an action
		 */

		public void actionPerformed(ActionEvent e){
			String s= e.getActionCommand();
			if(s.equals("Generate my library")){
				// methode genere image librairie
				generate.setText("Update my library now");


			}
			if(s.equals("Update my library now")){
				// remettre meme code ici pour la maj de la librairie
			}


		}
	}


}



