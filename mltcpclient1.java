/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Get Frequently Asked Question from a file
To make this program work you need to create a file name "faq.txt"
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FAQClient {

  private static final String HOST = "localhost";
  private static final int PORT = 7860;

  public static void main(String[] args) {
    try (
      Socket socket = new Socket(HOST, PORT);
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      Scanner scanner = new Scanner(System.in)
    ) {
      String question = null;
      String answer = null;

      while (true) {
        System.out.print("Enter your question (or 'bye' to exit): ");
        question = scanner.nextLine();
        out.writeObject(question);

        if (question.equalsIgnoreCase("bye")) {
          break;
        }

        try {
          answer = (String) in.readObject();
          System.out.println("Answer: " + answer);
        } catch (ClassNotFoundException ex) {
          System.out.println("Error: " + ex.getMessage());
        }
      }
    } catch (UnknownHostException ex) {
      System.out.println("Server not found: " + ex.getMessage());
    } catch (IOException ex) {
      System.out.println("I/O error: " + ex.getMessage());
    }
  }
}
