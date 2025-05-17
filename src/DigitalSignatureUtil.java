import java.security.*;
import java.util.Base64;

public class DigitalSignatureUtil {


    public static String createSignature(String data, PrivateKey privateKey) throws Exception {

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }


    public static boolean verifySignature(String data, String base64Signature, PublicKey publicKey) throws Exception {

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        byte[] digitalSignature = Base64.getDecoder().decode(base64Signature);
        return signature.verify(digitalSignature);
    }
}
