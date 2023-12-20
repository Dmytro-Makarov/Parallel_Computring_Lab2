package uni.makarov.parallel_processing.lab2.server;

import java.io.*;
import java.net.*;

public class Server
{
    private final ServerSocket serverSocket;
    private final ThreadManager manager;

    public Server(int socketPort) throws IOException {
        this.serverSocket = new ServerSocket(socketPort);
        this.manager = new ThreadManager();
    }

    public void start(){
        new Thread(() -> {
            try {
                while (!serverSocket.isClosed()){
                    Socket socket = serverSocket.accept();
                    new Thread(new ServerThread(socket, manager)).start();
                }
            } catch (IOException e) {
                close();
                e.printStackTrace();
            }
        }).start();
    }

    private void close(){
        try {
            manager.close();
            serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(4000);
        server.start();
    }
}

