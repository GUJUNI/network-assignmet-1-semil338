/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Find Pivot Point in Array
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPPivotArrayClient {

  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 5000;
  private static final int BUFFER_SIZE = 1024;

  public static void main(String[] args) {
    try {
      String arrayData = "3,5,2,6,1";

      // Create a socket for communication with the server
      DatagramSocket socket = new DatagramSocket();

      // Convert the array data to bytes
      byte[] buffer = arrayData.getBytes();

      // Create a packet with the server's address and port
      DatagramPacket packet = new DatagramPacket(
        buffer,
        buffer.length,
        InetAddress.getByName(SERVER_HOST),
        SERVER_PORT
      );

      // Send the packet to the server
      socket.send(packet);

      // Receive the response from the server
      byte[] responseBuffer = new byte[BUFFER_SIZE];
      DatagramPacket responsePacket = new DatagramPacket(
        responseBuffer,
        responseBuffer.length
      );
      socket.receive(responsePacket);

      // Extract the result from the response packet
      String result = new String(
        responsePacket.getData(),
        0,
        responsePacket.getLength()
      );

      System.out.println("Pivoted array: " + result);

      // Close the socket
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
