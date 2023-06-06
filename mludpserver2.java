/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Broadcast Weather Info
*/
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class UDPWeatherInfoBroadcaster {

  private static final int SERVER_PORT = 5000;

  public static void main(String[] args) {
    try {
      DatagramSocket socket = new DatagramSocket();
      InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

      // Create a thread pool for sending data
      ExecutorService executorService = Executors.newFixedThreadPool(10);

      while (true) {
        // Retrieve weather information for a city from API
        String weatherInfo = getWeatherInfoFromAPI("London");

        // Submit a data-sending task to the thread pool
        executorService.submit(
          new DataSender(socket, weatherInfo, broadcastAddress)
        );

        // Sleep for some time (e.g., every few minutes)
        Thread.sleep(300000); // Adjust the duration as per your requirement
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static String getWeatherInfoFromAPI(String city) {
    String weatherInfo = "";

    try {
      OkHttpClient client = new OkHttpClient();

      // Replace YOUR_API_KEY with your actual API key from OpenWeatherMap
      String apiKey = "YOUR_API_KEY";

      // Create an HTTP request to the API endpoint
      Request request = new Request.Builder()
        .url(
          "http://api.openweathermap.org/data/2.5/weather?q=" +
          city +
          "&appid=" +
          apiKey
        )
        .build();

      // Execute the request and retrieve the response
      Response response = client.newCall(request).execute();

      // Check if the request was successful (HTTP 200 status code)
      if (response.isSuccessful()) {
        // Read the response body and convert it to a string
        weatherInfo = response.body().string();
      } else {
        // Handle error response
        System.out.println(
          "Error: " + response.code() + " - " + response.message()
        );
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return weatherInfo;
  }

  private static class DataSender implements Runnable {

    private DatagramSocket socket;
    private String data;
    private InetAddress address;

    public DataSender(DatagramSocket socket, String data, InetAddress address) {
      this.socket = socket;
      this.data = data;
      this.address = address;
    }

    @Override
    public void run() {
      try {
        byte[] buffer = data.getBytes();

        // Create UDP packet and set broadcast address
        DatagramPacket packet = new DatagramPacket(
          buffer,
          buffer.length,
          address,
          SERVER_PORT
        );

        // Send the packet to all connected clients
        socket.send(packet);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
