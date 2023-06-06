/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Broadcast Stock Price 
*/

import java.net.DatagramPacket;
import java.net.DatagramSocket;

class StockPriceClient {

  private static final int SERVER_PORT = 8900;

  public static void main(String[] args) {
    try {
      DatagramSocket clientSocket = new DatagramSocket();

      byte[] receiveBuffer = new byte[1024];

      DatagramPacket receivePacket = new DatagramPacket(
        receiveBuffer,
        receiveBuffer.length
      );

      // Receive stock prices indefinitely
      while (true) {
        clientSocket.receive(receivePacket);

        String receivedData = new String(
          receivePacket.getData(),
          0,
          receivePacket.getLength()
        );
        System.out.println("Received stock price: " + receivedData);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
