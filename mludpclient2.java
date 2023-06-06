/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Broadcast Weather Info
*/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPWeatherInfoReceiver {

  private static final int SERVER_PORT = 5000;
  private static final int BUFFER_SIZE = 65507;

  public static void main(String[] args) {
    try {
      DatagramSocket socket = new DatagramSocket(SERVER_PORT);
      byte[] buffer = new byte[BUFFER_SIZE];

      while (true) {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // Receive packet from the broadcaster
        socket.receive(packet);

        // Extract weather information from the packet
        String weatherInfo = new String(
          packet.getData(),
          0,
          packet.getLength()
        );

        // Process and display the received weather information
        processWeatherInfo(weatherInfo);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void processWeatherInfo(String weatherInfo) {
    // Implement your logic to process and display the received weather information
    System.out.println("Received weather information: " + weatherInfo);
  }
}
