package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	/*
	 The client will need the server's IP address. Here is how you find it for your system.
	 
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
	 * the port number used for client communication
	 */
	private static final int PORT = 8000;

	/**
	 * used for shutting down the server thread
	 */
	private boolean running = true;
	
	/**
	 * unique ID for each client connection
	 */
	private int nextId = 0;
	
	/**
	 * the socket that waits for client connections
	 */
	private ServerSocket serversocket;
			
	/**
	 * list of active client threads by ID number
	 * Vector is a "thread safe" ArrayList
	 */
	private Vector<ClientHandler> clientconnections;
	
	/**
	 * constructor creates the list of clients and
	 * starts the server listening on the port
	 */
	public Server ()
	{
		// -- construct the list of active client threads
		clientconnections = new Vector<ClientHandler>();

		// -- listen for incoming connection requests
		listen();
	}

	/**
	 * listen for incoming client connections
	 */
	private void listen ()
	{
		try {
			// -- open the server socket
			serversocket = new ServerSocket(PORT);
			
			// -- server runs until we manually shut it down
			while (running) {
				// -- block until a client comes along (listen for the phone to ring)
				Socket socket = serversocket.accept();

				// -- connection accepted, create a peer-to-peer socket
				//    between the server (thread) and client (route the call to the requested extension)
				peerconnection(socket);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * creates a direct (peer-to-peer) connection between the client and the server
	 * via a thread
	 * 
	 * @param socket: socket from the ServerSocket.listen() call
	 */
	public void peerconnection (Socket socket)
	{		
		// -- when a client arrives, create a thread for their communication
		ClientHandler connection = new ClientHandler(nextId, socket, this);

		// -- add the thread to the active client threads list
		clientconnections.add(connection);
		
		// -- start the thread
		connection.start();

		// -- place some text in the area to let the server operator know
		//    what is going on
		System.out.println("SERVER: connection received for id " + nextId + "\n");
		++nextId;
	}

	/**
	 * called by a ServerThread when a client is terminated to remove
	 * the connection from the list
	 * 
	 * @param id: the unique ID assigned by the server
	 */
	public void removeID(int id)
	{
		// -- find the object belonging to the client thread being terminated
		for (int i = 0; i < clientconnections.size(); ++i) {
			ClientHandler cc = clientconnections.get(i);
			long x = cc.getId();
			if (x == id) {
				// -- remove it from the active threads list
				//    the thread will terminate itself
				clientconnections.remove(i);
				break;
			}
		}
	}

	/**
	 * @param args: command line arguments (unused)
	 */
	public static void main (String args[])
	{
		new ServerGUI();
		new Server();
	}
}