package uni.makarov.parallel_processing.lab2.server;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable
{
    private String name;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private ThreadManager clientManager;
    private Socket socket;
//    boolean isloggedin = true;

    // constructor
    public ServerThread(Socket socket, ThreadManager clientManager) {
        try {
            this.socket = socket;
            this.clientManager = clientManager;
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.name = inputStream.readLine();
            this.clientManager.add(this);
        } catch (IOException e) {
            this.close();
            e.printStackTrace();
        }
    }

    String getName() {
        return this.name;
    }


    @Override
    public void run() {
        try {
            while (socket.isConnected()){
                String msg = inputStream.readLine();
                this.clientManager.receiveMessage(name, msg);
            }
        } catch (IOException e){
            this.close();
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(String msg){
        this.outputStream.println(msg);
    }


    public synchronized void close(){
        try {
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}