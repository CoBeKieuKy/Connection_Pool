package connection_pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int portNumber = 2019;
    private int serverPort;
    static List<ClientThread> clients ; // or "protected static List<ClientThread> clients;"
    static final int max_client = 2;

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
	                Socket socket = serverSocket.accept();
	                System.out.println("accepts : " + socket.getRemoteSocketAddress());
	                ClientThread client = new ClientThread(this, socket);
	                Thread thread = new Thread(client);
	                thread.start();
	                clients.add(client);
            	}
            	System.out.println("Size hien tai la: ");
                System.out.println(clients.size());

            } catch (IOException ex){
                System.out.println("Accept failed on : "+serverPort);
            }
        }
    }
    
   public void remove(Socket socket) {
	   for(ClientThread temp : clients) {
		   if(temp.getSocket(). == socket.getRemoteSocketAddress()) {
			   clients.remove(temp);
		   }
	   }
   }
}