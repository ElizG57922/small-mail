package network;

public class ClientGUI {
	private ConnectGUI connect;
	private ConnectedGUI connected;
	private RegisterGUI register;
	private LoginGUI login;
	private LoggedinGUI loggedin;
	private ChangePassGUI changepass;
	private PassRecoverGUI recoverpass;
	
	public static void main(String[] args) {

		Client client = new Client();
		
		new ClientGUI(client);
	}
	
	public ClientGUI(Client client) {
		connect = new ConnectGUI(client, this);
		connected = new ConnectedGUI(client, this);
		register = new RegisterGUI(client, this);
		login = new LoginGUI(client, this);
		loggedin = new LoggedinGUI(client, this);
		changepass = new ChangePassGUI(client, this);
		recoverpass = new PassRecoverGUI(client, this);
	}

	public void showDisconnectedGUI() {
		hideAll();
		connect.setVisible(true);
	}
	public void showConnectedGUI() {
		hideAll();
		connected.setVisible(true);
	}
	public void showRegisterGUI() {
		hideAll();
		register.setVisible(true);
	}
	public void showLoginGUI() {
		hideAll();
		login.setVisible(true);
	}
	public void showPassRecoverGUI() {
		hideAll();
		recoverpass.setVisible(true);
	}
	public void showLoggedinGUI(String username) {
		hideAll();
		loggedin.setUsername(username);
		loggedin.setVisible(true);
	}
	public void showLoggedinGUI() {
		hideAll();
		loggedin.setVisible(true);
	}
	public void showPassChangeGUI(String username) {
		hideAll();
		changepass.setUsername(username);
		changepass.setVisible(true);
	}
	
	private void hideAll() {
		connect.setVisible(false);
		connected.setVisible(false);
		register.setVisible(false);
		login.setVisible(false);
		loggedin.setVisible(false);
		changepass.setVisible(false);
		recoverpass.setVisible(false);
	}
	
}