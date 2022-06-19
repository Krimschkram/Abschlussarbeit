package Client;

import Server.TCPServer3;

import java.io.*;
import java.net.Socket;

public class NetConnectton extends Thread {
    private final GUI gui;
    BufferedReader br;
    BufferedWriter wr;
    Socket socket;


    public NetConnectton(GUI gui, String IP, int Port) throws IOException {
        this.gui = gui;
        socket = new Socket(IP, Port);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        start();
    }

    // test3

    @Override
    public void run() {
        try {
            while (true) {
                String line = br.readLine();

                if (line.length() == 1) {
                    gui.allesEinfaerben(line);
                }
                if (line.equals("disable")) {
                    gui.disableTopButtons();

                }
                if (line.equals("enable")) {
                    gui.enableTopButtons();


                }
                if (!line.equals("enable") && !line.equals("disable") & line.length() != 1) {
                    gui.spielfeldAuslesen(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonPressed(int index) throws IOException {
        wr.write(index);

    }

    public void write(String line) {
        try {
            wr.write(line + "\r\n");
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
