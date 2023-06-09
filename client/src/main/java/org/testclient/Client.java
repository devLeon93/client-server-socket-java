package org.testclient;

import java.io.*;
import java.net.Socket;
/**
 This line declares the main method that is executed when the class is run. It takes an array of strings as a parameter.
  try {: This line starts a try-catch block to handle any exceptions that may occur in the code.
  This line creates a new Socket object that connects to the server running on the local machine at port 5000.
  This line creates a new DataInputStream object that reads data from the input stream of the socket.
  This line creates a new DataOutputStream object that writes data to the output stream of the socket.
  This line creates a new BufferedReader object that reads data from the standard input stream (keyboard input).
  This line declares two empty strings to hold the client's message and the server's response.
  This line starts a while loop that runs as long as the client's message is not "end".
  This line prints "Client: " to the console, indicating that the client is ready to send a message.
  This line reads a line of text from the standard input stream and assigns it to the message variable.
  This line writes the message string to the output stream of the socket as a UTF-encoded string.
  This line flushes the output stream, ensuring that all data is sent to the server immediately.
  This line reads a UTF-encoded string from the input stream of the socket and assigns it to the response variable.
  This line prints "Server: " followed by the response string to the console, indicating that the server has sent a message.
  This line marks the end of the while loop.
  This line closes the input stream of the socket.
  This line closes the output stream of the socket.
  This line closes the socket.
  This line catches any exceptions that were thrown during the execution of the code.
  This line prints the exception to the console.
 * */



public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 5000);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String message = "", response = "";

            while (!message.equals("end")) {
                System.out.print("Client: ");
                message = br.readLine();
                dos.writeUTF(message);
                dos.flush();

                response = dis.readUTF();
                System.out.println("Server: " + response);
            }

            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}