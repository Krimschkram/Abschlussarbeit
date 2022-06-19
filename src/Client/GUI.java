package Client;

import Server.TCPServer3;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class GUI extends Application {

    NetConnectton netCon;

    public int stageSizeX = 1200;
    public static int stageSizeY = 800;
    public int sceneSizeX = stageSizeX;
    public static int sceneSizeY = (stageSizeY * 5) / 4;
    public static int groesseperbutton = (sceneSizeY - 180) / 7;
    public static ArrayList<NeuerButton> spielfeld = new ArrayList<>();
    public static ArrayList<NeuerButton> topButtons = new ArrayList<>();
    public static BorderPane borderPane = new BorderPane();
    public static HBox h2 = new HBox();
    public static GridPane gridPane = new GridPane();
    public static String myUsername = "User1";
    public static String enemyUsername = "User2";
    public static Button neuesSpiel = new Button("Neues Spiel");

    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Gib deinen Username ein");
        stage.setMinWidth(350);
        stage.setMinHeight(100);

        TextField username = new TextField();
        Button submitUName = new Button("submit");
        HBox hUser = new HBox();
        Scene scene2 = new Scene(hUser);


        hUser.getChildren().addAll(username, submitUName);
        hUser.setLayoutX(70);
        hUser.setLayoutY(15);
        stage.setScene(scene2);
        stage.show();

        Scene scene = new Scene(borderPane, sceneSizeX, sceneSizeY); // breite, höhe

        TextField ip = new TextField();
        TextField port = new TextField();
        Button submit = new Button("submit");
        VBox vbottom = new VBox();

        ip.setText("127.0.0.1");
        port.setText("22333");

        // Ansteuerbare Buttons mittels ArrayList

        for (int i = 0; i < 7; i++) {
            topButtons.add(new NeuerButton(0, i, true, true));
            topButtons.get(i).setPrefSize(groesseperbutton - 15, groesseperbutton - 15);
            System.out.println("Top Button Number: " + i + ", Row: " + topButtons.get(i).row + ",  Col: " + topButtons.get(i).col);
        }

        VBox v = new VBox();
        HBox h1 = new HBox();
        HBox h3 = new HBox();
        Pane spacer = new Pane();               // ist zum Zentrieren der Elemente in h1 da
        Pane spacer2 = new Pane();              // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer, Priority.ALWAYS);  // ist zum Zentrieren der Elemente in h1 da
        HBox.setHgrow(spacer2, Priority.ALWAYS); // ist zum Zentrieren der Elemente in h1 da

        // Spielfeld Buttons mittels Arraylist


        for (int i = 0; i < 42; i++) {
            spielfeld.add(new NeuerButton((i) / 7, (i) % 7, false, false));
            spielfeld.get(i).setPrefSize(groesseperbutton - 15, groesseperbutton - 15);
            spielfeld.get(i).setDisable(!(spielfeld.get(i).enabled));
            System.out.println("Button Number: " + i + ", Row: " + spielfeld.get(i).row + ",  Col: " + spielfeld.get(i).col);

            gridPane.add(spielfeld.get(i), spielfeld.get(i).col, spielfeld.get(i).row);
        }

        vbottom.getChildren().add(neuesSpiel);
        vbottom.setAlignment(Pos.CENTER);
        vbottom.setSpacing(10);
        h1.getChildren().addAll(spacer, ip, port, submit, spacer2);
        h2.getChildren().addAll(topButtons);
        h2.setAlignment(Pos.CENTER);
        h2.setSpacing((sceneSizeX - sceneSizeY) / 5);
        v.getChildren().addAll(h1, h2, h3);
        v.setSpacing(10);
        gridPane.setHgap((sceneSizeX - sceneSizeY) / 5);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setTop(v);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(vbottom);

        submitUName.setOnAction(f -> {
            myUsername = username.getText();
            stage.setTitle("4 Gewinnt");
            stage.setMinWidth(stageSizeX);
            stage.setMinHeight(stageSizeY);
            stage.setScene(scene);
            stage.show();
        });

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

        SpielfeldString = SpielfeldString.replaceAll(";", "");

        final String finalString = SpielfeldString;
        Platform.runLater(() -> {
            for (int i = 0; i < finalString.length(); i++) {

                spielfeld.get(i).farbe = finalString.charAt(i) + "";
                einfaerben(spielfeld.get(i));
                neuesGridPane.add(spielfeld.get(i), spielfeld.get(i).col, spielfeld.get(i).row);
            }

            gridPane = neuesGridPane;
            gridPane.setHgap((sceneSizeX - sceneSizeY) / 5);
            gridPane.setVgap(10);
            borderPane.setCenter(gridPane);
        });

        Character gewinner = hatGewonnen(SpielfeldString);
        if (gewinner != '0') {
            netCon.write("Ende "+gewinner);
            System.out.println("Gewinner:"+gewinner);

        }
    }

    public static void einfaerben(NeuerButton button) {
        if (Objects.equals(button.farbe, "R")) {
            button.setStyle("-fx-background-color : red");
        }

        if (Objects.equals(button.farbe, "B")) {
            button.setStyle("-fx-background-color : blue");
        }

        if (Objects.equals(button.farbe, "0")) {
            button.setStyle("");
        }
    }

    public void allesEinfaerben(String color) {

        for (int i = 0; i < spielfeld.size(); i++) {

            if (color.equals("B")) {
                spielfeld.get(i).setStyle("-fx-background-color : blue");
            }
            if (color.equals("R")) {
                spielfeld.get(i).setStyle("-fx-background-color : red");
            }
        }
    }

    public static void disableTopButtons() {
        for (int i = 0; i < topButtons.size(); i++) {
            topButtons.get(i).setDisable(true);
            topButtons.get(i).setPrefSize(groesseperbutton - 15, groesseperbutton - 15);
        }
    }

    public static void enableTopButtons() {
        for (int i = 0; i < topButtons.size(); i++) {
            topButtons.get(i).setDisable(false);
            topButtons.get(i).setPrefSize(groesseperbutton - 15, groesseperbutton - 15);
        }
    }

    public static void disableBottom() {
        neuesSpiel.setDisable(true);
    }

    public static void enableBottom(){
        neuesSpiel.setDisable(false);
    }

    public static void roterSpieler(String ersterSpieler) {

        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        Label spielername = new Label();
        String path = new File("res/Roter_Spielstein.jpg").getAbsolutePath();
        path = "file:///" + path.replace('\\', '/');
        Image image = new Image(path, 100, 100, true, true);
        ImageView roterStein = new ImageView(image);
        roterStein.prefHeight(100);
        roterStein.prefWidth(100);

        spielername.setText(ersterSpieler);
        spielername.setStyle("-fx-font-family : Verdana; -fx-font-size: 14");
        hbox1.getChildren().add(spielername);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.getChildren().add(roterStein);
        hbox2.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox1, hbox2);
        vbox.setAlignment(Pos.CENTER);
        borderPane.setLeft(vbox);
    }

    public static void blauerSpieler(String zweiterSpieler) {

        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        Label spielername = new Label();
        String path = new File("res/Blauer_Spielstein.jpg").getAbsolutePath();
        path = "file:///" + path.replace('\\', '/');
        Image image = new Image(path, 100, 100, true, true);
        ImageView blauerStein = new ImageView(image);
        blauerStein.prefHeight(100);
        blauerStein.prefWidth(100);

        spielername.setText(zweiterSpieler);
        spielername.setStyle("-fx-font-family : Verdana; -fx-font-size: 14");
        hbox1.getChildren().add(spielername);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.getChildren().add(blauerStein);
        hbox2.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox1, hbox2);
        vbox.setAlignment(Pos.CENTER);
        borderPane.setRight(vbox);
    }

    public static char hatGewonnen(String Spielfeld) {

        // char = 0, niemand hat gewonnen | char = R, rot hat gewonnen | char = B, blau hat gewonnen

        int y = 0;
        int x = 0;

        Spielfeld = Spielfeld.replaceAll(";", "");
        char[][] temp = new char[6][7];

        for (int i = 0; i < Spielfeld.length(); i++) {
            if (x == 7) {
                y++;
                x = 0;
            }
            temp[y][x] = Spielfeld.charAt(i);
            x++;
        }
        return PatternScanner.scan(temp);
    }
}