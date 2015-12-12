package KingOfTokyoModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;

public class ServerListener extends Thread {

	private Socket clientsocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private GameState gamestate = null;

	public ServerListener(ClientModel clientmodel, Socket clientsocket) throws IOException {
		this.clientsocket = clientsocket;
		this.out = new ObjectOutputStream(clientsocket.getOutputStream());
		this.in = new ObjectInputStream(clientsocket.getInputStream());
	}

	public void run() {
		listen();
	}

	public void listen() {

		try {
			// sobald object im inputstream -> read
			while (in.readObject() != null) {
				// Thread l�uft die ganze Zeit und liest ob ein Object geschickt
				// wurde
				gamestate = (GameState) in.readObject();
				//von server erhaltene gamestate wird hier gesetzt
				setGamestate(gamestate);
				System.out.println("Recieved Game State from server: " + gamestate.toString());
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void close() {
		try {
			this.interrupt();
			clientsocket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendToServer(GameState gamestate) {
		try {

			System.out.println("Writing object to Server..");
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

}
