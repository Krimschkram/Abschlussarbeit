package Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    Socket client;
    BufferedReader br;
    BufferedWriter wr;
    String uname;
    boolean turn = false;

    public ClientHandler(Socket s) throws IOException {
        client = s;
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        wr = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    @Override
    public void run() {
    }

}
