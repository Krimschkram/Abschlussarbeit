package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHandler extends Thread {

    Socket client;
    BufferedReader br;
    BufferedWriter wr;
    String uname;
    boolean turn = false;
    static String spielfeld = "0000000;0000000;0000000;0000000;0000000;0000000";

    public ClientHandler(Socket s) throws IOException {
        client = s;
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        wr = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = br.readLine();

                // Hier nachricht auswerte

                if (line.startsWith("click")) {
                    int col = Integer.parseInt(line.split(" ")[1]);
                    int place = nextBest(col);
                    spielfeld = aendereSpielfeld(col);
                    write(spielfeld);
                }
                write("123");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String line) {
        try {
            wr.write(line + "\r\n");
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int nextBest(int col) {      // Helper Method für aendereSpielfeld, gibt mir die nächstmögliche reihe für einen Spielzug zurück, kann ich dir privat erklären, ist bissl zu kompliziert zum Schreiben
        ArrayList<String> li = new ArrayList<>(Arrays.asList(spielfeld.split(";")));

        for (int i = li.size()-1; i >= 0; i--) {
            if (li.get(i).charAt(col) == '0') {
                return i;
            }
        }
        return -1;
    }

    public String aendereSpielfeld(int col) {   // wie der Methodenname: nach einem Spielzug muss die Variable Spielfeld geändert werden, diese Methode bezweckt das...
        String[] ar = spielfeld.split(";");
        int row = nextBest(col);
        String newSpielfeld = "";

        for (int i = 0; i < ar.length; i++) {

            if (i != row) {
                newSpielfeld += ar[i];
                newSpielfeld += ";";
                continue;
            }

            for (int j = 0; j < ar[row].length(); j++) {

                if (j != col) {
                    if (ar[i].charAt(j) != '0') {
                        newSpielfeld += "X";
                        continue;
                    }
                    newSpielfeld += "0";
                    continue;
                }
                newSpielfeld += "X";
            }
            if (row != 5) {
                newSpielfeld += ";";
            }
        }
        return newSpielfeld;
    }


}
