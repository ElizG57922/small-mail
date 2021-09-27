package network;

public class Client {
	
	/*

	 For use on a single computer with loopback, use 127.0.0.1 or localhost
	 
	 Windows From ipconfig:
	 
	 Wireless LAN adapter Wireless Network Connection:
     Connection-specific DNS Suffix  . : clunet.edu
     Link-local IPv6 Address . . . . . : fe80::1083:3e22:f5a1:a3ec%11
     IPv4 Address. . . . . . . . . . . : 199.107.222.115 <======= This address works
     Subnet Mask . . . . . . . . . . . : 255.255.240.0
     Default Gateway . . . . . . . . . : 199.107.210.2

     MacOS From System preferences
     Network category, read the IP address directly

	 */

	/**
	 * provides a peer-to-peer connection to the server
	 */
	private NetworkAccess networkaccess;

	/**
	 * Creates a peer-to-peer connection to the server
	 *
	 * @param ip: the IP address of the server
	 * @param port: the port on which the server is listening
	 */
	public Client (String ip, int port)
	{
		networkaccess = new NetworkAccess(ip, port);
	}
	public Client () {

	}
	public void connect(String ip, int port) {
		networkaccess = new NetworkAccess(ip, port);
	}

	public String sendString(String commandString) {
		// -- send a command String to the server and wait for the response
		String replyString = networkaccess.sendString(commandString, true);
		return replyString;
	}

	/**
	 * Disconnects the client from the server
	 */
	public void disconnect ()
	{
		String text = "disconnect";
		networkaccess.sendString(text,  false);
		networkaccess.close();
	}
}