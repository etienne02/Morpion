package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.SocketTimeoutException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelConnection extends JPanel{

	private static final long serialVersionUID = 2L;
	
	private JTextField ip = new JTextField(20);
	private JTextField name = new JTextField(20);
	private JButton connection = new JButton("Commencer partie");
	private MorpionUI morpionUI;
	
	public PanelConnection(MorpionUI morionUI) {
		this.morpionUI = morionUI;
		this.setLayout(new BorderLayout());
		
		// TODO : TEMP
		ip.setText("10.31.4.136");
		
		//Creation de labels
		JLabel ipLabel = new JLabel("adresse IP : ");
		JLabel namLabel = new JLabel("votre pseudo : ");
		JLabel erreur = new JLabel();
		erreur.setForeground(Color.RED);
		
		//Creation de panels
		JPanel ipPanel= new JPanel();
		JPanel namePanel = new JPanel();
		JPanel champsPanel = new JPanel();
		JPanel boutonPanel = new JPanel();
		
		//ajout au panels pour mise en forme
		ipPanel.add(ipLabel);
		ipPanel.add(this.ip);
		ipPanel.add(erreur);
		namePanel.add(namLabel);
		namePanel.add(this.name);
		champsPanel.add(ipPanel);
		champsPanel.add(namePanel);
		boutonPanel.add(this.connection);
		
		champsPanel.setLayout(new BoxLayout(champsPanel, BoxLayout.Y_AXIS));
		
		//ajout au panel courant (this)
		this.add(champsPanel, BorderLayout.CENTER);
		this.add(boutonPanel, BorderLayout.SOUTH);
		
		//on se connecte quand on clic sur le bouton
		this.connection.addActionListener(e -> {
			try {
				PanelConnection.this.morpionUI.connect(PanelConnection.this.ip.getText(), PanelConnection.this.name.getText());
			}catch (SocketTimeoutException e1) {
				erreur.setText("le serveur ne repond pas");
			}catch (Exception e2) {
				erreur.setText("ip incorrecte");
			}
		});
	}

}
