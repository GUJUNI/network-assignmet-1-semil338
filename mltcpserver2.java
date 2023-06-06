/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Hotel Booking from server
*/
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPHotelBookingServer {

  private static final int PORT = 8000;

  // Map to store hotel booking details
  private static Map<String, String> hotelBookingDetails;

  public static void main(String[] args) {
    try {
      // Initialize hotel booking details
      initializeHotelBookingDetails();

      // Create a thread pool for handling client requests
      ExecutorService executorService = Executors.newFixedThreadPool(10);

      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.out.println("Hotel Booking Server started on port " + PORT);

      while (true) {
        // Accept client connection
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket);

        // Submit a client request task to the thread pool
        executorService.submit(new ClientRequestHandler(clientSocket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void initializeHotelBookingDetails() {
    // Initialize the hotel booking details with dummy data
    hotelBookingDetails = new HashMap<>();
    hotelBookingDetails.put("Hotel A", "5 rooms available");
    hotelBookingDetails.put("Hotel B", "10 rooms available");
    hotelBookingDetails.put("Hotel C", "3 rooms available");
  }

  private static class ClientRequestHandler implements Runnable {

    private Socket clientSocket;

    public ClientRequestHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      try {
        BufferedReader reader = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream())
        );
        PrintWriter writer = new PrintWriter(
          clientSocket.getOutputStream(),
          true
        );

        // Read client request
        String request = reader.readLine();
        System.out.println(
          "Received request from client " + clientSocket + ": " + request
        );

        // Process the request and send response to the client
        String response = processRequest(request);
        writer.println(response);

        // Close the reader, writer, and client socket
        reader.close();
        writer.close();
        clientSocket.close();
        System.out.println("Closed connection with client: " + clientSocket);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private String processRequest(String request) {
      // Process the client request and generate the appropriate response
      if (request.equalsIgnoreCase("get_hotel_booking_details")) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hotel Booking Details:\n");

        for (Map.Entry<String, String> entry : hotelBookingDetails.entrySet()) {
          sb
            .append(entry.getKey())
            .append(": ")
            .append(entry.getValue())
            .append("\n");
        }

        return sb.toString();
      } else {
        return "Invalid request";
      }
    }
  }
}
