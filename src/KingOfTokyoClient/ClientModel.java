package KingOfTokyoClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import KingOfTokyoCommon.GameState;

public class ClientModel {

	private Socket socket;
	private ServerListener serverListener;
	private GameState gamestate=null;
	private ObjectOutputStream out;
	private int port;
	private String ipadress;
	private int clientID;


	public ClientModel() {
	

	}

	public Socket startClientConnection(String ipaddress, int port) throws Exception {
		this.ipadress=ipadress;
		this.port=port;
		socket = new Socket(ipadress, port);
		System.out.println("Client ist gestartet");
		//neuer serverlistener erzeugt und dem Clientmodel zugeordnet
		serverListener = new ServerListener(this, socket);
		serverListener.start();
		
		while(true){
			this.out = new ObjectOutputStream(socket.getOutputStream());
			
			return socket;
		}
		
		
		
	}
	public void sendToServer(GameState gamestate) {
		try {
			System.out.println(gamestate.toString());
			this.out.writeObject(gamestate);
			out.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public GameState getGamestate() {
		return gamestate;
	}

	public void setGamestate(GameState gamestate) {
		
		this.gamestate = gamestate;
		
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
		System.out.println(clientID);
	}


}