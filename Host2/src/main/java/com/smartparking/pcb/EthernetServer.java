package com.smartparking.pcb;
// Todo: this program acts as if it were a LED display receiving data from the Java program.
//      it receives the new number of available spaces to display.
//      It needs inbound port 5050 to be opened in the Firewall in the computer running this program,
//      whenever the Java program needs to connect to this computer remotely.

import com.smartparking.host.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EthernetServer {
    private ServerSocket sSocket;
    private Socket cSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started on port: " + port);

        // while statement cannot complete without throwing an exception java
        // This error seems to be a false positive since Java thinks an infinite loop is a mistake.
        while (true) {

            System.out.println("Waiting for a client ...");

            try {

                cSocket = sSocket.accept();
                System.out.println("Client accepted");
                out = new PrintWriter(cSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                String data = null;

                // Read string sent by client
                data = in.readLine();

                System.out.println("Received: " + data);

                // Sent back the string received (As a confirmation)
                out.println("Received " + data);

                System.out.println("Sent: " + data + "\n");

            } catch (IOException e) {
//                throw new RuntimeException(e);
                System.out.println("An error has occurred.");
            }
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            cSocket.close();
            sSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        EthernetServer server = new EthernetServer();
        server.start(Constants.ETHERNET_PORT);
    }
}
