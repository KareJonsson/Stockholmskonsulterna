package se.modlab.generics.crypto;

import java.io.Serializable;
import java.math.BigInteger;

public class format2_encryptedHolder implements Serializable {
	private static final long serialVersionUID = 565453456235632L;

	public BigInteger senders_modulus;
	public BigInteger receivers_modulus;
	public byte salt_encrypted[];
	public byte infile_contents_encrypted[];
	public byte filename_popupmessage_encrypted[];
	public byte internal_md5_checksum_encrypted[];
	public String errorMessage;
}

