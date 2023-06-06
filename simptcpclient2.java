/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Solve NQueen Problem
*/
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TCPNQueensClient {

  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 8000;

  public static void main(String[] args) {
    try {
      // Create a socket and connect to the server
      Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
      System.out.println("Connected to server: " + socket);

      // Create input and output streams for object serialization
      ObjectOutputStream outputStream = new ObjectOutputStream(
        socket.getOutputStream()
      );
      ObjectInputStream inputStream = new ObjectInputStream(
        socket.getInputStream()
      );

      // Set the board size
      int boardSize = 8;

      // Send the board size to the server
      outputStream.writeInt(boardSize);
      outputStream.flush();

      // Receive the solutions from the server
      List<List<String>> solutions = (List<List<String>>) inputStream.readObject();
      System.out.println("Received " + solutions.size() + " solution(s)");

      // Display the solutions
      for (int i = 0; i < solutions.size(); i++) {
        System.out.println("Solution " + (i + 1) + ":");
        displaySolution(solutions.get(i));
        System.out.println();
      }

      // Close the streams and socket
      outputStream.close();
      inputStream.close();
      socket.close();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void displaySolution(List<String> solution) {
    for (String row : solution) {
      System.out.println(row);
    }
  }
}
