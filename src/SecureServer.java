import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class SecureServer {

   

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server: Listening on port 5000...");
        Socket socket = serverSocket.accept();
        System.out.println("Server: Client connected.");


        DiffieHellmanUtil dhServer = new DiffieHellmanUtil();


        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(dhServer.getP());
        out.println(dhServer.getG());
        out.println(dhServer.getPublicKey());


        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BigInteger B = new BigInteger(in.readLine());
        dhServer.setReceivedPublicKey(B);

        System.out.println("Server: Shared secret established: " + dhServer.getSharedSecret().toString(16));


        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey privateKeyRSA = pair.getPrivate();
        PublicKey publicKeyRSA = pair.getPublic();


        String welcomeMsg = "Welcome to secure server!";


        String digitalSignature = DigitalSignatureUtil.createSignature(welcomeMsg, privateKeyRSA);


        out.println(Base64.getEncoder().encodeToString(publicKeyRSA.getEncoded()));
        out.println(digitalSignature);
        out.println(welcomeMsg);


        byte[] aesKeyBytes = dhServer.getSharedSecret().toByteArray();
        aesKeyBytes = Arrays.copyOf(aesKeyBytes, 16); // AES 128-bit key
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");


        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedMsg = cipher.doFinal("This is a secret message".getBytes());


        out.println(Base64.getEncoder().encodeToString(encryptedMsg));


        socket.close();
        serverSocket.close();
    }
}
