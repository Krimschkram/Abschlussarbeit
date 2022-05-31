package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class GUI extends Application {
    NetConnectton netCon;

    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);

        TextField ip = new TextField();
        TextField port = new TextField();
        Button submit = new Button("submit");
        HBox h = new HBox();
        GridPane gridPane = new GridPane();
        h.getChildren().addAll(ip, port, submit);
        borderPane.setTop(h);
        borderPane.setCenter(gridPane);
        stage.setScene(scene);
        stage.show();

        submit.setOnAction(f -> {
            try {
                netCon = new NetConnectton(this, ip.getText(), Integer.parseInt(port.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }
}
