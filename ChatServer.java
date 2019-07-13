package connection_pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int portNumber = 4444;

    private int serverPort;
    static final int max_client = 2;
    static List<ClientThread> clients;
    Socket socket;
    
    public static void main(String[] args){
        ChatServer server = new ChatServer(portNumber);
        server.startServer();
    }

    public ChatServer(int portNumber){
        this.serverPort = portNumber;
    }

    public List<ClientThread> getClients(){
        return clients;
    }

    private void startServer(){
        clients = new ArrayList<ClientThread>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            acceptClients(serverSocket);
        } catch (IOException e){
            System.err.println("Could not listen on port: "+serverPort);
            System.exit(1);
        }
    }

    private void acceptClients(ServerSocket serverSocket){

        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        while(true){
            try{
            	if(clients.size() <= max_client) {
	                socket = serverSocket.accept();
	                System.out.println("accepts : " + socket.getRemoteSocketAddress());
	                ClientThread client = new ClientThread(this, socket);
	                Thread thread = new Thread(client);
	                clients.add(client);
	                thread.start();
            	}
            	else {
            		System.out.println("Overload");
            		socket.close();
            	}
            } catch (IOException ex){
                System.out.println("Accept failed on : "+serverPort);
            }
        }
    }
}   
 