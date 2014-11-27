package client;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;

public class MorpionUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private Morpion morpion;
	private PanelGame game;
	private PanelConnection connection;
	
	
	public MorpionUI(Morpion morpion) {
		super();
		this.morpion = morpion;
		this.connection = new PanelConnection(this);
		this.game = new PanelGame(morpion);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(800, 600);
		this.setTitle("Morpion : se connecter");
		this.add(this.connection);
	}
	
	/**
	 * Accesseur à l'instance de Morpion
	 * @return l'instance de Morpion
	 */
	public Morpion getMorpion() {
		return this.morpion;
	}
	
	/**
	 * Change le panel actuellement affiché et met à jour le titre de la fenetre
	 */
	private void startGame() {
		this.remove(this.connection);
		this.setTitle("Morpion : " + this.morpion.getName() + " VERSUS " + this.morpion.getAdversaireName());
		this.add(this.game);
		this.validate();
	}
	
	/**
	 * Met à jour le plateau
	 * @param plateau les données du plateau
	 */
	public void update(String[][] plateau) {
		this.game.update(plateau);
	}
	
	/**
	 * Permat d'afficher le nom du vainqueur à la fin de la partie
	 * @param nom
	 */
	public void afficherVainqueur(String nom){
		
	}
	
	/**
	 * Methode qui se connecte au serveur et lance la partie si c'est possible sinon ...
	 * @param ip l'ip du serveur auquel se connecter
	 * @param name le pseudo de l'utilisateur
	 * @throws Exception 
	 * @throws IOException 
	 * @throws SocketTimeoutException 
	 */
	public void connect(String ip, String name) throws SocketTimeoutException, IOException, Exception {
		this.morpion.setName(name);
		this.morpion.connectToServ(ip);
		this.startGame();
	}

}
