package client;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelGame extends JPanel{

	private static final long serialVersionUID = 3L;
	
	private Morpion morpion;
	
	private JButton b1 = new JButton("");
	private JButton b2 = new JButton("");
	private JButton b3 = new JButton("");
	private JButton b4 = new JButton("");
	private JButton b5 = new JButton("");
	private JButton b6 = new JButton("");
	private JButton b7 = new JButton("");
	private JButton b8 = new JButton("");
	private JButton b9 = new JButton("");
	
	public PanelGame(Morpion morpion) {
		this.morpion = morpion;
		
		//Gestion du layout
		this.setLayout(new GridLayout(3, 3, 10, 10));
		
		//L'ajout des boutons à la fenetre
		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(b5);
		this.add(b6);
		this.add(b7);
		this.add(b8);
		this.add(b9);
		
		//L'ajout d'action aux boutons
		b1.addActionListener(e -> this.morpion.updateOnClick(0, 0));
		b2.addActionListener(e -> this.morpion.updateOnClick(0, 1));
		b3.addActionListener(e -> this.morpion.updateOnClick(0, 2));
		b4.addActionListener(e -> this.morpion.updateOnClick(1, 0));
		b5.addActionListener(e -> this.morpion.updateOnClick(1, 1));
		b6.addActionListener(e -> this.morpion.updateOnClick(1, 2));
		b7.addActionListener(e -> this.morpion.updateOnClick(2, 0));
		b8.addActionListener(e -> this.morpion.updateOnClick(2, 1));
		b9.addActionListener(e -> this.morpion.updateOnClick(2, 2));
		
	}
	
	/**
	 * Met � jour le l'affichage du plateau
	 * @param tab le plateau contenant les données à afficher
	 */
	public void update(String[][] tab) {		
		this.b1.setText(tab[0][0]);
		this.b2.setText(tab[0][1]);
		this.b3.setText(tab[0][2]);
		this.b4.setText(tab[1][0]);
		this.b5.setText(tab[1][1]);
		this.b6.setText(tab[1][2]);
		this.b7.setText(tab[2][0]);
		this.b8.setText(tab[2][1]);
		this.b9.setText(tab[2][2]);
	}

}
