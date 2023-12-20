package uni.makarov.parallel_processing.lab2.client.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import uni.makarov.parallel_processing.lab2.client.Client;

//Server could send SERVER broadcast messages
public class ClientView {
    Client backend;

    Scene scene;

    private TextArea chatArea;
    private TextField messageField;

    public ClientView(String name, int port) {
        initFrontend();
        //initBackend(name, port);
    }

    private void initBackend(String name, int port) {
//        backend = new Client(name, port, chatArea);
        backend.input();
        backend.listen();
    }

    private void initFrontend() {
        chatArea = new TextArea();
        chatArea.setEditable(false);

        messageField = new TextField();
        messageField.setPromptText("Введіть ім'я...");
        messageField.setText("");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage());

        // Layout setup
        HBox inputBox = new HBox(messageField, sendButton);
        inputBox.setSpacing(10);
        inputBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(inputBox);

        // Scene setup
        scene = new Scene(root, 400, 300);
    }

    private void sendMessage() {
        //backend.start(messageField.getText());
    }

    public void onMessageReceived() {

    }

    public Scene getScene() {
        return scene;
    }

}
