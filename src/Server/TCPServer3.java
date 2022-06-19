package Server;

import Client.Spielfeld;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TCPServer3 {

    static List<ClientHandler> clientHandlers = new ArrayList<>();
    static TreeSet<String> users = new TreeSet<>();

    public static void main(String[] args) {
        // users.add("Marius");

        try (ServerSocket server = new ServerSocket(22333)) {
            while (true) {


                System.out.println("ready to connect");

                Socket client = server.accept();




                if (clientHandlers.size() < 2) {

                    clientHandlers.add(new ClientHandler(client, clientHandlers.size()));
                    System.out.println(clientHandlers.get(0).index);
                    clientHandlers.get(clientHandlers.size() - 1).start();
                    clientHandlers.get(clientHandlers.size() - 1).write("disable");

                } else {
                    client.getOutputStream().write("Spiel voll\r\n\r\n".getBytes(StandardCharsets.UTF_8));
                    client.close();
                }

                System.out.println(clientHandlers.size());

                if (clientHandlers.size() == 2) {
                    System.out.println("ENABLEAFASFASFCA");
                    clientHandlers.get(0).write("enable");
                }


            }
        } catch (IOException io) {
            io.printStackTrace();
        }


    }



    public static void ausgabe(String s) {
        System.out.println(s);
    }

    /*
    public static void close(String user) throws IOException {
        for (int i = 0; i < clientHandlers.size(); i++) {

            if (clientHandlers.get(i).uname.equals(user)) {
                clientHandlers.get(i).br.close();
                users.remove(user);
                clientHandlers.remove(i);
            }

        }
    }
    */

}
