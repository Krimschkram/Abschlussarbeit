package Client;

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

    @Override
    public void run() {
        try {
            while (true) {
                String line = br.readLine();
                /*
                if (line.equals("kjhgfd"))
                    gui.startGame(:)
                System.out.println("br.readLine() = " + line);

                 */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
