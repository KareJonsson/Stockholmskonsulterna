package se.modlab.generics.crypto;

import java.io.Serializable;

public class format3_cleartextHolder implements Serializable {
	private static final long serialVersionUID = 565453456235633L;

	public KeyPairWrapper sender;
	public PublicKeyWrapper receiver;
	public byte popupmessage[];
	public boolean md5_checksum_ok;
	public String errorMessage;
}
