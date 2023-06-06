/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Broadcast Stock Price 
*/
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

class StockPriceServer {

  private static final int PORT = 8900;

  public static void main(String[] args) {
    try {
      DatagramSocket serverSocket = new DatagramSocket(PORT);

      System.out.println("Stock Price Server is running.");

      while (true) {
        byte[] sendData = generateStockPrice().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(
          sendData,
          sendData.length,
          InetAddress.getByName("255.255.255.255"),
          PORT
        );

        // Create a new thread to handle each client connection
        Thread clientThread = new Thread(
          new ClientHandler(serverSocket, sendPacket)
        );
        clientThread.start();

        Thread.sleep(1000); // Wait for 1 second
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String generateStockPrice() {
    Random random = new Random();
    double price = 100 + random.nextDouble() * 50; // Generate random price between 100 and 150
    return String.format("%.2f", price);
  }

  private static class ClientHandler implements Runnable {

    private DatagramSocket serverSocket;
    private DatagramPacket sendPacket;

    public ClientHandler(
      DatagramSocket serverSocket,
      DatagramPacket sendPacket
    ) {
      this.serverSocket = serverSocket;
      this.sendPacket = sendPacket;
    }

    @Override
    public void run() {
      try {
        serverSocket.send(sendPacket);
        System.out.println(
          "Sent stock price: " + new String(sendPacket.getData())
        );
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
