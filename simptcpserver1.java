/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Merge Sorted Array
*/
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TCPMergeSortedArraysServer {

  private static final int SERVER_PORT = 8000;

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
      System.out.println("Server started. Listening on port: " + SERVER_PORT);

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected: " + socket);

        // Create input and output streams for object serialization
        ObjectInputStream inputStream = new ObjectInputStream(
          socket.getInputStream()
        );
        ObjectOutputStream outputStream = new ObjectOutputStream(
          socket.getOutputStream()
        );

        // Receive the first sorted array from the client
        int[] array1 = (int[]) inputStream.readObject();
        System.out.println("Received Array 1: " + Arrays.toString(array1));

        // Receive the second sorted array from the client
        int[] array2 = (int[]) inputStream.readObject();
        System.out.println("Received Array 2: " + Arrays.toString(array2));

        // Merge the sorted arrays
        int[] mergedArray = mergeSortedArrays(array1, array2);
        System.out.println("Merged Array: " + Arrays.toString(mergedArray));

        // Send the merged array back to the client
        outputStream.writeObject(mergedArray);

        // Close the streams and socket
        inputStream.close();
        outputStream.close();
        socket.close();
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static int[] mergeSortedArrays(int[] array1, int[] array2) {
    int[] mergedArray = new int[array1.length + array2.length];
    int i = 0, j = 0, k = 0;

    while (i < array1.length && j < array2.length) {
      if (array1[i] < array2[j]) {
        mergedArray[k++] = array1[i++];
      } else {
        mergedArray[k++] = array2[j++];
      }
    }

    while (i < array1.length) {
      mergedArray[k++] = array1[i++];
    }

    while (j < array2.length) {
      mergedArray[k++] = array2[j++];
    }

    return mergedArray;
  }
}
