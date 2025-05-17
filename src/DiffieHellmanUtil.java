import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellmanUtil {
    // Variablat per numrin prim p, primitive root g, celesin privat, publik dhe sekretin e perbashket
    private BigInteger p, g, privateKey, publicKey, sharedSecret;
    private SecureRandom random;
    public DiffieHellmanUtil(BigInteger p, BigInteger g) {
        this.p = p;
        this.g = g;
        random = new SecureRandom();
        privateKey = new BigInteger(512, random);
        publicKey = g.modPow(privateKey, p);
    }

    public DiffieHellmanUtil() {
        random = new SecureRandom();

        // Gjenerojme nje numer te madh prim (512 bit)
        p = BigInteger.probablePrime(512, random);

        // Vendosim g = 2, primitive root
        g = BigInteger.valueOf(2);

        // Gjenerojme celesin privat si nje numer random 512 bit
        privateKey = new BigInteger(512, random);

        // Llogarisim celesin publik: g^privateKey mod p
        publicKey = g.modPow(privateKey, p);
    }

    // Kthe numrin prim p
    public BigInteger getP() {
        return p;
    }

    // Kthe primitive root g
    public BigInteger getG() {
        return g;
    }

    // Kthe celesin publik qe do te dergohet palese tjeter
    public BigInteger getPublicKey() {
        return publicKey;
    }

    // Vendos celesin publik qe kemi marre nga pala tjeter dhe llogarit sekretin e perbashket
    public void setReceivedPublicKey(BigInteger receivedPublicKey) {
        // sharedSecret = (receivedPublicKey)^privateKey mod p
        sharedSecret = receivedPublicKey.modPow(privateKey, p);
    }

    // Kthe sekretin e perbashket qe do te perdoret si celes per kriptim simetrik
    public BigInteger getSharedSecret() {
        return sharedSecret;
    }
}
