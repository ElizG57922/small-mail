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

public class LoginGUI extends JFrame{

		private int WIDTH = 512;
		private int HEIGHT = 512;

		private LoginPanel loginPanel;
		
		private Client client;
		private ClientGUI cGUI;
		
		public LoginGUI (Client client, ClientGUI cGUI)
		{
			setTitle("Client GUI");
			setSize(WIDTH, HEIGHT);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout(5, 5));
			
			this.client = client;
			this.cGUI = cGUI;

			loginPanel = new LoginPanel();
		
    		this.add(loginPanel, BorderLayout.CENTER);
    		
			setVisible(false);
		}

		public class LoginPanel extends JPanel {

			private JTextField username, password;
			private JButton loginButton;
			
			public LoginPanel ()
			{
				username = new JTextField();
				password = new JTextField();
				prepareButtonHandlers();
				setLayout(new GridLayout(20, 1, 1, 1));			
				
				this.add(new JLabel("Username"));
				this.add(username);
				this.add(new JLabel("Password"));
				this.add(password);
				this.add(loginButton);
			}
			
			private void prepareButtonHandlers()
			{
			loginButton = new JButton("Login");
			loginButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String reply = client.sendString("login;" + username.getText() + ";" + password.getText());
						if(reply.equals("success"))
							cGUI.showLoggedinGUI(username.getText());
						else if(reply.equals("invaliduser"))
					    	username.setText("INVALID");
						else if(reply.equals("invalidpass"))
						    password.setText("INVALID");
						else if(reply.equals("lockedout"))
							username.setText("LOCKED OUT");
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