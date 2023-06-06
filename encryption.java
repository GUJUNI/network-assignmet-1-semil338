/*
Name : Semil Khedawala
Course : MCA - I
Sem : 2
Definition : Program That Performs Encryption/Decryption.
*/
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

class FileEncryption {

  private static final String ENCRYPTION_ALGORITHM = "AES";

  public static void main(String[] args) {
    try {
      // Generate a random secret key for encryption
      SecretKey secretKey = generateSecretKey();

      Scanner sc = new Scanner(System.in);
      System.out.print("Enter text to encrypt : ");
      String plaintext = sc.nextLine();

      // Encrypt the plaintext
      String encryptedText = encrypt(plaintext, secretKey);
      System.out.println("Encrypted text: " + encryptedText);

      // Decrypt the encrypted text
      String decryptedText = decrypt(encryptedText, secretKey);
      System.out.println("Decrypted text: " + decryptedText);
      sc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static SecretKey generateSecretKey() throws Exception {
    // Generate a random secret key using AES algorithm
    KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
    keyGenerator.init(128); // Set the key size
    return keyGenerator.generateKey();
  }

  public static String encrypt(String plaintext, SecretKey secretKey)
    throws Exception {
    Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);

    byte[] encryptedBytes = cipher.doFinal(
      plaintext.getBytes(StandardCharsets.UTF_8)
    );
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String decrypt(String encryptedText, SecretKey secretKey)
    throws Exception {
    Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);

    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }
}
