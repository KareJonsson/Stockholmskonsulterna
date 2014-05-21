package se.modlab.generics.crypto;

import java.io.Serializable;
import java.math.BigInteger;

public class format3_encryptedHolder implements Serializable {
	private static final long serialVersionUID = 565453456235635L;

	public BigInteger senders_modulus;
	public BigInteger receivers_modulus;
	public byte salt_encrypted[];
	public byte popupmessage_encrypted[];
	public byte internal_md5_checksum_encrypted[];
	public String errorMessage;
}
