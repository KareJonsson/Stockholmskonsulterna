package se.modlab.generics.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import se.modlab.generics.util.Base64;

public class Services3 {

	public static String compileByteArray(byte a[]) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < a.length ; i++) {
			if(i != 0) {
				sb.append(", ");
			}
			sb.append(""+a[i]);
		}
		return sb.toString();
	}

	public static byte[] getByteSequence(byte sequence[], int fromInclusive, int toEclusive) {
		byte out[] = new byte[toEclusive - fromInclusive];
		System.arraycopy(sequence, fromInclusive, out, 0, Math.min((toEclusive - fromInclusive), sequence.length));
		return out;
	}

	public static byte[] assembeByteSqeuence(Vector<byte[]> sequences) {
		int len = 0;
		for(int i = 0 ; i < sequences.size() ; i++) {
			len += sequences.elementAt(i).length;
		}
		byte out[] = new byte[len];
		int pos = 0;
		for(int i = 0 ; i < sequences.size() ; i++) {
			System.arraycopy(sequences.elementAt(i), 0, out, pos, sequences.elementAt(i).length);
			pos += sequences.elementAt(i).length;
		}
		return out;
	}

	public static final int cipherLengthAllow = 117;

	public static byte[] makeCipher(Cipher cipher, byte sequence[], final int chunkLength) throws IllegalBlockSizeException, BadPaddingException {
		int noSeqs = (int) ((sequence.length-1+chunkLength) / chunkLength);
		Vector<byte[]> sequences = new Vector<byte[]>();
		for(int i = 0 ; i < noSeqs ; i++) {
			sequences.add(cipher.doFinal(getByteSequence(sequence, i * chunkLength, Math.min(((i+1) * chunkLength), sequence.length))));
		}
		return assembeByteSqeuence(sequences);
	}

	public static byte[] encryptWithPubKey(byte[] input, PublicKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return makeCipher(cipher, input, cipherLengthAllow);
	}

	public static byte[] encryptWithPrivKey(byte[] input, PrivateKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return makeCipher(cipher, input, cipherLengthAllow);
	}

	public static byte[] decryptWithPrivKey(byte[] input, PrivateKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return makeCipher(cipher, input, 128);
	}

	public static byte[] decryptWithPubKey(byte[] input, PublicKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return makeCipher(cipher, input, 128);
	}

	public static byte[] encryptRSA_asym(byte clear[], PrivateKey sender, PublicKey receiver) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		byte halfway_asym_encrypted[] = encryptWithPrivKey(clear, sender);
		byte encrypted[] = encryptWithPubKey(halfway_asym_encrypted, receiver);
		return encrypted;
	}

	public static byte[] decryptRSA_asym(byte encrypted[], PrivateKey receiver, PublicKey sender) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		byte halfway_asym_decrypted[] = decryptWithPrivKey(encrypted, receiver);
		byte decrypted[] = decryptWithPubKey(halfway_asym_decrypted, sender);
		return decrypted;
	}

	public static void _main(String args[]) throws Exception {
		int totctr = 0;
		for(int rsakeysNr = 100 ; rsakeysNr < 170 ; rsakeysNr++) {
			//System.out.println("Start");
			KeyPair sender = newKeyPair(1024);
			//System.out.println("Sender "+sender);
			KeyPair receiver = newKeyPair(1024);
			//System.out.println("Receiver "+receiver);
			int ctr = 0;
			for(int deskeyNr = 0 ; deskeyNr < 2000 ; deskeyNr++) {
				byte deskey[] = getDESKey();
				//System.out.println("deskey len "+deskey.length);
				byte halfway_asym_encrypted[] = encryptWithPrivKey(deskey, sender.getPrivate());
				//System.out.println("halfway_asym_encrypted len "+halfway_asym_encrypted.length);
				byte encrypted[] = encryptWithPubKey(halfway_asym_encrypted, receiver.getPublic());
				//System.out.println("enc len "+encrypted.length);
				byte halfway_asym_decrypted[] = decryptWithPrivKey(encrypted, receiver.getPrivate());
				//System.out.println("halfway_asym_decrypted len "+halfway_asym_decrypted.length);
				byte decrypted[] = decryptWithPubKey(halfway_asym_decrypted, sender.getPublic());
				//System.out.println("dec len "+decrypted.length);
				boolean equal = isEqual(deskey, decrypted);
				//System.out.println("Lika "+equal);
				if(!equal) {
					ctr++;
				}
			}
			totctr += ctr;
			System.out.println("Nycklar "+rsakeysNr+", sammanlagt "+totctr+" fel, senast "+ctr);
		}
	}
	
	public static boolean equal(byte a[], byte b[]) {
		if(a.length != b.length) {
			return false;
		}
		for(int i = 0 ; i < a.length ; i++) {
			if(a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String args[]) {
		int ctr = 0;
		for(int i = 0 ; i < 100000 ; i++) {
			byte mess[] = getRandomByteSequence(100+((int) (100*Math.random())));
			byte salt[] = getDESKey();
			byte encB[] = padAndEncryptMessage(salt, mess);
			String trans = new String(Base64.encode(encB));
			byte encBE[] = Base64.decode(trans.getBytes());
			byte messE[] = decryptMessageAndUnpad(salt, encBE);
			if(!equal(mess, messE)) {
				System.out.println("Ut "+compileByteArray(mess));
				System.out.println("Mess trans "+trans);
				System.out.println("It "+compileByteArray(messE));
			}
			else {
				ctr++;
			}
		}
		System.out.println("ctr "+ctr);
	}

	public static boolean isEqual(byte a[], byte b[]) {
		if(a.length != b.length) {
			return false;
		}
		for(int i = 0 ; i < a.length ; i++) {
			if(a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	public static byte[] getRandomByteSequence(int len) {
		byte out[] = new byte[len];
		for(int i = 0 ; i < len ; i++) {
			out[i] = (byte) Math.round(256*Math.random());
		}
		return out; 
	}

	public static byte[] getDESKey() {
		return getRandomByteSequence(deskeylength); 
	}
	
	private final static int deskeylength = 40;
	
    private static byte[] makeSaltFromString(String key, int len) throws Exception {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	baos.write(key.getBytes());
    	while(baos.size() < len) {
        	baos.write(key.getBytes());
    	}
    	byte[] desseed = new byte[len];
    	System.arraycopy(baos.toByteArray(), 0, desseed, 0, len);
    	return desseed;
    }

    public static byte[] makeSaltFromString(String key) throws Exception {
    	return makeSaltFromString(key, deskeylength);
    }

	public static KeyPair newKeyPair(int bytes) {
		KeyPairGenerator kpg;
		KeyPair out = null;
		try {
			// Create a 1024 bit RSA private key
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(bytes);
			out = kpg.genKeyPair();
		}
		catch(Exception e) {
			return null;
		}
		return out;
	}

	//private static SecureRandom sr = new SecureRandom();

	public static byte[] pubKeyToBytes(PublicKey key){
		return key.getEncoded(); // X509 for a public key
	}
	public static byte[] privKeyToBytes(PrivateKey key){
		return key.getEncoded(); // PKCS8 for a private key
	}

	/*
	public static PublicKey bytesToPubKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException{
		return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
	}
	public static PrivateKey bytesToPrivKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException{
		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
	}

	public static void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			oout.close();
		}
	}
	*/
	/*
	public static void savePublicToFile(String fileName, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(publicKey, RSAPublicKeySpec.class);
		
		saveToFile(fileName, pub.getModulus(), pub.getPublicExponent());
	}

	public static void savePrivateToFile(String fileName, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(privateKey, RSAPrivateKeySpec.class);
		saveToFile(fileName, priv.getModulus(), priv.getPrivateExponent());
	}
	*/
	
	private static byte[] addTrailingBytes8AndSome(byte bare_message[]) {
		if(bare_message == null) return new byte[0];
		if(bare_message.length == 0) return new byte[0];
		int pad_trail = 8*((int) Math.round((Math.random()*5)+2)) -
				(bare_message.length % 8);
		//System.out.println("Services3.addTrailingBytes8AndSome - "+pad_trail+" bytes to add. "+bare_message.length+" + "+pad_trail+" = "+(bare_message.length+pad_trail));
		int totlen = bare_message.length + pad_trail;
		byte padded_message[] = new byte[totlen];
		System.arraycopy(bare_message, 0, padded_message, 0, bare_message.length);
		for(int i =  bare_message.length ; i < totlen-2 ; i++) {
			padded_message[i] = (byte) (Math.random()*256);
		}
		int dir = (byte) ((256-64)*Math.random());
		//System.out.println("Services3.addTrailingBytes8AndSome - dir = "+dir);
		padded_message[totlen-2] = (byte) dir;
		padded_message[totlen-1] = (byte) (dir+pad_trail);    
		//System.out.println("Services3.addTrailingBytes8AndSome");
		//dumpByteArray(padded_message);
		return padded_message;
	}

	public static byte[] removeTrailingBytes8AndSome(byte padded_message[]) {
		//System.out.println("Services3.removeTrailingBytes8AndSome");
		//dumpByteArray(padded_message);
		if(padded_message == null) return null;
		if(padded_message.length == 0) return null;
		int totlen = padded_message.length;
		int dir = (int) padded_message[totlen-2];
		//System.out.println("Services3.removeTrailingBytes8AndSome - dir = "+dir);
		int pad_trail = ((int) (padded_message[totlen-1])) - dir;
		if(pad_trail < 0) pad_trail += 256;
		//System.out.println("Services3.removeTrailingBytes8AndSome - length = "+padded_message.length+", pad_trail "+pad_trail);
		byte bare_message[] = new byte[padded_message.length - pad_trail];
		System.arraycopy(padded_message, 0, bare_message, 0, padded_message.length - pad_trail);
		return bare_message;
	}

	private static byte[] makePaddedDoubleStringArray(
			byte bare_message1[], 
			byte bare_message2[])
	{
		byte message1_padded[] = addTrailingBytes8AndSome(bare_message1);
		byte message2_padded[] = addTrailingBytes8AndSome(bare_message2);
		byte trail[]= new byte[8];
		System.arraycopy("ab12".getBytes(), 0, trail, 0, 4);
		for(int i = 0 ; i < 4 ; i++) {
			trail[i] = (byte) (Math.random()*256);
		}
		trail[4] = (byte) ((int) (message1_padded.length / 256));
		trail[5] = (byte) ((int) (message1_padded.length % 256));
		//System.out.println("1c 1 : "+((int) trail[4]));
		//System.out.println("2c 1 : "+((int) trail[5]));
		//System.out.println("message1_padded.length = "+message1_padded.length);

		//System.out.println("makePaddedDoubleStringArray mes1 length = "+message1_padded.length);
		trail[6] = (byte) ((int) (message2_padded.length / 256));
		trail[7] = (byte) ((int) (message2_padded.length % 256));
		//System.out.println("1c 2 : "+((int) trail[6]));
		//System.out.println("2c 2 : "+((int) trail[7]));
		//System.out.println("message2_padded.length = "+message2_padded.length);

		//System.out.println("makePaddedDoubleStringArray mes2 length = "+message2_padded.length);
		int totlen = message1_padded.length + 
				message2_padded.length +
				trail.length; // = 8
		byte message_out[] = new byte[totlen];
		System.arraycopy(message1_padded, 0, 
				message_out, 0, 
				message1_padded.length);
		System.arraycopy(message2_padded, 0, 
				message_out, message1_padded.length, 
				message2_padded.length);
		System.arraycopy(trail, 0, 
				message_out, message1_padded.length + 
				message2_padded.length, 
				trail.length);
		//System.out.println("makePaddedDoubleStringArray out = \n"+(new String(message_out))+
		//                   "\nlength = "+message_out.length+"\n\n\n\n");
		return message_out;
	}

	public static String analyse_f2(
			PKIManager pkiman,
			format2_encryptedHolder f2eh)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			if(f2eh.errorMessage != null)
			{ 
				sb.append(
						"Reading the file gave the following problem:\n"+
								f2eh.errorMessage+"\n"+
								"This means that any other information this analysis\n"+
								"can produce is highly questionable. The only way to\n"+
								"know is to decrypt so that the internal encrypted md5"+
						"checksum can be verified.\n");
			}
			else
			{
				sb.append(
						"The file was correctly read. This means that the\n"+
								"external md5 checksum was correct. It does not mean that\n"+
								"the file isn't manipulated. Anybody can calculate a md5\n"+
								"checksum. The only way to know is to decrypt so that the\n"+
						"internal encrypted md5 checksum can be verified.\n");
			}

			if(f2eh.infile_contents_encrypted != null)
			{
				sb.append(
						"This file contains an encrypted file. Its size is\n"+
								"somewhat shorter than "+
								f2eh.infile_contents_encrypted.length+" bytes.\n");
			}

			// Senders E and N
			PublicKeyWrapper sender = 
					pkiman.getPublic(f2eh.senders_modulus);
			if(sender == null)
			{
				sb.append("Senders public key is not known.\n");
			}
			else
			{
				sb.append("The sender is "+sender.getClientName()+"\n");
			}
			// Receivers E and N
			KeyPairWrapper receiver = 
					pkiman.getPair(f2eh.receivers_modulus);
			if(receiver == null)
			{
				PublicKeyWrapper _receiver = 
						pkiman.getPublic(f2eh.receivers_modulus);
				if(_receiver != null)
				{
					sb.append( 
							"The receivers private key is not available but the\n"+
									"public key of the receiver is. Therethrough it is \n"+
									"know that the intended receiver is:\n"+
									_receiver.getClientName()+"\n");
				}
				else
				{
					sb.append(
							"Receivers private key is not known. The public key\n"+
									"of the intended receiver is not available so who\n"+
							"it is is not known.\n");
				}
			}
			else
			{
				sb.append("The receiver is "+receiver.getClientName()+"\n");
			}
			if((sender != null) &&
					(receiver != null))
			{
				sb.append("The proper keys to decrypt ARE available.\n");
			}
			else
			{
				sb.append("The proper keys to decrypt are NOT available.\n");
			}
		}
		catch(Throwable t)
		{
			sb.append("Anaylsis failed. Problem is:\n"+t);
		}
		return sb.toString();
	}

	public static byte[] decryptMessage(byte salt[],
			byte message[])
	{
		try
		{
			DES5 cipher_DES5 = new DES5();
			cipher_DES5.setKey(salt);
			return cipher_DES5.decrypt(message);
		}
		catch(Throwable t)
		{
		}
		return null;
	}

	public static byte[] decryptMessageAndUnpad(byte salt[],
			byte message[])
	{
		try
		{
			DES5 cipher_DES5 = new DES5();
			cipher_DES5.setKey(salt);
			return removeTrailingBytes8AndSome(cipher_DES5.decrypt(message));
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public static byte[] encryptMessage(byte salt[], byte message[])
	{
		try {
			DES5 cipher_DES5 = new DES5();
			cipher_DES5.setKey(salt);
			return cipher_DES5.encrypt(message);
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public static byte[] padAndEncryptMessage(byte salt[], byte message[])
	{
		try {
			DES5 cipher_DES5 = new DES5();
			cipher_DES5.setKey(salt);
			return cipher_DES5.encrypt(addTrailingBytes8AndSome(message));
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public static byte[] getMessage1(byte paddedDoubleStringArray[])
	{
		//System.out.println(new String(paddedDoubleStringArray));
		int i1;
		int i2;

		i1 = (int) paddedDoubleStringArray[paddedDoubleStringArray.length - 4];
		if(i1 < 0) i1 += 256;
		i2 = (int) paddedDoubleStringArray[paddedDoubleStringArray.length - 3];
		if(i2 < 0) i2 += 256;
		int mes1len = i1*256+i2;

		//System.out.println("getMessage1 i1 = "+i1);
		//System.out.println("getMessage1 i2 = "+i2);
		/*
    int mes1len = ((byte) paddedDoubleStringArray[paddedDoubleStringArray.length - 4])*256+
                  ((byte) paddedDoubleStringArray[paddedDoubleStringArray.length - 3]);
		 */
		//System.out.println("getMessage1\n"+
		//                   (new String(paddedDoubleStringArray))+"\n"+
		//                   "length = "+mes1len);
		byte message_out[] = new byte[mes1len];
		//System.out.println("getMessage1 mes1len = "+mes1len);
		System.arraycopy(paddedDoubleStringArray, 0, 
				message_out, 0, 
				mes1len);
		return removeTrailingBytes8AndSome(message_out);
	}

	public static byte[] getMessage2(byte paddedDoubleStringArray[])
	{
		int i1;
		int i2;

		i1 = (int) paddedDoubleStringArray[paddedDoubleStringArray.length - 4];
		if(i1 < 0) i1 += 256;
		i2 = (int) paddedDoubleStringArray[paddedDoubleStringArray.length - 3];
		if(i2 < 0) i2 += 256;
		int mes1len = i1*256+i2;
		//System.out.println("getMessage2 mes1len = "+mes1len);

		i1 = (int) paddedDoubleStringArray[paddedDoubleStringArray.length - 2];
		if(i1 < 0) i1 += 256;
		i2 = (int) paddedDoubleStringArray[paddedDoubleStringArray.length - 1];
		if(i2 < 0) i2 += 256;
		int mes2len = i1*256+i2;
		//System.out.println("getMessage2 mes2len = "+mes2len);

		byte message_out[] = new byte[mes2len];
		System.arraycopy(paddedDoubleStringArray, mes1len, 
				message_out, 0, 
				mes2len);
		return removeTrailingBytes8AndSome(message_out);
	}



	public static format2_decryptedHolder decrypt_f2(
			PKIManager pkiman,
			format2_encryptedHolder f2eh)
	{
		format2_decryptedHolder f2dh = new format2_decryptedHolder();
		try
		{
			MD5State internal_md5 = new MD5State();
			if(f2eh.errorMessage != null)
			{ 
				f2dh.errorMessage = 
						"Reading the encrypted message was not successful.\n"+
								"The encryptions error message was:\n"+
								f2eh.errorMessage;
				return f2dh;
			}

			// Senders E and N
			f2dh.sender = pkiman.getPublic(f2eh.senders_modulus);
			if(f2dh.sender == null)
			{
				f2dh.errorMessage = "Senders public key is not known";
				return f2dh;
			}
			//System.out.println("Sender "+f2dh.sender.getClientName());
			internal_md5.update(f2eh.senders_modulus.toByteArray());

			// Receivers E and N
			f2dh.receiver = pkiman.getPair(f2eh.receivers_modulus);
			if(f2dh.receiver == null)
			{
				PublicKeyWrapper receiver = 
						pkiman.getPublic(f2eh.receivers_modulus);
				if(receiver != null)
				{
					f2dh.errorMessage = 
							"The receivers private key is not available but the\n"+
									"public key of the receiver is. Therethrough it is \n"+
									"know that the intended receiver is:\n"+
									receiver.getClientName();
					return f2dh;
				}
				f2dh.errorMessage = 
						"Receivers private key is not known. The public key\n"+
								"of the intended receiver is not available so who\n"+
								"it is is not known.";
				return f2dh;
			}
			//System.out.println("Receiver "+f2dh.receiver.getClientName());
			internal_md5.update(f2eh.receivers_modulus.toByteArray());

			// Encrypted salt
			internal_md5.update(f2eh.salt_encrypted);
			//System.out.println("Decrypt Salt encrypted= "+f2eh.salt_encrypted);
			//System.out.println("Encrypted Salt = "+f2eh.salt_encrypted);
			//BigInteger salt = decryptSalt(f2dh.receiver, 
			//		f2dh.sender,
			//		f2eh.salt_encrypted);
			byte salt[] = decryptRSA_asym(f2eh.salt_encrypted, f2dh.receiver.getPrivateKey(), f2dh.sender.getPublicKey());
			//System.out.println("Decrypt Salt = "+salt);

			// Infile contents
			byte infile_contents_padded[] = 
					decryptMessage(salt, f2eh.infile_contents_encrypted);
			if(infile_contents_padded == null)
			{
				f2dh.errorMessage = 
						"File contents not possible do decrypt!";
				return f2dh;
			}
			f2dh.infile_contents = 
					removeTrailingBytes8AndSome(infile_contents_padded);
			internal_md5.update(f2dh.infile_contents);

			// Filename and popup message
			byte filename_popupmessage_padded[] = 
					decryptMessage(salt, f2eh.filename_popupmessage_encrypted);
			if(filename_popupmessage_padded == null)
			{
				f2dh.errorMessage = 
						"Filename of encrypted file not possible do decrypt!";
				return f2dh;
			}
			//System.out.println("Decrypt decrypted : \n\n"+(new String(filename_popupmessage_padded))+" "+filename_popupmessage_padded.length);
			//System.out.println("Decrypt encrypted : \n\n"+(new String(f2eh.filename_popupmessage_encrypted))+" "+f2eh.filename_popupmessage_encrypted.length);
			f2dh.filename = getMessage1(filename_popupmessage_padded);
			internal_md5.update(f2dh.filename);
			f2dh.popupmessage = getMessage2(filename_popupmessage_padded);
			internal_md5.update(f2dh.popupmessage);

			// Internal md5 checksum
			byte messages_md5_checksum[] = 
					decryptMessage(salt, f2eh.internal_md5_checksum_encrypted);
			if(messages_md5_checksum == null)
			{
				f2dh.errorMessage = 
						"Internal md5 checksum not possible do decrypt!";
				return f2dh;
			}
			byte internal_md5_checksum_calculated[] = internal_md5.digest();
			if(messages_md5_checksum.length !=
					internal_md5_checksum_calculated.length)
			{
				f2dh.md5_checksum_ok = false;
				f2dh.errorMessage = 
						"Internal calculated md5 checksum not of same\n"+
								"length as decrypted internal md5 checksum";
				return f2dh;
			}
			for(int i = 0 ; i < messages_md5_checksum.length ; i++)
			{
				if(messages_md5_checksum[i] !=
						internal_md5_checksum_calculated[i])
				{
					f2dh.md5_checksum_ok = false;
					f2dh.errorMessage = 
							"Internal calculated md5 checksum not same\n"+
									"as decrypted internal md5 checksum";
					return f2dh;
				}
			}
			f2dh.md5_checksum_ok = true;

			// All ok
			f2dh.errorMessage = null;
		}
		catch(Throwable t)
		{
			f2dh.errorMessage = 
					"Decryption unsuccessful due to:\n"+t;
			t.printStackTrace();
		}
		return f2dh;
	}

	public static String analyse_f3(
			PKIManager pkiman,
			format3_encryptedHolder f3eh)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			if(f3eh.errorMessage != null)
			{ 
				sb.append(
						"Reading the file gave the following problem:\n"+
								f3eh.errorMessage+"\n"+
								"This means that any other information this analysis\n"+
								"can produce is highly questionable. The only way to\n"+
								"know is to decrypt so that the internal encrypted md5"+
						"checksum can be verified.\n");
			}
			else
			{
				sb.append(
						"The file was correctly read. This means that the\n"+
								"external md5 checksum was correct. It does not mean that\n"+
								"the file isn't manipulated. Anybody can calculate a md5\n"+
								"checksum. The only way to know is to decrypt so that the\n"+
						"internal encrypted md5 checksum can be verified.\n");
			}

			sb.append("This file contains a popup message.\n");

			// Senders E and N
			PublicKeyWrapper sender = 
					pkiman.getPublic(f3eh.senders_modulus);
			if(sender == null)
			{
				sb.append("Senders public key is not known.\n");
			}
			else
			{
				sb.append("The sender is "+sender.getClientName()+"\n");
			}
			// Receivers E and N
			KeyPairWrapper receiver = 
					pkiman.getPair(f3eh.receivers_modulus);
			if(receiver == null)
			{
				PublicKeyWrapper _receiver = 
						pkiman.getPublic(f3eh.receivers_modulus);
				if(_receiver != null)
				{
					sb.append( 
							"The receivers private key is not available but the\n"+
									"public key of the receiver is. Therethrough it is \n"+
									"know that the intended receiver is:\n"+
									_receiver.getClientName()+"\n");
				}
				else
				{
					sb.append(
							"Receivers private key is not known. The public key\n"+
									"of the intended receiver is not available so who\n"+
							"it is is not known.\n");
				}
			}
			else
			{
				sb.append("The receiver is "+receiver.getClientName()+"\n");
			}
			if((sender != null) &&
					(receiver != null))
			{
				sb.append("The proper keys to decrypt ARE available.\n");
			}
			else
			{
				sb.append("The proper keys to decrypt are NOT available.\n");
			}
		}
		catch(Throwable t)
		{
			sb.append("Anaylsis failed. Problem is:\n"+t);
		}
		return sb.toString();
	}

	public static format3_decryptedHolder decrypt_f3(
			PKIManager pkiman,
			format3_encryptedHolder f3eh)
	{
		format3_decryptedHolder f3dh = new format3_decryptedHolder();
		try
		{
			MD5State internal_md5 = new MD5State();
			if(f3eh.errorMessage != null)
			{ 
				f3dh.errorMessage = 
						"Reading the encrypted message was not successful.\n"+
								"The encryptions error message was:\n"+
								f3eh.errorMessage;
				return f3dh;
			}

			// Senders E and N
			f3dh.sender = pkiman.getPublic(f3eh.senders_modulus);
			if(f3dh.sender == null)
			{
				f3dh.errorMessage = "Senders public key is not known";
				return f3dh;
			}
			//System.out.println("Sender "+f2dh.sender.getClientName());
			//System.out.println("services2.decrypt_f3 senders E "+f3eh.senders_modulus);
			//System.out.println("services2.decrypt_f3 receivers E "+f3eh.receivers_modulus);
			internal_md5.update(f3eh.senders_modulus.toByteArray());

			// Receivers E and N
			f3dh.receiver = pkiman.getPair(f3eh.receivers_modulus);
			if(f3dh.receiver == null)
			{
				PublicKeyWrapper receiver = 
						pkiman.getPublic(f3eh.receivers_modulus);
				if(receiver != null)
				{
					f3dh.errorMessage = 
							"The receivers private key is not available but the\n"+
									"public key of the receiver is. Therethrough it is \n"+
									"know that the intended receiver is:\n"+
									receiver.getClientName();
					return f3dh;
				}
				f3dh.errorMessage = 
						"Receivers private key is not known. The public key\n"+
								"of the intended receiver is not available so who\n"+
								"it is is not known.";
				return f3dh;
			}
			//System.out.println("Receiver "+f3dh.receiver.getClientName());
			internal_md5.update(f3eh.receivers_modulus.toByteArray());

			// Encrypted salt
			internal_md5.update(f3eh.salt_encrypted);
			//System.out.println("Decrypt Salt encrypted= "+f3eh.salt_encrypted);
			//System.out.println("Encrypted Salt = "+f3eh.salt_encrypted);

			//BigInteger salt = decryptSalt(f3dh.receiver, 
			//		f3dh.sender,
			//		f3eh.salt_encrypted);

			byte salt[] = decryptRSA_asym(f3eh.salt_encrypted, f3dh.receiver.getPrivateKey(), f3dh.sender.getPublicKey());

			//System.out.println("servcies.decrypt_f3 - Salt = "+compileByteArray(salt));
			//System.out.println("servcies.decrypt_f3 - Encrypted Salt = "+compileByteArray(f3eh.salt_encrypted));

			//System.out.println("Decrypt Salt = "+salt);

			// Filename and popup message
			byte popupmessage_padded[] = 
					decryptMessage(salt, f3eh.popupmessage_encrypted);
			if(popupmessage_padded == null)
			{
				f3dh.errorMessage = 
						"Popupmessage of encrypted file not possible do decrypt!";
				return f3dh;
			}
			//System.out.println("Decrypt decrypted : \n\n"+(new String(filename_popupmessage_padded))+" "+filename_popupmessage_padded.length);
			//System.out.println("Decrypt encrypted : \n\n"+(new String(f2eh.filename_popupmessage_encrypted))+" "+f2eh.filename_popupmessage_encrypted.length);
			f3dh.popupmessage = removeTrailingBytes8AndSome(popupmessage_padded);
			internal_md5.update(f3dh.popupmessage);

			// Internal md5 checksum
			byte messages_md5_checksum[] = 
					decryptMessage(salt, f3eh.internal_md5_checksum_encrypted);
			if(messages_md5_checksum == null)
			{
				f3dh.errorMessage = 
						"Internal md5 checksum not possible do decrypt!";
				return f3dh;
			}
			byte internal_md5_checksum_calculated[] = internal_md5.digest();
			if(messages_md5_checksum.length !=
					internal_md5_checksum_calculated.length)
			{
				f3dh.md5_checksum_ok = false;
				f3dh.errorMessage = 
						"Internal calculated md5 checksum not of same\n"+
								"length as decrypted internal md5 checksum";
				return f3dh;
			}
			for(int i = 0 ; i < messages_md5_checksum.length ; i++)
			{
				if(messages_md5_checksum[i] !=
						internal_md5_checksum_calculated[i])
				{
					f3dh.md5_checksum_ok = false;
					f3dh.errorMessage = 
							"Internal calculated md5 checksum not same\n"+
									"as decrypted internal md5 checksum";
					return f3dh;
				}
			}
			f3dh.md5_checksum_ok = true;

			// All ok
			f3dh.errorMessage = null;
		}
		catch(Throwable t)
		{
			f3dh.errorMessage = 
					"Decryption unsuccessful due to:\n"+t;
			t.printStackTrace();
		}
		return f3dh;
	}

	public static format2_encryptedHolder encrypt_f2(
			format2_cleartextHolder f2ch)
	{
		//System.out.println("Sender "+f2ch.sender.getClientName());
		//System.out.println("Receiver "+f2ch.receiver.getClientName());
		format2_encryptedHolder f2eh = new format2_encryptedHolder();
		try
		{
			MD5State internal_md5 = new MD5State();
			if(f2ch.errorMessage != null)
			{ 
				f2eh.errorMessage = 
						"The cleartext message appears to be erroneous.\n"+
								"The decryptions error message was:\n"+
								f2eh.errorMessage;
				return f2eh;
			}

			// Senders E and N
			f2eh.senders_modulus = ((RSAPrivateKey) (f2ch.sender.getPrivateKey())).getModulus();
			internal_md5.update(f2eh.senders_modulus.toByteArray());

			// Receivers E and N
			f2eh.receivers_modulus = ((RSAPublicKey) (f2ch.receiver.getPublicKey())).getModulus();
			internal_md5.update(f2eh.receivers_modulus.toByteArray());

			// Salt
			//BigInteger salt = null;
			//do
			//{
			//	salt = new BigInteger(8*40, 64, new SecureRandom());
			//} while(salt.toByteArray().length != 41);
			//System.out.println("Cleartext Salt = "+salt);
			//f2eh.salt_encrypted = encryptSalt(f2ch.sender, f2ch.receiver, salt);
			byte salt[] = getDESKey();
			f2eh.salt_encrypted = encryptRSA_asym(salt, f2ch.sender.getPrivateKey(), f2ch.receiver.getPublicKey());
			//System.out.println("Encrypted Salt = "+f2eh.salt_encrypted);
			if(f2eh.salt_encrypted == null)
			{
				f2eh.errorMessage = 
						"Salt not possible do encrypt!";
				return f2eh;
			}
			internal_md5.update(f2eh.salt_encrypted);
			//System.out.println("Encrypt Salt = "+salt);
			//System.out.println("Encrypt Salt encrypted = "+f2eh.salt_encrypted);

			// Infile contents 
			internal_md5.update(f2ch.infile_contents);
			byte infile_contents_padded[] = 
					addTrailingBytes8AndSome(f2ch.infile_contents);
			f2eh.infile_contents_encrypted =  
					encryptMessage(salt, infile_contents_padded);
			if(f2eh.infile_contents_encrypted == null)
			{
				f2eh.errorMessage = 
						"Infiles contents not possible do encrypt!";
				return f2eh;
			}

			// Filename and popup message
			//System.out.println("Filename = "+(new String(f2ch.filename)));
			//System.out.println("Popupmessage = "+(new String(f2ch.popupmessage)));
			internal_md5.update(f2ch.filename);
			internal_md5.update(f2ch.popupmessage);
			byte filename_popupmessage_padded[] = 
					makePaddedDoubleStringArray(f2ch.filename, f2ch.popupmessage);
			//System.out.println("Filename = "+(new String(f2ch.filename)));
			//System.out.println("Popupmessage = "+(new String(f2ch.popupmessage)));
			//System.out.println("Filename = "+(new String(getMessage1(filename_popupmessage_padded))));
			//System.out.println("Popupmessage = "+(new String(getMessage2(filename_popupmessage_padded))));
			f2eh.filename_popupmessage_encrypted =  
					encryptMessage(salt, filename_popupmessage_padded);
			if(f2eh.filename_popupmessage_encrypted == null)
			{
				f2eh.errorMessage = 
						"Filename of encrypted file not possible do encrypt!";
				return f2eh;
			}
			//System.out.println("Encrypt decrypted : \n\n"+(new String(filename_popupmessage_padded))+" "+filename_popupmessage_padded.length);
			//System.out.println("Encrypt encrypted : \n\n"+(new String(f2eh.filename_popupmessage_encrypted))+" "+f2eh.filename_popupmessage_encrypted.length);
			//System.out.println("Fn pum = "+(new String(f2eh.filename_popupmessage_encrypted))+"\n");
			// Internal md5 checksum
			byte internal_md5_calculated[] = internal_md5.digest();
			f2eh.internal_md5_checksum_encrypted = 
					encryptMessage(salt, internal_md5_calculated);
			if(f2eh.internal_md5_checksum_encrypted == null)
			{
				f2eh.errorMessage = 
						"Internal checksum not possible do encrypt!";
				return f2eh;
			}

			// All ok
			f2eh.errorMessage = null;
		}
		catch(Exception e)
		{
			f2eh.errorMessage = "Encryption failed\n"+e;
			e.printStackTrace();
		}
		return f2eh;
	}

	public static format3_encryptedHolder encrypt_f3(
			format3_cleartextHolder f3ch)
	{
		//System.out.println("Sender "+f2ch.sender.getClientName());
		//System.out.println("Receiver "+f2ch.receiver.getClientName());
		format3_encryptedHolder f3eh = new format3_encryptedHolder();
		try
		{
			MD5State internal_md5 = new MD5State();
			if(f3ch.errorMessage != null)
			{ 
				f3eh.errorMessage = 
						"The cleartext message appears to be erroneous.\n"+
								"The decryptions error message was:\n"+
								f3eh.errorMessage;
				return f3eh;
			}

			// Senders E and N
			f3eh.senders_modulus = ((RSAPrivateKey) (f3ch.sender.getPrivateKey())).getModulus();
			internal_md5.update(f3eh.senders_modulus.toByteArray());

			// Receivers E and N
			f3eh.receivers_modulus = ((RSAPublicKey) (f3ch.receiver.getPublicKey())).getModulus();
			internal_md5.update(f3eh.receivers_modulus.toByteArray());

			//System.out.println("services2.encrypt_f3 senders E "+f3eh.senders_modulus);
			//System.out.println("services2.encrypt_f3 receivers E "+f3eh.receivers_modulus);

			// Salt
			//BigInteger salt = null;
			//do
			//{
			//	salt = new BigInteger(8*40, 64, new SecureRandom());
			//} while(salt.toByteArray().length != 41);
			//System.out.println("Cleartext Salt = "+salt);
			byte salt[] = getDESKey();
			//f3eh.salt_encrypted = encryptSalt(f3ch.sender, f3ch.receiver, salt);
			f3eh.salt_encrypted = encryptRSA_asym(salt, f3ch.sender.getPrivateKey(), f3ch.receiver.getPublicKey());
			//System.out.println("servcies.encrypt_f3 - Salt = "+compileByteArray(salt));
			//System.out.println("servcies.encrypt_f3 - Encrypted Salt = "+compileByteArray(f3eh.salt_encrypted));
			if(f3eh.salt_encrypted == null)
			{
				f3eh.errorMessage = 
						"Salt not possible do encrypt!";
				return f3eh;
			}
			internal_md5.update(f3eh.salt_encrypted);
			//System.out.println("Encrypt Salt = "+salt);
			//System.out.println("Encrypt Salt encrypted = "+f2eh.salt_encrypted);

			// Popup message
			//System.out.println("Filename = "+(new String(f2ch.filename)));
			//System.out.println("Popupmessage = "+(new String(f2ch.popupmessage)));
			internal_md5.update(f3ch.popupmessage);
			byte popupmessage_padded[] = 
					addTrailingBytes8AndSome(f3ch.popupmessage);
			//System.out.println("Filename = "+(new String(f2ch.filename)));
			//System.out.println("Popupmessage = "+(new String(f2ch.popupmessage)));
			//System.out.println("Filename = "+(new String(getMessage1(filename_popupmessage_padded))));
			//System.out.println("Popupmessage = "+(new String(getMessage2(filename_popupmessage_padded))));
			f3eh.popupmessage_encrypted =  
					encryptMessage(salt, popupmessage_padded);
			if(f3eh.popupmessage_encrypted == null)
			{
				f3eh.errorMessage = 
						"Filename of encrypted file not possible do encrypt!";
				return f3eh;
			}
			//System.out.println("Encrypt decrypted : \n\n"+(new String(filename_popupmessage_padded))+" "+filename_popupmessage_padded.length);
			//System.out.println("Encrypt encrypted : \n\n"+(new String(f2eh.filename_popupmessage_encrypted))+" "+f2eh.filename_popupmessage_encrypted.length);
			//System.out.println("Fn pum = "+(new String(f2eh.filename_popupmessage_encrypted))+"\n");
			// Internal md5 checksum
			byte internal_md5_calculated[] = internal_md5.digest();
			f3eh.internal_md5_checksum_encrypted = 
					encryptMessage(salt, internal_md5_calculated);
			if(f3eh.internal_md5_checksum_encrypted == null)
			{
				f3eh.errorMessage = 
						"Internal checksum not possible do encrypt!";
				return f3eh;
			}

			// All ok
			f3eh.errorMessage = null;
		}
		catch(Exception e)
		{
			f3eh.errorMessage = "Encryption failed\n"+e;
			e.printStackTrace();
		}
		return f3eh;
	}

	public static byte[] encrypt(KeyPairWrapper own, 
			PublicKeyWrapper others, 
			byte message[]) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
			{
		if(own.getLength() != others.getLength()) return null;
		return encrypt(own.getKeyPair().getPrivate(), 
				others.getPublicKey(),
				message);
			}

	public static byte[] encrypt(PrivateKey own, 
			PublicKey others, 
			byte message[]) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
			{
		//System.out.println("services - encrypt");
		try
		{
			byte salt[] = getDESKey();
			byte salt_encrypted[] = encryptRSA_asym(salt, own, others);

			DES3 cipher_DES3 = new DES3();
			cipher_DES3.setKey(salt);

			int trail = message.length % 8;
			byte encrypted[] = cipher_DES3.encrypt(Services.addTrailingBytes8(message, (byte) 0x20));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(salt_encrypted);
			oos.writeInt(trail);
			oos.writeObject(encrypted);
			oos.close();
			return baos.toByteArray();
		}
		catch(IOException e)
		{
			System.out.println("encrypt IOExcpetion");
		}
		return null;
			}

	public static byte[] decrypt(KeyPairWrapper own, 
			PublicKeyWrapper others, 
			byte message[]) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
			{
		if(own.getLength() != others.getLength()) return null;
		return decrypt(own.getKeyPair().getPrivate(), 
				others.getPublicKey(),
				message);
			}

	public static byte[] decrypt(PrivateKey own, 
			PublicKey others, 
			byte message[]) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
			{
		//System.out.println("services - decrypt");
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(message);
			ObjectInputStream ois = new ObjectInputStream(bais);
			byte salt_encrypted[] = (byte[]) ois.readObject();
			byte salt[] = decryptRSA_asym(salt_encrypted, own, others);
			int trail = ois.readInt();
			byte encrypted[] = (byte[]) ois.readObject();
			ois.close();

			DES3 cipher_DES3 = new DES3();
			cipher_DES3.setKey(salt);
			byte message_decrypted[] = cipher_DES3.decrypt(encrypted);
			return Services.removeTrailingBytes(message_decrypted, (8 - trail) % 8);
		}
		catch(IOException e)
		{
			System.out.println("decrypt IOExcpetion");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("decrypt ClassNotFoundException");
		}
		return null;
	}
	
	public static byte[] getBytesFromPublicKey(PublicKeyWrapper pkw) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(writePublic(baos, pkw)) {
			return baos.toByteArray();
		}
		return null;
	}

	public static boolean writePublic(PublicKeyWrapper pkw) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pkw.getFilename());
		}
		catch(Exception e) {
			return false;
		}
		return writePublic(fos, pkw);
	}
	
	public static boolean writePublic(OutputStream os, PublicKeyWrapper pkw) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);

			oos.writeObject("pub");
			oos.writeObject(pkw.getClientName());
			oos.writeInt(pkw.getLength());
			oos.writeObject((RSAPublicKey)pkw.getPublicKey());
			oos.close();
			return true;
		}
		catch(Exception e){
		}
		return false;

	}

	public static boolean writePair(KeyPairWrapper kpw) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(kpw.getFilename());
		}
		catch(Exception e) {
			return false;
		}
		return writePair(fos, kpw);
	}

	public static boolean writePair(OutputStream os, KeyPairWrapper kpw) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);

			oos.writeObject("priv");
			oos.writeObject(kpw.getClientName());
			oos.writeInt(kpw.getLength());
			oos.writeObject((RSAPublicKey)kpw.getPublicKey());
			oos.writeObject((RSAPrivateKey)kpw.getPrivateKey());
			oos.close();
			return true;
		}
		catch(Exception e) {
		}
		return false;

	}

	public static PublicKeyWrapper getPublicKey(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			return getPublicKey(fis, filename);
		}
		catch(Exception fnfe) {
		}
		return null;
	}

	public static PublicKeyWrapper getPublicKey(InputStream fis, String filename) {
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			String type = (String) ois.readObject();
			if(type.compareTo("pub") != 0) return null;
			String clientName = (String) ois.readObject();
			int len = ois.readInt();
			RSAPublicKey puk = (RSAPublicKey) ois.readObject();
			ois.close();
			return new PublicKeyWrapper(clientName, len, puk, filename);
		}
		catch(Exception e) {
		}
		return null;
	}

	public static PublicKeyWrapper getPublicKey(byte array[]) {
		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		PublicKeyWrapper out = getPublicKey(bais, null);
		if(out == null) {
			return null;
		}
		String clientName = out.getClientName();
		String name = clientName.replaceAll("\\W+", "");
		out.setFilename(name+"_"+System.currentTimeMillis()+".pub");
		return out;
	}

	// String name = s.replaceAll("\W+", "");

	public static KeyPairWrapper getKeyPair(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			return getKeyPair(fis, filename);
		}
		catch(FileNotFoundException fnfe) {
		}
		return null;
	}

	public static KeyPairWrapper getKeyPair(InputStream is, String filename) {
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			String type = (String) ois.readObject();
			if(type.compareTo("priv") != 0) return null;
			String clientName = (String) ois.readObject();
			int len = ois.readInt();
			RSAPublicKey puk = (RSAPublicKey) ois.readObject();
			RSAPrivateKey prk = (RSAPrivateKey) ois.readObject();
			ois.close();
			return new KeyPairWrapper(clientName, len, new KeyPair(puk, prk), filename);
		}
		catch(Exception e) {
		}
		return null;
	}

	public static KeyWrapper getKeyWrapper(String absolutePath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(absolutePath);
		}
		catch(Exception e) {
			return null;
		}
		int idx = absolutePath.lastIndexOf(File.separator);
		if(idx == -1) {
			return getKeyWrapper(absolutePath, fis);
		}
		String atomicFilename = absolutePath.substring(idx);
		return getKeyWrapper(atomicFilename, fis);
	}
	
	public static KeyWrapper getKeyWrapper(String filename, FileInputStream fis) {
		try
		{
			//FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			String type = (String) ois.readObject();
			if(type.compareTo("priv") == 0)
			{
				String clientName = (String) ois.readObject();
				int len = ois.readInt();
				RSAPublicKey puk = (RSAPublicKey) ois.readObject();
				RSAPrivateKey prk = (RSAPrivateKey) ois.readObject();
				return new KeyPairWrapper(clientName, len, 
						new KeyPair(puk, prk), filename);
			}
			if(type.compareTo("pub") == 0)
			{
				String clientName = (String) ois.readObject();
				int len = ois.readInt();
				RSAPublicKey puk = (RSAPublicKey) ois.readObject();
				ois.close();
				return new PublicKeyWrapper(clientName, len, puk, filename);
			}
		}
		catch(ClassNotFoundException e)
		{
		}
		catch(IOException e)
		{
		}
		return null;
	}

	public static boolean generateKeys(int nobytes, String filePair, String filePub, String clientName) {
		KeyPair kp = newKeyPair(nobytes);

		try
		{
			FileOutputStream fos = new FileOutputStream(filePair);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject("priv");
			oos.writeObject(clientName);
			oos.writeInt(nobytes);
			oos.writeObject((RSAPublicKey)kp.getPublic());
			oos.writeObject((RSAPrivateKey)kp.getPrivate());
			oos.close();
		}
		catch(java.io.FileNotFoundException e)
		{
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
		try
		{
			FileOutputStream fos = new FileOutputStream(filePub);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject("pub");
			oos.writeObject(clientName);
			oos.writeInt(nobytes);
			oos.writeObject((RSAPublicKey)kp.getPublic());
			oos.close();
			return true;
		}
		catch(java.io.FileNotFoundException e)
		{
			//System.out.println("FileNotFoundException");
		}
		catch(Exception e)
		{
			//System.out.println("Exception");
		}
		return false;
	}

}
