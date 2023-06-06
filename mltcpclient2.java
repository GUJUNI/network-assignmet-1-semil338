/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Hotel Booking from server
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPHotelBookingClient {

  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 8000;

  public static void main(String[] args) {
    try {
      // Connect to the server
      Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
      System.out.println("Connected to server: " + socket);

      // Create reader and writer for socket communication
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      );
      PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

      // Send request to the server
      writer.println("get_hotel_booking_details");

      // Read response from the server
      String response = reader.readLine();
      System.out.println("Server response: ");
      System.out.println(response);

      // Close reader, writer, and socket
      reader.close();
      writer.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
