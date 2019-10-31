package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args){
        new Server();
    }

    public Server(){
        try {
            while (true){
                ServerSocket ss = new ServerSocket(44444);
                Socket socket = ss.accept();
                new ClientHandler(socket).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Main server shut down");
    }
}