/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Find Pivot Point in Array
*/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPPivotArrayServer {

  private static final int SERVER_PORT = 5000;
  private static final int BUFFER_SIZE = 1024;

  public static void main(String[] args) {
    try {
      DatagramSocket socket = new DatagramSocket(SERVER_PORT);
      byte[] buffer = new byte[BUFFER_SIZE];

      while (true) {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // Receive packet from the client
        socket.receive(packet);

        // Extract array data from the packet
        String arrayData = new String(packet.getData(), 0, packet.getLength());

        // Perform pivot operation on the array
        String result = pivotArray(arrayData);

        // Send the result back to the client
        DatagramPacket responsePacket = new DatagramPacket(
          result.getBytes(),
          result.length(),
          packet.getAddress(),
          packet.getPort()
        );
        socket.send(responsePacket);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String pivotArray(String arrayData) {
    // Split the array data by delimiter (e.g., comma)
    String[] elements = arrayData.split(",");

    // Convert the string elements to integers
    int[] array = new int[elements.length];
    for (int i = 0; i < elements.length; i++) {
      array[i] = Integer.parseInt(elements[i]);
    }

    // Find the pivot index
    int pivotIndex = findPivotIndex(array);

    // Perform the pivot operation on the array
    pivot(array, pivotIndex);

    // Convert the array back to a string
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < array.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(array[i]);
    }

    return sb.toString();
  }

  private static int findPivotIndex(int[] array) {
    int sum = Arrays.stream(array).sum();
    int leftSum = 0;

    for (int i = 0; i < array.length; i++) {
      if (leftSum == sum - leftSum - array[i]) {
        return i;
      }
      leftSum += array[i];
    }

    return -1;
  }

  private static void pivot(int[] array, int pivotIndex) {
    if (pivotIndex >= 0 && pivotIndex < array.length - 1) {
      int temp = array[pivotIndex];
      System.arraycopy(
        array,
        pivotIndex + 1,
        array,
        pivotIndex,
        array.length - pivotIndex - 1
      );
      array[array.length - 1] = temp;
    }
  }
}
