package uni.makarov.parallel_processing.lab2.server;

import java.util.*;

public class ThreadManager {
    private final Set<ServerThread> clients;
    private final Logger logger;

    public ThreadManager() {
        this.clients = new HashSet<>();
        this.logger = new Logger(System.getProperty("user.dir") + "\\" + "log.txt");
    }

    public void add(ServerThread client){
        this.clients.add(client);
        String name = client.getName();
        sendPrivateMessage("SERVER", name, name + " приєднався до чату.");
    }

    public void receiveMessage(String receiverUsername, String msg){
        String[] parts = msg.split(" ");
        String msgPart;

        if (msg.contains("/msg")){
            String usernameTo = parts[1];
            msgPart = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));

            if(!msgPart.isEmpty())
                sendPrivateMessage(receiverUsername, usernameTo, msgPart);
        }
        else{
//            msgPart = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length));

            sendGlobalMessage(receiverUsername, msg);
        }
    }

    private void sendGlobalMessage(String from, String msg){
        for (ServerThread client: clients){
            String to = client.getName();
//            if (!to.equals(from)){
//                client.sendMessage(getFormattedMsg(from, msg));
//                logger.log(from, to, msg);
//            }
            //Will send a message to itself
            client.sendMessage(getFormattedMsg(from, msg));
            logger.log(from, to, msg);
        }
    }

    private void sendPrivateMessage(String fromUsername, String toUsername, String msg){
        Optional<ServerThread> to = find(toUsername);

        if (to.isEmpty()){
            sendPrivateMessage("SERVER", fromUsername, "Користувача з таким іменем не існує");
            return;
        }

        to.get().sendMessage(getFormattedMsg(fromUsername, msg));
        logger.log(fromUsername, toUsername, msg);
    }

    private Optional<ServerThread> find(String username){
        return this.clients.stream()
                .filter(e -> e.getName().equals(username))
                .findFirst();
    }

    private String getFormattedMsg(String fromUsername, String msg){
//        SimpleDateFormat format = new SimpleDateFormat("d-MM-yy hh:mm");
//        return "|" + format.format(new Date()) + "| " + fromUsername + ": " + msg;
        return fromUsername + ": " + msg;
    }

    public void close(){
        logger.close();
        for (ServerThread clientHandler: clients){
            clientHandler.close();
        }
    }
}