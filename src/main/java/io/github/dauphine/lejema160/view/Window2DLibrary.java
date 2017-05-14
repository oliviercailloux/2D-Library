package io.github.dauphine.lejema160.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;



public class Window2DLibrary extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton generate;
	private JTextField presentation;
	private JTextArea options;
	private JTextArea visuG;
	private JLabel opt;
	private JLabel opt2; //a supp
	private JLabel opt3; // a supp
	private Image myLyb; // library picture 

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
	public JTabbedPane getPanelCentre(){ // Une tab d'onglet avec 2 onglets, un pour les options et lautre pour afficher sa librairie

		JTabbedPane tabPane = new JTabbedPane();

		JPanel panOptions = new JPanel(new BorderLayout());
		this.options=new JTextArea();
		options.setFont(new Font("Arial", Font.BOLD, 60));
		panOptions.add(options,"Center");
		tabPane.add("Options for my library",getPanelCentreOptions());


		JPanel panBooks= new JPanel(new BorderLayout());
		this.visuG=new JTextArea();
		visuG.setFont(new Font("Arial", Font.BOLD, 60));
		panBooks.add(visuG,"Center");
		tabPane.add("My Library",new PanelLibrary()); 
		return tabPane;

	}
/**
 * creates panel with the options for the user
 * @return
 */
	public JPanel getPanelCentreOptions(){ // panel qui contient les checkbox des options de generations

		JPanel centre= new JPanel();

		// Premiere Colonne
		JPanel optName= new JPanel(new GridLayout(0,1,40,40));
		JLabel titleFirstColumn= new JLabel("Parameters");
		//faudra faire une boucle ici quand on aura les differentes options
		opt= new JLabel("TEST                                     ");
		opt.setVisible(true);
		opt2= new JLabel("TEST                                    ");
		opt.setVisible(true);
		opt3= new JLabel("TEST                                     ");
		opt.setVisible(true);
		centre.add(optName);
		optName.add(titleFirstColumn);
		optName.add(opt);
		optName.add(opt2);//pr test
		optName.add(opt3);//pr test

		//Deuxieme Colonne
		JPanel optCheck= new JPanel(new GridLayout(0,1,40,40));
		JLabel titleSecondColumn= new JLabel("Choices");
		JCheckBox check = new JCheckBox();
		check.setSelected(true);
		JCheckBox check2 = new JCheckBox();
		check2.setSelected(true);
		JCheckBox check3 = new JCheckBox();
		check3.setSelected(true);
		centre.add(optCheck);
		optCheck.add(titleSecondColumn);
		optCheck.add(check);
		optCheck.add(check2);
		optCheck.add(check3);

		return centre;

	}

	/**
	 * creates a panel that contains the layout of the library (not finished)
	 * @author Olympie
	 *
	 */
	public class PanelLibrary extends JPanel{ // enfait ca c'est un panel complet qui va integrer limage de la librairie si on arrive a coder propre... lol
		public void paintComponent(Graphics g){
			super.paintComponent(g);;
			String nom= "/home/titine/Téléchargements/Books.jpg";
			myLyb = Toolkit.getDefaultToolkit().getImage(nom);
			g.drawImage(myLyb, 0, 0, this);
			
		}
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

	public static void main(String[] args) {

		new Window2DLibrary("2D_LIBRARY PROJECT");

	}
}



