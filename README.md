# Diffie-Hellman-and-Digital-Signatures-Console-Application
// klasa e vecante
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryptionUtil {
    public static String encrypt(String data, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String data, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedData = Base64.getDecoder().decode(data);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}


//server
// Pjesa e serverit që përdor çelësin e përbashkët për të kriptuar mesazhin
// Pas krijimit të shared secret me Diffie-Hellman

// Krijimi i çelësit AES nga shared secret
byte[] aesKeyBytes = sharedSecret.toByteArray();
aesKeyBytes = Arrays.copyOf(aesKeyBytes, 16); // AES 128-bit key
SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

// Kriptimi i mesazhit me AES
Cipher cipher = Cipher.getInstance("AES");
cipher.init(Cipher.ENCRYPT_MODE, aesKey);
byte[] encryptedMsg = cipher.doFinal("This is a secret message".getBytes());

// Dërgimi i mesazhit të kriptuar tek klienti
out.println(Base64.getEncoder().encodeToString(encryptedMsg));


//klientet
// Klienti merr mesazhin e kriptuar dhe përdor shared secret për dekriptimin
// Kriptimi dhe dekriptimi AES në klient

// Krijimi i çelësit AES nga shared secret
byte[] aesKeyBytes = sharedSecret.toByteArray();
aesKeyBytes = Arrays.copyOf(aesKeyBytes, 16); // AES 128-bit key
SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

// Dekriptimi i mesazhit
Cipher cipher = Cipher.getInstance("AES");
cipher.init(Cipher.DECRYPT_MODE, aesKey);
byte[] encryptedMsg = Base64.getDecoder().decode(in.readLine());
byte[] decryptedMsg = cipher.doFinal(encryptedMsg);

// Shfaqja e mesazhit të dekriptuar
System.out.println("Client received encrypted message: " + new String(decryptedMsg));
