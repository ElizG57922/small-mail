package network;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PassRecoverGUI extends JFrame{

	private final int WIDTH = 512;
	private final int HEIGHT = 512;

	private PasswordRecoveryPanel passRecPanel;

	private Client client;
	private ClientGUI cGUI;

	public PassRecoverGUI (Client client, ClientGUI cGUI)
	{
		setTitle("Client GUI");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));

		this.client = client;
		this.cGUI = cGUI;

		passRecPanel = new PasswordRecoveryPanel();

		this.add(passRecPanel, BorderLayout.CENTER);

		setVisible(false);
	}

	public class PasswordRecoveryPanel extends JPanel {

		private JTextField username;
		private JButton recoverPassButton;

		public PasswordRecoveryPanel ()
		{
			username = new JTextField();
			prepareButtonHandlers();
			setLayout(new GridLayout(20, 1, 1, 1));			

			this.add(new JLabel("Username"));
			this.add(username);
			this.add(recoverPassButton);
		}

		private void prepareButtonHandlers()
		{
			recoverPassButton = new JButton("Password Recovery");
			recoverPassButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							String reply = client.sendString("recoverpass;" + username.getText());
							if(reply.equals("success"))
						        cGUI.showConnectedGUI();
							else if(reply.equals("invaliduser"))
								username.setText("INVALID");
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