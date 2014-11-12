package client;

import javax.swing.SwingUtilities;

import request.Request;
import request.RequestPattern;
import request.RequestProcessing;
import connection.MyConnection;

public class Morpion {

	private MyClient client;
	private String nom;
	private boolean playing = false;
	private String[][] plateau;
	private RequestProcessing rp = RequestProcessing.getInstance();
	private MorpionUI mui;

	public Morpion() throws Exception {
		
		this.mui = new MorpionUI(this);
		SwingUtilities.invokeLater(() -> this.mui.setVisible(true));
		this.client = new MyClient();
		
		//envoi du nom au serveur
		//this.client.sendRequest(new Request(1, nom.getBytes()));

		// requete pour savoir quand c'est au client de jouer
		rp.add(new RequestPattern(0) {

			@Override
			public void process(Request r, MyConnection myConnection) {
				Morpion.this.playing = true;
				Morpion.this.plateau = Morpion.toString(toPlate(null));
				Morpion.this.mui.update(Morpion.this.plateau);
			}
		});
		
		// Win
		rp.add(new RequestPattern(1) {

			@Override
			public void process(Request r, MyConnection myConnection) {
				Morpion.this.playing = false;
			}
		});	
		
				
	}
	
	/**
	 * Accesseur au nom
	 * @return le nom
	 */
	public String getName() {
		return this.nom;
	}
	
	/**
	 * Methode qui doit etre apellé a chaque clic sur un bouton, si c'est au joueur de jouer,
	 * elle enverra les données au serveur
	 * @param ligne la ligne choisie
	 * @param colonne la colonne choisie
	 */
	public void updateOnClick(byte ligne, byte colonne) {
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
	private void sendChoice(byte ligne, byte colonne) {
			this.client.sendRequest(new Request(2, new byte[] {ligne, colonne }));
	}

	/**
	 * Change un tableau à une dimension en un tableau à 2 dimension pour le plateau
	 * @param data le tableau de données recu par le serveur
	 * @return le tableau transformé
	 */
	private static byte[][] toPlate(byte[] data) {
		return new byte[][] { new byte[] { data[0], data[1], data[2] },
				new byte[] { data[3], data[4], data[5] },
				new byte[] { data[6], data[7], data[8] }
		};
	}
	
	/**
	 * Change le tableau de bytes en String
	 * @param tab le plateau de données
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
