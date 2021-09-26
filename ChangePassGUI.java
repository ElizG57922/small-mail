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

public class ChangePassGUI extends JFrame{

	private int WIDTH = 512;
	private int HEIGHT = 512;

	private ChangePassPanel passChangePanel;
	private Client client;
	private ClientGUI cGUI;
	private String username;

	public ChangePassGUI (Client client, ClientGUI cGUI)
	{
		setTitle("Client GUI");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));

		this.client = client;
		this.cGUI = cGUI;

		passChangePanel = new ChangePassPanel();

		this.add(passChangePanel, BorderLayout.CENTER);

		setVisible(false);
	}
	
	public void setUsername(String name) {
		username = name;
	}

	public class ChangePassPanel extends JPanel {

		private JTextField oldPassword, newPassword, newPasswordConfirm;
		private JButton changePassButton;

		public ChangePassPanel ()
		{
			oldPassword = new JTextField();
			newPassword = new JTextField();
			newPasswordConfirm = new JTextField();
			prepareButtonHandlers();
			setLayout(new GridLayout(20, 1, 1, 1));


			this.add(new JLabel("Old Password"));
			this.add(oldPassword);
			this.add(new JLabel("New Password"));
			this.add(newPassword);
			this.add(new JLabel("New Password Confirm"));
			this.add(newPasswordConfirm);
			this.add(changePassButton);
		}

		private void prepareButtonHandlers()
		{
			changePassButton = new JButton("Password Change");
			changePassButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							String reply = client.sendString("changepass;" + username +";"+ oldPassword.getText() +";"+ newPassword.getText() +";"+ newPasswordConfirm.getText());
							if(reply.equals("success"))
						        cGUI.showLoggedinGUI();
							else if(reply.equals("no_confirmation_match"))
								newPasswordConfirm.setText("DOES NOT MATCH");
							else if(reply.equals("invalid_new_pass"))
								newPassword.setText("INVALID");
							else if(reply.equals("no_old_match"))
								oldPassword.setText("INCORRECT");
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