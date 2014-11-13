package client;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.swing.SwingUtilities;

import request.Request;
import request.RequestPattern;
import request.RequestProcessing;
import connection.MyConnection;

public class Morpion {

	private MyClient client;
	private String name;
	private boolean playing = false;
	private String[][] plateau;
	private RequestProcessing rp = RequestProcessing.getInstance();
	private MorpionUI mui;

	public Morpion() {
		
		this.mui = new MorpionUI(this);
		SwingUtilities.invokeLater(() -> this.mui.setVisible(true));
		this.client = new MyClient();

		// requete pour savoir quand c'est au client de jouer
		try {
			rp.add(new RequestPattern(0) {

				@Override
				public void process(Request r, MyConnection myConnection) {
					Morpion.this.playing = true;
					Morpion.this.plateau = Morpion.toString(toPlate(r.data));
					Morpion.this.mui.update(Morpion.this.plateau);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Win
		try {
			rp.add(new RequestPattern(1) {

				@Override
				public void process(Request r, MyConnection myConnection) {
					Morpion.this.playing = false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
				
	}
	
	/**
	 * Accesseur au nom
	 * @return le nom
	 */
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Methode qui se connecte au serveur si elle y arrive et envoi une requete comportant le nom
	 * @param ip l'ip du serveur
	 * @throws SocketTimeoutException si le socket n'a pas repondu assez vite (10 ms)
	 * @throws IOException si la connexion ne s'�tablie pas
	 * @throws Exception dans les autres cas
	 */
	public void connectToServ(String ip) throws SocketTimeoutException, IOException, Exception {
		this.client.connect(ip, 5555, 10);
		
		//envoi du nom au serveur
		this.client.sendRequest(new Request(1, this.name));
	}
	
	/**
	 * Methode qui doit etre apell� a chaque clic sur un bouton, si c'est au joueur de jouer,
	 * elle enverra les donn�es au serveur
	 * @param ligne la ligne choisie
	 * @param colonne la colonne choisie
	 */
	public void updateOnClick(int ligne, int colonne) {
		if (this.playing) {
			this.sendChoice(ligne, colonne);
			this.plateau[ligne][colonne] = "X";
			this.playing = false;
			this.mui.update(this.plateau);
		}
	}

	/**
	 * Envoi au serveur une requete contenant le choix de l'utilisateur
	 * @param ligne la ligne selectionnee
	 * @param colonne la colonne selectionnee
	 */
	private void sendChoice(int ligne, int colonne) {
			this.client.sendRequest(new Request(2, new int[] {ligne, colonne }));
	}

	/**
	 * Change un tableau � une dimension en un tableau � 2 dimension pour le plateau
	 * @param data le tableau de donn�es recu par le serveur
	 * @return le tableau transform�
	 */
	private static byte[][] toPlate(byte[] data) {
		return new byte[][] { new byte[] { data[0], data[1], data[2] },
				new byte[] { data[3], data[4], data[5] },
				new byte[] { data[6], data[7], data[8] }
		};
	}
	
	/**
	 * Change le tableau de bytes en String
	 * @param tab le plateau de donn�es
	 * @return le plateau a afficher
	 */
	private static String[][] toString(byte[][] tab) {
		String[][] tableau = new String[3][3];
		for (int i = 0; i < tab.length; i++) {
			for(int j = 0; j < tab[i].length; j++)
				if (tab[i][j] == 0)
					tableau[i][j] = "X";
				else if (tab[i][j] == 1)
					tableau[i][j] = "Y";
				else 
					tableau[i][j] = "";
		}
		return tableau;
	}


	public static void main(String[] args) {
		try {
			new Morpion();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
