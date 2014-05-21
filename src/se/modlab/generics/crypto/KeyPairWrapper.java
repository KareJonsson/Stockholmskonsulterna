package se.modlab.generics.crypto;
    
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

public class KeyPairWrapper implements KeyWrapper
{

  private String clientName;
  private int len;
  private KeyPair kp;
  private String filename;

  public KeyPairWrapper(String clientName,
                        int len,
                        KeyPair kp,
                        String filename)
  {
    this.clientName = clientName;
    this.len = len;
    this.kp = kp;
    this.filename = filename;
  }

  public String getClientName()
  {
    return clientName;
  }

  public void setClientName(String clientName)
  {
    this.clientName = clientName;
  }

  public int getLength()
  {
    return len;
  }

  public KeyPair getKeyPair()
  {  
    return kp;
  }

  public PublicKey getPublicKey()
  {
    return kp.getPublic();
  }

  public PrivateKey getPrivateKey()
  {
    return kp.getPrivate();
  }

  public String getFilename()
  { 
    return filename;
  }

  public void setFilename(String filename)
  { 
    this.filename = filename;
  }

  public PublicKeyWrapper getPublicKeyWrapper()
  {
    int idx = filename.lastIndexOf('.');
    String pubFilename = filename.substring(0, idx)+".pub";
    return new PublicKeyWrapper(clientName, len, kp.getPublic(), pubFilename);
  }
  
  public BigInteger getModulus() {
	  KeyFactory fact;
	  try {
		  fact = KeyFactory.getInstance("RSA");
	  } catch (NoSuchAlgorithmException e) {
		  return null;
	  }
	  RSAPrivateKeySpec priv;
	  try {
		  priv = (RSAPrivateKeySpec) fact.getKeySpec(getPrivateKey(), RSAPrivateKeySpec.class);
	  } catch (InvalidKeySpecException e) {
		  return null;
	  }
	  return priv.getModulus();
  }

}