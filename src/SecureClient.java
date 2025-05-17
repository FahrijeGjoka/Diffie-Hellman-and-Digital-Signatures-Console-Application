import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class SecureClient {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);
        System.out.println("Client: Connected to server.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Merr p, g dhe A nga serveri
        BigInteger p = new BigInteger(in.readLine());
        BigInteger g = new BigInteger(in.readLine());
        BigInteger A = new BigInteger(in.readLine());

        // Krijo DiffieHellmanUtil me p dhe g (duhet të shtosh këtë konstruktor në klasën tënde)
        DiffieHellmanUtil dhClient = new DiffieHellmanUtil(p, g);

        // Merr celesin publik te klientit dhe dergoje te serveri
        BigInteger B = dhClient.getPublicKey();
        out.println(B);

        // Vendos celesin publik te serverit dhe llogarit sekretin e perbashket
        dhClient.setReceivedPublicKey(A);
        BigInteger sharedSecret = dhClient.getSharedSecret();
        System.out.println("Client: Shared secret established: " + sharedSecret.toString(16));

        // Merr public key, signature dhe mesazhin e firmosur nga serveri
        byte[] publicKeyBytes = Base64.getDecoder().decode(in.readLine());
        byte[] signatureBytes = Base64.getDecoder().decode(in.readLine());
        String welcomeMsg = in.readLine();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKeyRSA = keyFactory.generatePublic(pubKeySpec);

        // Verifiko firmën dixhitale (supozon që DigitalSignatureUtil ka metodën verifySignature)
        boolean verified = DigitalSignatureUtil.verifySignature(welcomeMsg,
                Base64.getEncoder().encodeToString(signatureBytes), publicKeyRSA);

        if (verified) {
            System.out.println("Client: Signature is valid. Trusted communication established.");
        } else {
            System.out.println("Client: Signature verification failed!");
        }

        socket.close();
    }
}
