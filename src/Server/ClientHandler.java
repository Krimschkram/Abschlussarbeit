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
    public String spielfeld = "0000000;0000000;0000000;0000000;0000000;0000000";
    public int index;

    public ClientHandler(Socket s, int index) throws IOException {
        client = s;
        this.index = index;
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        wr = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = br.readLine();

                // Hier nachricht auswerte

                if (line.equals("reset")) {
                    reset();

                    for (int i = 0; i < TCPServer3.clientHandlers.size(); i++) {
                        TCPServer3.clientHandlers.get(i).write("0");
                    }
                    write("enable");

                }

                if (line.startsWith("click")) {
                    int col = Integer.parseInt(line.split(" ")[1]);
                    int place = nextBest(col);
                    spielfeld = aendereSpielfeld(col);
                    TCPServer3.ausgabe(spielfeld);

                    for (int i = 0; i < TCPServer3.clientHandlers.size(); i++) {
                        TCPServer3.clientHandlers.get(i).spielfeld = spielfeld;
                        TCPServer3.clientHandlers.get(i).write(spielfeld);
                    }

                    if (index == 0) {
                        TCPServer3.clientHandlers.get(index).write("disable");
                        TCPServer3.clientHandlers.get(index+1).write("enable");
                    } else {
                        TCPServer3.clientHandlers.get(index).write("disable");
                        TCPServer3.clientHandlers.get(index-1).write("enable");
                    }

                }

                if (line.startsWith("Ende")) {
                    for (int i = 0; i < TCPServer3.clientHandlers.size(); i++) {
                        TCPServer3.clientHandlers.get(i).write(line.split(" ")[1]);
                        TCPServer3.clientHandlers.get(i).write("disable");
                    }
                    /*
                    reset();
                    TCPServer3.ausgabe("Moin");
                    for (int i = 0; i < TCPServer3.clientHandlers.size(); i++) {
                        TCPServer3.clientHandlers.get(i).spielfeld = spielfeld;
                        TCPServer3.clientHandlers.get(i).write(spielfeld);
                    }

                     */
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        spielfeld =  "0000000;0000000;0000000;0000000;0000000;0000000";
        for (int i = 0; i < TCPServer3.clientHandlers.size(); i++) {
            TCPServer3.clientHandlers.get(i).spielfeld = spielfeld;
            TCPServer3.clientHandlers.get(i).write(spielfeld);
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

        if (row == -1) {

        }
        TCPServer3.ausgabe(row+"");
        String newSpielfeld = "";

        for (int i = 0; i < 6; i++) {

            if (i != row) {
                newSpielfeld += ar[i];
                if (i != 5) {
                    newSpielfeld += ";";
                }
                continue;
            }

            for (int j = 0; j < ar[row].length(); j++) {

                if (j != col) {
                    if (ar[i].charAt(j) != '0') {
                        newSpielfeld += ar[i].charAt(j);
                        continue;
                    }
                    newSpielfeld += "0";
                    continue;
                }
                if (index == 0) {
                    newSpielfeld += "R";
                } else {
                    newSpielfeld += "B";
                }

            }
         //   TCPServer3.ausgabe(row+"");
            if (row != 5 ) {
                newSpielfeld += ";";
            }
        }
        return newSpielfeld;
    }
}
