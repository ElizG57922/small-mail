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

public class LoggedinGUI extends JFrame{

	private int WIDTH = 512;
	private int HEIGHT = 512;

	private LoggedInPanel loggedinPanel;
	private Client client;
	private ClientGUI cGUI;
	private String username;

	public LoggedinGUI (Client client, ClientGUI cGUI)
	{
		setTitle("Client GUI");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));

		this.client = client;
		this.cGUI = cGUI;

		loggedinPanel = new LoggedInPanel();

		this.add(loggedinPanel, BorderLayout.CENTER);

		setVisible(false);
	}
	
	public void setUsername(String name) {
		username = name;
	}


	public class LoggedInPanel extends JPanel {

		private JButton changePassButton, logoutButton;

		public LoggedInPanel ()
		{
			prepareButtonHandlers();
			setLayout(new GridLayout(20, 1, 1, 1));

			this.add(changePassButton);
			this.add(logoutButton);
		}

		private void prepareButtonHandlers()
		{
			changePassButton = new JButton("Change Password");
			changePassButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							cGUI.showPassChangeGUI(username);
						}
					}
					);

			logoutButton = new JButton("Logout");
			logoutButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							String reply = client.sendString("logout;" + username);
							if(reply.equals("success"))
								cGUI.showConnectedGUI();
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