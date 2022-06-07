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

    public int stageSizeX = 1000;
    public int stageSizeY = 1000;
    public int sceneSizeX = stageSizeX;
    public int sceneSizeY = (stageSizeY * 3) / 4;
    public int groesseperbutton = sceneSizeY / 7;


    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("4 Gewinnt");
        stage.setMinWidth(stageSizeX);
        stage.setMinHeight(stageSizeY);

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, sceneSizeX, sceneSizeY); // breite, höhe

        TextField ip = new TextField();
        TextField port = new TextField();
        Button submit = new Button("submit");

        // Ansteuerbare Buttons mittels ArrayList

        ArrayList<NeuerButton> topButtons = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            topButtons.add(new NeuerButton(0,i,true,true));
            topButtons.get(i).setPrefSize(groesseperbutton-10, groesseperbutton-10);
            System.out.println("Top Button Number: " + i + ", Row: " + topButtons.get(i).row + ",  Col: " + topButtons.get(i).col);

        }

        VBox v = new VBox();
        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();
        Pane spacer = new Pane();               // ist zum Zentrieren der Elemente in h1 da
        Pane spacer2 = new Pane();              // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer,Priority.ALWAYS);  // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer2,Priority.ALWAYS); // ist zum Zentrieren der Elemente in h1 da

        GridPane gridPane = new GridPane();

        // Spielfeld Buttons mittels Arraylist

        ArrayList<NeuerButton> spielfeld = new ArrayList<>();

        for (int i = 0; i < 42; i++) {
            spielfeld.add(new NeuerButton((i)/7,(i)%7,false,false));
            spielfeld.get(i).setPrefSize(groesseperbutton-10, groesseperbutton-10);
            spielfeld.get(i).setDisable(!(spielfeld.get(i).enabled));
            System.out.println("Button Number: " + i + ", Row: " + spielfeld.get(i).row + ",  Col: " + spielfeld.get(i).col);

            gridPane.add(spielfeld.get(i),spielfeld.get(i).col,spielfeld.get(i).row);
        }

        h1.getChildren().addAll(spacer, ip, port, submit, spacer2);
        h2.getChildren().addAll(topButtons);
        h2.setSpacing((sceneSizeX-sceneSizeY)/5);
        v.getChildren().addAll(h1,h2,h3);
        v.setSpacing(10);
        gridPane.setHgap((sceneSizeX-sceneSizeY)/5);
        gridPane.setVgap(10);
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