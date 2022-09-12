// Java program: socket client
// The Host JAVA program is the client, each LED display is a server.
// Each server should have the socket connection open, listening/waiting for
// the JAVA host program (the client), to connect.

import java.net.*;
import java.io.*;


public class EthernetClient {

    private static Socket socket = null;
    private static PrintWriter out;
    private static BufferedReader in;
    private final String ipAddress;

    // Constructor
    public EthernetClient(String address) {
        ipAddress = address;
    }

    public void connect() throws IOException {
        int port = Constants.ETHERNET_PORT;

//        try {
        // Establish a connection
        socket = new Socket(ipAddress, port);
        System.out.println("Connected to: " + ipAddress + ":" + port);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//        } catch (IOException e) {
//            //throw new RuntimeException(e);
//            System.out.println("Connection to display timed out");
//        }
    }

    public static String sendData(int freeSpaces) {

        // Send output to the socket
        out.println(freeSpaces);

        String response = null;

        // Read response from the Display Unit
        try {
            response = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public static void close() {
        try {
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

