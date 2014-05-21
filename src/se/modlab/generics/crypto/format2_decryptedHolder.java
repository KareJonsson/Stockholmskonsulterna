package se.modlab.generics.crypto;

import java.io.Serializable;

public class format2_decryptedHolder implements Serializable {
	private static final long serialVersionUID = 565453456235631L;

	public KeyPairWrapper receiver;
	public PublicKeyWrapper sender;
	public byte infile_contents[];
	public byte filename[];
	public byte popupmessage[];
	public boolean md5_checksum_ok;
	public String errorMessage;
}
