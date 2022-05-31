package Client;

import javafx.scene.control.Button;

public class NeuerButton extends Button {

    int col;
    int row;
    boolean enabled;
    boolean abc;    // Damit man die oberen Buttons (die eine Aktion ausführen) mit den Unteren unterscheiden kann
    int farbe;      // Farbe des Spielstein auf dem Button (1 = Farbe x / 2 = Farbe y / 0 = leer, noch keine Farbe)



}
