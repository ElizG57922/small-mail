package network;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConnectedGUI extends JFrame{

	private final int WIDTH = 512;
	private final int HEIGHT = 512;

	private ConnectedPanel connectedPanel;
	private Client client;
	private ClientGUI cGUI;

	public ConnectedGUI (Client client, ClientGUI cGUI)
	{
		setTitle("Client GUI");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));

		this.client = client;
		this.cGUI = cGUI;

		connectedPanel = new ConnectedPanel();
		this.add(connectedPanel, BorderLayout.CENTER);

		setVisible(false);
	}

	public class ConnectedPanel extends JPanel {

		private JButton recoverPassButton, registerButton, loginButton, disconnectButton;

		public ConnectedPanel ()
		{
			prepareButtonHandlers();
			setLayout(new GridLayout(20, 1, 1, 1));				

			this.add(registerButton);
			this.add(loginButton);
			this.add(recoverPassButton);
			this.add(disconnectButton);
		}

		private void prepareButtonHandlers()
		{
			registerButton = new JButton("Register");
			registerButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							cGUI.showRegisterGUI();
						}
					}
					);

			loginButton = new JButton("Login");
			loginButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							cGUI.showLoginGUI();
						}
					}
					);

			recoverPassButton = new JButton("Password Recovery");
			recoverPassButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							cGUI.showPassRecoverGUI();
						}
					}
					);

			disconnectButton = new JButton("Disconnect");
			disconnectButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							client.disconnect();
							cGUI.showDisconnectedGUI();
						}
					}
					);
		}

		public Dimension getPreferredSize() 
		{
			return new Dimension(130, 500);
		}

	}
}