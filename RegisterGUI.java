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

public class RegisterGUI extends JFrame{

	private int WIDTH = 512;
	private int HEIGHT = 512;

	private RegisterPanel registerPanel;
	private Client client;
	private ClientGUI cGUI;

	public RegisterGUI (Client client, ClientGUI cGUI)
	{
		setTitle("Client GUI");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));

		this.client = client;
		this.cGUI = cGUI;

		registerPanel = new RegisterPanel();
		this.add(registerPanel, BorderLayout.CENTER);

		setVisible(false);
	}

	public class RegisterPanel extends JPanel {

		private JTextField username, password, email;
		private JButton registerButton;

		public RegisterPanel ()
		{
			username = new JTextField();
			password = new JTextField();
			email = new JTextField();
			prepareButtonHandlers();
			setLayout(new GridLayout(20, 1, 1, 1));

			this.add(new JLabel("Username"));
			this.add(username);
			this.add(new JLabel("Password"));
			this.add(password);
			this.add(new JLabel("Email"));
			this.add(email);
			this.add(registerButton);
		}

		private void prepareButtonHandlers()
		{
			registerButton = new JButton("Register");
			registerButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							String reply = client.sendString("register;" + username.getText() + ";" + password.getText() + ";" + email.getText());
							if(reply.equals("success"))
								cGUI.showConnectedGUI();
							else {
								if(reply.equals("invalidemail"))
    								email.setText("INVALID");
							    if(reply.equals("invalidpass"))
								    password.setText("INVALID");
							    else if(reply.equals("duplicateuser"))
							    	username.setText("DUPLICATE USERNAME");
							}
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