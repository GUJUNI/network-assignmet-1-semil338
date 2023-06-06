/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Remove Duplicate using client server program
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UDPDuplicateRemovalServer {

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

        // Extract linked list data from the packet
        String linkedListData = new String(
          packet.getData(),
          0,
          packet.getLength()
        );

        // Remove duplicates from the linked list
        String result = removeDuplicatesFromLinkedList(linkedListData);

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

  private static String removeDuplicatesFromLinkedList(String linkedListData) {
    // Split the linked list data by delimiter (e.g., comma)
    String[] elements = linkedListData.split(",");

    // Create a list to store unique elements
    List<String> uniqueElements = new ArrayList<>();

    // Use a set to keep track of seen elements for duplicate removal
    Set<String> seenElements = new HashSet<>();

    for (String element : elements) {
      // Skip empty elements
      if (element.isEmpty()) {
        continue;
      }

      // Check if the element is already seen
      if (!seenElements.contains(element)) {
        uniqueElements.add(element);
        seenElements.add(element);
      }
    }

    // Join the unique elements back into a string
    return String.join(",", uniqueElements);
  }
}
