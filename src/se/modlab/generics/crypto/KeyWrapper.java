package se.modlab.generics.crypto;
    
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyWrapper
{

  public String getClientName();
  public int getLength();
  public PublicKey getPublicKey();
  public PrivateKey getPrivateKey();
  public KeyPair getKeyPair();

}