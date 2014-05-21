package se.modlab.generics.crypto;

import java.math.*;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;

public class PublicKeyWrapper implements KeyWrapper
{

	private String clientName;
	private int len;
	private PublicKey pk;
	private String filename;

	public PublicKeyWrapper(String clientName,
			int len,
			PublicKey pk,
			String filename) {
		this.clientName = clientName;
		this.len = len;
		this.pk = pk;
		this.filename = filename;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getLength() {
		return len;
	}

	public PublicKey getPublicKey() {  
		return pk;
	}

	public KeyPair getKeyPair() {  
		return null;
	}

	public PrivateKey getPrivateKey() {
		return null;
	}

	public String getFilename() { 
		return filename;
	}

	public void setFilename(String filename) { 
		this.filename = filename;
	}

	public BigInteger getModulus() {
		KeyFactory fact;
		try {
			fact = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		RSAPublicKeySpec pub;
		try {
			pub = (RSAPublicKeySpec) fact.getKeySpec(getPublicKey(), RSAPublicKeySpec.class);
		} catch (InvalidKeySpecException e) {
			return null;
		}
		return pub.getModulus();
	}

}