package org.testserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 Starts a try block to handle potential exceptions.
 Creates a new ServerSocket object which listens for connections on port 5000.
 Prints a message to the console indicating that the server is running and listening for connections.
 Line  blocks and waits for a client to connect. When a client connects, it returns a Socket object that represents the connection.
 Creates a new DataInputStream object that is used to read data from the client.
 Creates a new DataOutputStream object that is used to write data to the client.
 Creates a new BufferedReader object that reads input from the console.
 Line declares two strings: message and response, which are used to hold the messages sent between client and server.
 Here starts a while loop that continues until the message string is equal to "End".
 Line reads a message sent by the client using the DataInputStream object.
 Line prints the client's message to the console.
 Line prompts the server to enter a response using the BufferedReader object.
 Line  writes the response to the client using the DataOutputStream object.
 Line flushes the output stream to ensure that all data is sent to the client.
 Line closes the DataInputStream object.
 Line closes the Socket object.
 Line closes the ServerSocket object.
 The catch block is executed if an exception is thrown while running the program. It prints the exception message to the console.

 * */


public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket s = serverSocket.accept();

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String message = "", response = "";

            while (!message.equals("end")) {
                message = dis.readUTF();
                System.out.println("Client: " + message);

                System.out.print("Server: ");
                response = br.readLine();
                dos.writeUTF(response);
                dos.flush();
            }

            dis.close();
            s.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}