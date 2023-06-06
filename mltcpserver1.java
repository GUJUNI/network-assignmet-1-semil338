/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Get Frequently Asked Question from a file
To make this program work you need to create a file name "faq.txt"
*/
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class FAQServer {

  private static final int PORT = 7860;

  public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(5);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server is running and listening on port " + PORT);

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected: " + socket);

        pool.execute(new ClientHandler(socket));
      }
    } catch (IOException ex) {
      System.out.println("Server exception: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  private static class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try (
        ObjectOutputStream out = new ObjectOutputStream(
          socket.getOutputStream()
        );
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
      ) {
        String question = null;

        while (true) {
          try {
            question = (String) in.readObject();
          } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
          }

          if (question.equalsIgnoreCase("bye")) {
            System.out.println("Client disconnected: " + socket);
            break;
          }

          String answer = getAnswer(question);
          out.writeObject(answer);
        }
      } catch (IOException ex) {
        System.out.println("Error: " + ex.getMessage());
      }
    }

    private String getAnswer(String question) {
      String answer = "Sorry, I don't know the answer.";

      try (
        BufferedReader reader = new BufferedReader(new FileReader("faq.txt"))
      ) {
        String line;

        while ((line = reader.readLine()) != null) {
          if (line.equalsIgnoreCase(question)) {
            answer = reader.readLine();
            break;
          }
        }
      } catch (IOException ex) {
        System.out.println("Error: " + ex.getMessage());
      }

      return answer;
    }
  }
}
