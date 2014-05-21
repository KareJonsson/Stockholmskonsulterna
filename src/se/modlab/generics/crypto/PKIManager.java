package se.modlab.generics.crypto;

import java.security.interfaces.RSAKey;
import java.util.*;
import java.io.*;
import java.math.*;

public class PKIManager {

	private String certsFilename = null;
	private File certs;
	private Vector<KeyPairWrapper> privateKeyWrappers = null;
	private Vector<PublicKeyWrapper> publicKeyWrappers = null;

	public PKIManager() throws Exception {
		certsFilename = System.getProperty("user.dir")+File.separator+
				".."+File.separator+"certs";
		readKeyset();
	}

	public PKIManager(String certsDirectory) throws Exception {
		certsFilename = certsDirectory;
		readKeyset();
	}
	
	public String getDirectory() {
		return certsFilename;
	}

	public void readKeyset() throws Exception {
		privateKeyWrappers = new Vector<KeyPairWrapper>();
		publicKeyWrappers = new Vector<PublicKeyWrapper>();
		try {
			certs = new File(certsFilename);
			if(certs == null) return;
			File cs[] = certs.listFiles();
			if(cs == null) return;
			for(int i = 0 ; i < cs.length ; i++) {
				String name = cs[i].getAbsolutePath();
				if((name.endsWith(".pub")) || (name.endsWith(".priv"))) {
					KeyWrapper kw = Services3.getKeyWrapper(name);
					if(kw != null) {
						if(kw.getKeyPair() != null) {
							privateKeyWrappers.addElement((KeyPairWrapper) kw);
						}
						else {
							publicKeyWrappers.addElement((PublicKeyWrapper)kw);
						}
					}
				}
			}
		}
		catch(Exception e) {
			//universalTellUser.general(null, new unclassedError("PKImanager - readkeySet", e));
			throw e;
		}
	}
	
	public void addPrivateKey(KeyWrapper kw) {
		privateKeyWrappers.addElement((KeyPairWrapper) kw);
	}

	public void addPublicKey(PublicKeyWrapper kw) {
		BigInteger modulus = kw.getModulus();
		for(int i = 0 ; i < getNoPublics() ; i++) {
			PublicKeyWrapper kwi = getPublic(i);
			BigInteger modulusi = kwi.getModulus();
			if(modulus.equals(modulusi)) {
				return;
			}
		}
		publicKeyWrappers.addElement(kw);
	}

	public boolean hasKey(PublicKeyWrapper kw) {
		BigInteger modulus = kw.getModulus();
		for(int i = 0 ; i < getNoPublics() ; i++) {
			PublicKeyWrapper kwi = getPublic(i);
			BigInteger modulusi = kwi.getModulus();
			if(modulus.equals(modulusi)) {
				return true;
			}
		}
		return false;
	}

	public int getNoPairs() {
		return privateKeyWrappers.size();
	}

	public int getNoPublics() {
		return publicKeyWrappers.size();
	}

	public KeyPairWrapper getPair(int i) {
		if(i < 0) return null;
		if(i >= privateKeyWrappers.size()) return null;
		return (KeyPairWrapper) privateKeyWrappers.elementAt(i);
	}
	
	public KeyPairWrapper getKeyPairFromClientName(String clientName) {
		for(int i = 0 ; i < privateKeyWrappers.size() ; i++) {
			KeyPairWrapper kpw = (KeyPairWrapper) privateKeyWrappers.elementAt(i);
			if(kpw.getClientName().compareTo(clientName) == 0) {
				return kpw;
			}
		}
		return null;
	}

	public KeyPairWrapper getKeyPairFromModulus(BigInteger modulus) {
		for(int i = 0 ; i < privateKeyWrappers.size() ; i++) {
			KeyPairWrapper kpw = privateKeyWrappers.elementAt(i);
			if(kpw.getModulus().equals(modulus)) {
				return kpw;
			}
		}
		return null;
	}

	public PublicKeyWrapper getPublic(int i) {
		if(i < 0) return null;
		if(i >= publicKeyWrappers.size()) return null;
		return (PublicKeyWrapper) publicKeyWrappers.elementAt(i);
	}

	public PublicKeyWrapper getPublicKeyFromClientName(String clientName) {
		for(int i = 0 ; i < publicKeyWrappers.size() ; i++) {
			PublicKeyWrapper pkw = publicKeyWrappers.elementAt(i);
			if(pkw.getClientName().compareTo(clientName) == 0) {
				return pkw;
			}
		}
		return null;
	}

	public PublicKeyWrapper getPublicKeyFromModulus(BigInteger modulus) {
		for(int i = 0 ; i < publicKeyWrappers.size() ; i++) {
			PublicKeyWrapper pkw = publicKeyWrappers.elementAt(i);
			if(pkw.getModulus().equals(modulus)) {
				return pkw;
			}
		}
		return null;
	}


	public KeyPairWrapper getPair(BigInteger modulus) {
		for(int i = 0 ; i < privateKeyWrappers.size() ; i++) {
			KeyPairWrapper kp = (KeyPairWrapper) privateKeyWrappers.elementAt(i);
			BigInteger _m = ((RSAKey) kp.getPublicKey()).getModulus();
			if(_m.compareTo(modulus) != 0) continue;
			return kp;
		}
		return null;
	}

	public PublicKeyWrapper getPublic(RSAKey rsak) {
		BigInteger m = rsak.getModulus();
		return getPublic(m);
	}

	public PublicKeyWrapper getPublic(BigInteger modulus) {
		for(int i = 0 ; i < publicKeyWrappers.size() ; i++) {
			PublicKeyWrapper pk = (PublicKeyWrapper) publicKeyWrappers.elementAt(i);
			BigInteger _m = ((RSAKey) pk.getPublicKey()).getModulus();
			if(_m.compareTo(modulus) != 0) continue;
			return pk;
		}
		return null;
	}
	
	public String toString() {
		return toStringShort();
	}

	public String toStringShort() {
		StringBuffer sb = new StringBuffer();
		sb.append("There are "+getNoPairs()+" private keys. Their clients names are:\n");
		for(int i = 0 ; i < getNoPairs() ; i++) {
			sb.append(getPair(i).getClientName()+"\n");
		}
		sb.append("\n");
		sb.append("There are "+getNoPublics()+" public keys. Their clients names are:\n");
		for(int i = 0 ; i < getNoPublics() ; i++) {
			sb.append(getPublic(i).getClientName()+"\n");
		}
		return sb.toString();
	}

	public String toStringExhaustive() {
		StringBuffer sb = new StringBuffer(toStringShort());
		sb.append("\n\nIn a list with modulus the publics make\n");
		for(int i = 0 ; i < getNoPublics() ; i++) {
			PublicKeyWrapper pkw = getPublic(i);
			String filename = pkw.getFilename();
			if(!filename.startsWith(certsFilename)) {
				filename = certsFilename+File.separator+filename;
			}
			String md5sumStr = "Unable to calculate";
			try {
				  FileInputStream fis = new FileInputStream(filename);
				  byte cont[] = Services.getCleartextFromFile(fis);
				  MD5State internal_md5 = new MD5State();
				  internal_md5.update(cont);
				  byte md5sum[] = internal_md5.digest();

				  md5sumStr = "MD5="+Services.toDenseHexString(md5sum);
			}
			catch(Exception e) {
				e.printStackTrace();
				md5sumStr = "Unable to calculate MD5";
			}

			BigInteger pubmod = pkw.getModulus();
			sb.append(""+pubmod+", "+md5sumStr+", "+pkw.getClientName()+"\n");
		}
		return sb.toString();
	}

}
