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

public class ConnectGUI extends JFrame{

	private final int WIDTH = 512;
	private final int HEIGHT = 512;

	private DisconnectedPanel disconnectedPanel;
	private Client client;
	private ClientGUI cGUI;

	public ConnectGUI (Client client, ClientGUI cGUI)
	{
		setTitle("Client GUI");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));

		this.client = client;
		this.cGUI = cGUI;

		disconnectedPanel = new DisconnectedPanel();

		this.add(disconnectedPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	public class DisconnectedPanel extends JPanel {

		private JButton connectButton;
		private JTextField ip, port;

		public DisconnectedPanel ()
		{
			ip = new JTextField("127.0.0.1");
			port = new JTextField("8000");
			prepareButtonHandlers();
			setLayout(new GridLayout(20, 1, 1, 1));

			this.add(new JLabel("Server IP"));
			this.add(ip);
			this.add(new JLabel("Port"));
			this.add(port);
			this.add(connectButton);
		}

		private void prepareButtonHandlers()
		{
			connectButton = new JButton("Connect");
			connectButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							client.connect(ip.getText(), getPort());
							cGUI.showConnectedGUI();
						}
					}
					);
		}
		private int getPort() {
			return Integer.parseInt(port.getText());
		}

		public Dimension getPreferredSize() 
		{
			return new Dimension(130, 500);
		}

	}
}