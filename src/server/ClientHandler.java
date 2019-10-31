package server;

import java.io.*;
import java.net.Socket;


public class ClientHandler extends Thread {

    Socket socket;
    BufferedReader din;
    PrintWriter dout;
    GameHandler gameHandler;


    public ClientHandler(Socket socket){
        super("ClientHandlerThread");
        this.socket = socket;
        this.gameHandler = new GameHandler(this);
        try{
            din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dout = new PrintWriter(socket.getOutputStream(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        String newGame = gameHandler.startFirstGame();
        publish(newGame);
        while (true){
            String input = listenForResponse();
            String output = gameHandler.handle(input.toLowerCase());
            publish(output);
        }
    }

    private String listenForResponse() {
        try {
            String response;
            while(true){
                if ((response = din.readLine()) != null){
                    System.out.println("Client said: " + response);
                    return response;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        };
        return "ListenForResponse is not working if we end up here...";
    }

    private void publish(String text) {
        dout.println(text);
        dout.flush();
    }

    //Todo: IMplementera ett sätt att stänga en enda client-socket.
    public void closeConnection(){
        try {
            din.close();
            dout.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
