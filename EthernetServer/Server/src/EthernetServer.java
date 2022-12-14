import java.io.*;
import java.net.*;

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
//        System.out.println("\nServer started at port " + port);
        System.out.println("\nLED Display listening on port " + port);

        // while statement cannot complete without throwing an exception java
        // This error seems to be a false positive since Java thinks an infinite loop is a mistake.
        while(true) {

//            System.out.println("Waiting for a client ...");
            System.out.println("Waiting for updates from Control Unit...");

            try {

                cSocket = sSocket.accept();
//                System.out.println("Client accepted");
                System.out.println("Control Unit connected");
                out = new PrintWriter(cSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                String data = null;

                // Read string sent by client
                data = in.readLine();

                System.out.println("Update Received: ");
                System.out.println("==================");
                System.out.println("|    " + data + "  --->    |");
                System.out.println("==================\n");

                // Sent back the string received (As a confirmation)
                out.println("Received: " + data);

            } catch (IOException e) {
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
        server.start(5050);
    }
}