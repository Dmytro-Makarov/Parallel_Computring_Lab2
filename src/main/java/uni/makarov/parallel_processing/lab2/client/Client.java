package uni.makarov.parallel_processing.lab2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;

//    TextArea chat;
//    TextField input;

    public Client(String name, int port) {
        try{
            this.socket = new Socket(InetAddress.getByName(name), port);
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            close();
            e.printStackTrace();
        }
    }

    public void input(){
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            // TODO: get GUI input
            print("Введіть своє ім'я:");
            while (socket.isConnected()){
                String message = scanner.nextLine();
                if (!message.trim().isEmpty()){
                    outputStream.println(message);
//                    print(message);
                }
            }
        }).start();
    }

    public void listen(){
        new Thread(() -> {
            try {
                while (socket.isConnected()){
                    String message = inputStream.readLine();
                    //TODO: push to GUI
                    print(message);
                }
            }catch (IOException e){
                close();
                e.printStackTrace();
            }
        }).start();
    }

    private synchronized void print(String msg){
        SimpleDateFormat format = new SimpleDateFormat("d-MM-yy HH:mm");
        String msgWithTimestamp = "|" + format.format(new Date()) + "| " + msg;
        System.out.println(msgWithTimestamp);
//        chat.appendText(msgWithTimestamp + "\n");
    }

    private void close(){
        try{
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 4000);
        client.input();
        client.listen();
    }
}
