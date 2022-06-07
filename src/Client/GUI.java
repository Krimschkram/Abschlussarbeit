package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class GUI extends Application {

    NetConnectton netCon;

    public int stageSize = 1000;
    public int sceneSize = sceneSize = (stageSize / 4) * 3;
    public int groesseperbutton = sceneSize / 7;
    ArrayList<NeuerButton> topButtons = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("4 Gewinnt");
        stage.setMinWidth(stageSize);
        stage.setMinHeight(stageSize);

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, sceneSize, sceneSize); // breite, höhe

        TextField ip = new TextField();
        TextField port = new TextField();
        Button submit = new Button("submit");

        // Ansteuerbare Buttons mittels ArrayList



        for (int i = 0; i <= 7; i++) {
            topButtons.add(new NeuerButton(0,i,true,true));
            topButtons.get(i).setPrefSize(groesseperbutton-10, groesseperbutton-10);
            //topButtons.get(i).setOnAction();      // Hier kannst du coden was bei dem Drücken passiert
        }

        VBox v = new VBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        Pane spacer = new Pane();               // ist zum Zentrieren der Elemente in h1 da
        Pane spacer2 = new Pane();              // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer,Priority.ALWAYS);  // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer2,Priority.ALWAYS); // ist zum Zentrieren der Elemente in h1 da

        GridPane gridPane = new GridPane();

        h1.getChildren().addAll(spacer, ip, port, submit, spacer2);
        h2.getChildren().addAll(topButtons);
        h2.setSpacing(10);
        v.getChildren().addAll(h1,h2);
        v.setSpacing(10);
        borderPane.setTop(v);
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
