
import java.security.*;
import java.util.Base64;


public class DigitalSignatureUtil {
    private PrivateKey privateKey;
    private PublicKey publicKey;


    public DigitalSignatureUtil() throws NoSuchAlgorithmException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }


    public String signMessage(String message) throws Exception{
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        byte [] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }


    public static boolean verify(String message, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(message.getBytes());
        return signature.verify(signatureBytes);
    }


}
