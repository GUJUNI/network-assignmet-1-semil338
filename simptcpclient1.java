/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Merge Sorted Array
*/
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class TCPMergeSortedArraysClient {

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

      // Create two sorted arrays
      int[] array1 = { 1, 3, 5, 7 };
      int[] array2 = { 2, 4, 6, 8 };

      // Send the first sorted array to the server
      outputStream.writeObject(array1);

      // Send the second sorted array to the server
      outputStream.writeObject(array2);

      // Receive the merged array from the server
      int[] mergedArray = (int[]) inputStream.readObject();
      System.out.println("Merged Array: " + Arrays.toString(mergedArray));

      // Close the streams and socket
      outputStream.close();
      inputStream.close();
      socket.close();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
