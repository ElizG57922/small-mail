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

		private int WIDTH = 512;
		private int HEIGHT = 512;

		private ControlPanel controlPanel;
		
		public ServerGUI ()
		{
			setTitle("Server GUI");

			// -- size of the frame: width, height
			setSize(WIDTH, HEIGHT);

			// -- center the frame on the screen
			setLocationRelativeTo(null);

			// -- shut down the entire application when the frame is closed
			//    if you don't include this the application will continue to

			//    run in the background and you'll have to kill it by pressing
			//    the red square in eclipse
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// -- set the layout manager and add items
			//    5, 5 is the border around the edges of the areas
			setLayout(new BorderLayout(5, 5));

			controlPanel = new ControlPanel();
			this.add(controlPanel, BorderLayout.CENTER);

			// -- show the frame on the screen
			setVisible(true);
		}

		// -- Inner class for control panel
		public class ControlPanel extends JPanel {

			private JButton queryButton;
			
			public ControlPanel ()
			{
				// -- set up buttons
				prepareButtonHandlers();

				// -- add buttons and text field to panel layout manager
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