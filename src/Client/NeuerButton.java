package Client;

import javafx.scene.control.Button;

public class NeuerButton extends Button {

    int row;
    int col;
    boolean enabled;
    boolean drückbar;    // Damit man die oberen Buttons (die eine Aktion ausführen) mit den Unteren unterscheiden kann
    int farbe;      // Farbe des Spielstein auf dem Button (1 = Farbe rot / 2 = Farbe blau / 0 = leer, noch keine Farbe)


    public NeuerButton(int row, int col, boolean enabled, boolean drückbar) {
        this.row = row;
        this.col = col;
        this.enabled = enabled;
        this.drückbar = drückbar;
    }
}
