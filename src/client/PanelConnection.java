package client;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelConnection extends JPanel{

	private static final long serialVersionUID = 2L;
	
	private JTextField ip = new JTextField();
	private JTextField name = new JTextField();
	private JButton connection = new JButton("Commencer partie");
	private MorpionUI morpionUI;
	
	public PanelConnection(MorpionUI morionUI) {
		this.morpionUI = morionUI;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Creation de labels
		JLabel ipLabel = new JLabel("adresse IP : ");
		JLabel namLabel = new JLabel("votre pseudo : ");
		
		//Creation de panels
		JPanel ipPanel= new JPanel();
		JPanel namePanel = new JPanel();
		
		//ajout au panels pour mise en forme
		ipPanel.add(ipLabel);
		ipPanel.add(this.ip);
		namePanel.add(namLabel);
		namePanel.add(this.name);
		
		//ajout au panel courant (this)
		this.add(ipPanel);
		this.add(namePanel);
		this.add(this.connection);
		
		//on se connecte quand on clic sur le bouton
		this.connection.addActionListener(e -> {
			PanelConnection.this.morpionUI.connect(PanelConnection.this.ip.getText(), PanelConnection.this.name.getText());
		});
	}

}
