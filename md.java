/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : program to compute a message digest for a file of any type and any size.
*/
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class FileMessageDigest {

  public static void main(String[] args) {
    String filePath = "Z:/Sem2/Networking/question.txt";

    try {
      // Create an instance of the SHA-256 message digest algorithm
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

      // Open the file
      FileInputStream fileInputStream = new FileInputStream(filePath);
      DigestInputStream digestInputStream = new DigestInputStream(
        fileInputStream,
        messageDigest
      );

      // Read the file and compute the message digest
      byte[] buffer = new byte[8192];
      while (digestInputStream.read(buffer) != -1) {
        // No need to do anything; the digest is automatically computed as the file is read
      }

      // Get the computed message digest
      byte[] digest = messageDigest.digest();

      // Convert the digest bytes to a hexadecimal string
      StringBuilder hexString = new StringBuilder();
      for (byte digestByte : digest) {
        String hex = Integer.toHexString(0xff & digestByte);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }

      // Display the message digest
      System.out.println(
        "Message Digest (SHA-256) for file '" + filePath + "':"
      );
      System.out.println(hexString.toString());

      // Close the streams
      digestInputStream.close();
      fileInputStream.close();
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }
  }
}
