package connection_pool;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;



public class ServerThread implements Runnable {
    private Socket socket;
    private String userName;
    private boolean isAlived;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public ServerThread(Socket socket, String userName){
        this.socket = socket;
        this.userName = userName;
        messagesToSend = new LinkedList<String>();
    }

    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run(){
        System.out.println("Welcome :" + userName);

        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress());

        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);
            // BufferedReader userBr = new BufferedReader(new InputStreamReader(userInStream));
            // Scanner userIn = new Scanner(userInStream);

            while(!socket.isClosed()){
                if(serverInStream.available() > 0){
                    if(serverIn.hasNextLine()){
                        System.out.println(serverIn.nextLine());
                    }
                }
                if(hasMessages){
                    String nextSend = "";
                    synchronized(messagesToSend){
                        nextSend = messagesToSend.pop();
                        if(nextSend.equals("exit")) {
                        	System.out.println(socket.getLocalPort());
                        	int i=0;
                        	Iterator<ClientThread> iterator = ChatServer.clients.iterator();
//                        	for(ClientThread temp : ChatServer.clients) {
//                        		System.out.println(temp.getSocket().getPort());
//                        		i++;
//                        		if(socket.getLocalPort() == temp.getSocket().getPort()) {
////                                	socket.close();
//                        			System.out.println("Yes ok!");
//                        			break;
//                        		}
//                        	}
                        	while(iterator.hasNext()) {
                        		ClientThread temp= iterator.next();
                        		System.out.println(temp.getSocket().getPort());
                        	}
                        	System.out.println("gia tri cua i: "+i);
                        }
                        else
                			System.out.println("No");
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    serverOut.println(userName + " > " + nextSend);
                    serverOut.flush();
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}