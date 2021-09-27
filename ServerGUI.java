package network;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerGUI extends JFrame{

		private final int WIDTH = 512;
		private final int HEIGHT = 512;

		private ControlPanel controlPanel;
		
		public ServerGUI ()
		{
			setTitle("Server GUI");
			setSize(WIDTH, HEIGHT);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout(5, 5));
			controlPanel = new ControlPanel();
			this.add(controlPanel, BorderLayout.CENTER);
			setVisible(true);
		}

		public class ControlPanel extends JPanel {
			private JButton queryButton;
			
			public ControlPanel ()
			{
				prepareButtonHandlers();
				setLayout(new GridLayout(20, 1, 1, 1));
				this.add(queryButton);
			}
			
			private void prepareButtonHandlers()
			{
				queryButton = new JButton("Query");
				queryButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							System.out.println("pressed.");
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