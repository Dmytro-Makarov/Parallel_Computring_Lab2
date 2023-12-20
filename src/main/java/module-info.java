module uni.makarov.parallel_processing.lab2.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens uni.makarov.parallel_processing.lab2.client to javafx.fxml;
    opens uni.makarov.parallel_processing.lab2.server to javafx.fxml;

    exports uni.makarov.parallel_processing.lab2.client;
    exports uni.makarov.parallel_processing.lab2.server;
    exports uni.makarov.parallel_processing.lab2.client.gui;
    opens uni.makarov.parallel_processing.lab2.client.gui to javafx.fxml;
    exports uni.makarov.parallel_processing.lab2.server.gui;
    opens uni.makarov.parallel_processing.lab2.server.gui to javafx.fxml;

}