package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window2DLibrary extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton generate;
	
	public Window2DLibrary(String title) {

		super(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.dimension();
		this.initialise();
		this.setVisible(true);

	}

	public void dimension(){
		Toolkit atk= Toolkit.getDefaultToolkit();
		Dimension dim =atk.getScreenSize();
		int h=dim.height;
		int w=dim.width;
		this.setSize(w,h);

	}

	public void initialise(){
		Container c=this.getContentPane();
		c.add(this.getPanelSud(),BorderLayout.SOUTH);
		
	}

	public JPanel getPanelSud(){

		JPanel sud= new JPanel();
		generate = new JButton("Génerer ma librairie");
		sud.add(generate);
		return sud;
	}
	
	
	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String s= e.getActionCommand();
			if(s.equals("Génerer ma librairie")){
				// Ajout methode
			}
		}
	}
			
	public static void main(String[] args) {
		
		new Window2DLibrary("2D_LIBRARY");

	}
}



