package uni.makarov.parallel_processing.lab2.client.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import uni.makarov.parallel_processing.lab2.client.gui.ClientView;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        final String name = "localhost";
        final int port = 4000;

        ClientView clientView = new ClientView(name, port);
        stage.setTitle("Chat Client");
        stage.setScene(clientView.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}