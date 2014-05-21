package se.modlab.generics.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Helper {
	
	public static byte[] calc(byte b[]) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(b, 0, b.length);
			return md.digest();
		} 
		catch (NoSuchAlgorithmException e) {
		}
		catch (Exception e) {
		}
		return null;
	}

	public static byte[] calc(String s) {
		return calc(s.getBytes());
	}

	public static byte[] calc(File f) {
		MessageDigest md;
		try {
			FileInputStream fis = new FileInputStream(f);
			byte buf[] = new byte[4000];
			int len = -1;
			md = MessageDigest.getInstance("MD5");
			while((len = fis.read(buf)) > 0) {
				md.update(buf, 0, len);
			}
			return md.digest();
		} 
		catch (NoSuchAlgorithmException e) {
		}
		catch (Exception e) {
		}
		return null;
	}
	
	public static boolean equal(byte b1[], byte b2[]) {
		if(b1 == b2) return true;
		if(b1 == null) return false;
		if(b2 == null) return false;
		if(b1.length != b2.length) return false;
		for(int i = 0 ; i < b1.length ; i++)  {
			if(b1[i] != b2[i]) return false;
		}
		return true;
	}

}
