import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerClientTest {
    private static final int PORT = 5000;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    private static ExecutorService executor;

    @BeforeAll
    public static void setup() throws IOException {
        // Start the server
        serverSocket = new ServerSocket(PORT);

        // Start the client
        clientSocket = new Socket("localhost", PORT);

        // Start a separate thread to handle incoming connections
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                Socket s = serverSocket.accept();
                handleConnection(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void handleConnection(Socket s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        String message = "", response = "";

        while (!message.equals("end")) {
            message = br.readLine();
            response = "Server: " + message.toUpperCase();
            dos.writeBytes(response + "\n");
            dos.flush();
        }

        br.close();
        dos.close();
        s.close();
    }

    @AfterAll
    public static void cleanup() throws IOException {
        // Close all sockets and the server socket
        clientSocket.close();
        serverSocket.close();
        executor.shutdownNow();
    }

    @Test
    @DisplayName("Test client-server communication")
    public void testCommunication() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        String message = "", response = "";

        dos.writeBytes("hello\n");
        dos.flush();
        response = br.readLine();
        assertEquals("Server: HELLO", response);

        dos.writeBytes("world\n");
        dos.flush();
        response = br.readLine();
        assertEquals("Server: WORLD", response);

        dos.writeBytes("end\n");
        dos.flush();
        response = br.readLine();
        assertEquals("Server: END", response);

        br.close();
        dos.close();
    }
}

