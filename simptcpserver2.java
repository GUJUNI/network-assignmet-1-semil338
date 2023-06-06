/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Solve NQueen Problem
*/
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPNQueensServer {

  private static final int SERVER_PORT = 8000;

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
      System.out.println("Server started. Listening on port: " + SERVER_PORT);

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected: " + socket);

        // Create input and output streams for object serialization
        ObjectInputStream inputStream = new ObjectInputStream(
          socket.getInputStream()
        );
        ObjectOutputStream outputStream = new ObjectOutputStream(
          socket.getOutputStream()
        );

        // Receive the board size from the client
        int boardSize = inputStream.readInt();
        System.out.println("Received Board Size: " + boardSize);

        // Solve the N-Queens problem
        List<List<String>> solutions = solveNQueens(boardSize);
        System.out.println("Found " + solutions.size() + " solution(s)");

        // Send the solutions back to the client
        outputStream.writeObject(solutions);

        // Close the streams and socket
        inputStream.close();
        outputStream.close();
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<List<String>> solveNQueens(int n) {
    List<List<String>> solutions = new ArrayList<>();
    solveNQueensHelper(n, 0, new int[n], solutions);
    return solutions;
  }

  private static void solveNQueensHelper(
    int n,
    int row,
    int[] queens,
    List<List<String>> solutions
  ) {
    if (row == n) {
      // All queens have been placed, add the solution to the list
      solutions.add(generateSolution(queens));
    } else {
      for (int col = 0; col < n; col++) {
        if (isValidPlacement(row, col, queens)) {
          queens[row] = col;
          solveNQueensHelper(n, row + 1, queens, solutions);
        }
      }
    }
  }

  private static boolean isValidPlacement(int row, int col, int[] queens) {
    for (int i = 0; i < row; i++) {
      if (
        queens[i] == col ||
        queens[i] - col == i - row ||
        queens[i] - col == row - i
      ) {
        return false;
      }
    }
    return true;
  }

  private static List<String> generateSolution(int[] queens) {
    List<String> solution = new ArrayList<>();
    int n = queens.length;

    for (int i = 0; i < n; i++) {
      StringBuilder sb = new StringBuilder();
      for (int j = 0; j < n; j++) {
        if (queens[i] == j) {
          sb.append("Q");
        } else {
          sb.append(".");
        }
      }
      solution.add(sb.toString());
    }

    return solution;
  }
}
