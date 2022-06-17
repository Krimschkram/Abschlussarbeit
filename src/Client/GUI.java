package Client;

import Server.TCPServer3;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class GUI extends Application {

    NetConnectton netCon;

    public int stageSizeX = 1000;
    public static int stageSizeY = 1000;
    public int sceneSizeX = stageSizeX;
    public static int sceneSizeY = (stageSizeY * 3) / 4;
    public static int groesseperbutton = sceneSizeY / 7;
    public static ArrayList<NeuerButton> spielfeld = new ArrayList<>();
    public static ArrayList<NeuerButton> topButtons = new ArrayList<>();
    public BorderPane borderPane = new BorderPane();
    public static HBox h2 = new HBox();
    public static GridPane gridPane = new GridPane();
    boolean[] buttonPressed = new boolean[7];

    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("4 Gewinnt");
        stage.setMinWidth(stageSizeX);
        stage.setMinHeight(stageSizeY);

        Scene scene = new Scene(borderPane, sceneSizeX, sceneSizeY); // breite, höhe

        TextField ip = new TextField();
        TextField port = new TextField();
        Button submit = new Button("submit");

        // Ansteuerbare Buttons mittels ArrayList

        for (int i = 0; i < 7; i++) {
            topButtons.add(new NeuerButton(0,i,true,true));
            topButtons.get(i).setPrefSize(groesseperbutton-10, groesseperbutton-10);
            System.out.println("Top Button Number: " + i + ", Row: " + topButtons.get(i).row + ",  Col: " + topButtons.get(i).col);
        }

        VBox v = new VBox();
        HBox h1 = new HBox();
        HBox h3 = new HBox();
        Pane spacer = new Pane();               // ist zum Zentrieren der Elemente in h1 da
        Pane spacer2 = new Pane();              // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer,Priority.ALWAYS);  // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer2,Priority.ALWAYS); // ist zum Zentrieren der Elemente in h1 da



        // Spielfeld Buttons mittels Arraylist


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

        topButtons.get(0).setOnAction(f -> {
            netCon.write("click 0");
        });
        topButtons.get(1).setOnAction(f -> {
            netCon.write("click 1");
        });
        topButtons.get(2).setOnAction(f -> {
            netCon.write("click 2");
        });
        topButtons.get(3).setOnAction(f -> {
            netCon.write("click 3");
        });
        topButtons.get(4).setOnAction(f -> {
            netCon.write("click 4");
        });
        topButtons.get(5).setOnAction(f -> {
            netCon.write("click 5");
        });
        topButtons.get(6).setOnAction(f -> {
            netCon.write("click 6");
        });
    }
    public void Ausgabe(String s) {
        System.out.println(s);
    }

    public void spielfeldAuslesen(String SpielfeldString) {
        System.out.println(SpielfeldString);
        GridPane neuesGridPane = new GridPane();

        SpielfeldString = SpielfeldString.replaceAll(";","");

        final String finalString = SpielfeldString;
        Platform.runLater(() -> {
        for (int i = 0; i < finalString.length(); i++) {

            spielfeld.get(i).farbe = finalString.charAt(i) + "";
            einfaerben(spielfeld.get(i));
            neuesGridPane.add(spielfeld.get(i),spielfeld.get(i).col,spielfeld.get(i).row);
        }

            gridPane = neuesGridPane;
            gridPane.setHgap((sceneSizeX-sceneSizeY)/5);
            gridPane.setVgap(10);
            borderPane.setCenter(gridPane);
        });


        if (hatGewonnen(SpielfeldString) != '0') {

            netCon.write("Ende");
        }
    }

    public static void einfaerben(NeuerButton button){
        if (Objects.equals(button.farbe, "R")){
            button.setStyle("-fx-background-color : red");
        }

        if (Objects.equals(button.farbe, "B")){
            button.setStyle("-fx-background-color : blue");
        }

        if (Objects.equals(button.farbe, "0")){
            button.setStyle("");
        }
    }

    public static void disableTopButtons(){
        for (int i = 0; i < topButtons.size(); i++) {
            topButtons.get(i).setDisable(true);
            topButtons.get(i).setPrefSize(groesseperbutton-10, groesseperbutton-10);
        }
    }

    public static void enableTopButtons(){
        for (int i = 0; i < topButtons.size(); i++) {
            topButtons.get(i).setDisable(false);
            topButtons.get(i).setPrefSize(groesseperbutton-10, groesseperbutton-10);
        }
    }

    public static char hatGewonnen(String Spielfeld){

        // char = 0, niemand hat gewonnen | char = R, rot hat gewonnen | char = B, blau hat gewonnen

        int y = 0;
        int x = 0;

        Spielfeld = Spielfeld.replaceAll(";","");
        char [][] temp = new char[6][7];

        for (int i = 0; i < Spielfeld.length(); i++) {
            if (x == 7){
                y++;
                x=0;
            }
            temp[y][x] = Spielfeld.charAt(i);
            x++;
        }
        return PatternScanner.scan(temp);
    }
}