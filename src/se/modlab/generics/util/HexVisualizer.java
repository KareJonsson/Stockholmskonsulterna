package se.modlab.generics.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;

public class HexVisualizer {

	private static final String validChars = "0123456789ABCDEF";
	
	public static String visualizeOne(char someChar) {
		int first = (((int) someChar + 256) / 16) % 16;
		int second = (int) someChar % 16;
		String out = "0x"+validChars.substring(first, first+1)+validChars.substring(second, second+1);
		if(someChar < (char) 32) {
			if(someChar == (char) 9) return out;
			if(someChar == (char) 10) return out;
			if(someChar == (char) 13) return out;
			return "!"+out;
		}
		return out;
	}
	
	public static String visualizeOneDense(char someChar) {
		int first = (((int) someChar + 256) / 16) % 16;
		int second = (int) someChar % 16;
		return validChars.substring(first, first+1)+validChars.substring(second, second+1);
	}
	
	public static String visualizeOne(byte someByte) {
		//return ""+((int) someByte); 
		int first = (((int)someByte + 256) / 16) % 16;
		int second = ((int)someByte +256) % 16;
		String out = "0x"+validChars.substring(first, first+1)+validChars.substring(second, second+1);
		if(((int)someByte) < 32) {
			if(((int)someByte) == 9) return out;
			if(((int)someByte) == 10) return out;
			if(((int)someByte) == 13) return out;
			return "!"+out;
		}
		return out; 
	}
	
	public static String visualizeOneDense(byte someByte) {
		//return ""+((int) someByte); 
		int first = (((int)someByte + 256) / 16) % 16;
		int second = ((int)someByte +256) % 16;
		return validChars.substring(first, first+1)+validChars.substring(second, second+1);
	}
	
	public static String visualize(String m) {
		StringBuffer sb = new StringBuffer();
		if(m == null) return "";
		if(m.length() == 0) return "";
		sb.append(visualizeOne(m.charAt(0)));
		for(int i = 1 ; i < m.length() ; i++) {
			sb.append(","+visualizeOne(m.charAt(i)));
		}
		return "{"+sb.toString()+"}";
	}
	
	public static String visualizeDense(String m) {
		StringBuffer sb = new StringBuffer();
		if(m == null) return "";
		if(m.length() == 0) return "";
		for(int i = 0 ; i < m.length() ; i++) {
			sb.append(visualizeOneDense(m.charAt(i)));
		}
		return sb.toString();
	}
	
	public static boolean isUtf8Formed(byte m[]) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(m));
		try {
			dis.readUTF();
			return true;
		}
		catch(Exception e) {
		}
		return false;
	}

	public static boolean isUtf8Formed(String m) {
		return isUtf8Formed(m.getBytes());
	}

	public static String visualize(byte m[]) {
		StringBuffer sb = new StringBuffer();
		if(m == null) return "<NULL>";
		if(m.length == 0) return "<Zero length>";
		sb.append(visualizeOne(m[0]));
		for(int i = 1 ; i < m.length ; i++) {
			sb.append(","+visualizeOne(m[i]));
		}
		return "{"+sb.toString()+"}";
	}
	
	public static String visualizeDense(byte m[]) {
		StringBuffer sb = new StringBuffer();
		if(m == null) return "<NULL>";
		if(m.length == 0) return "<Zero length>";
		for(int i = 0 ; i < m.length ; i++) {
			sb.append(visualizeOneDense(m[i]));
		}
		return sb.toString();
	}
	
	//public static void main(String args[]) {
	//	System.out.println("Test: "+visualize("ABCDEF"));
	//}
	
	/*
	private static byte[] formForReciept(byte reciept[]) {
    	Vector<Byte> tmp = new Vector<Byte>();
    	for(int i = 0 ; i < reciept.length ; i++) {
    		if((reciept[i] == 0x0A) && (i != 0) && (i != reciept.length-1) && (reciept[i-1] != (byte)0x0D)) {
    			tmp.addElement(new Byte((byte)0x0D));
    		}
    		tmp.addElement(new Byte(reciept[i]));
    	}
    	byte out[] = new byte[tmp.size()];
    	for(int i = 0 ; i < out.length ; i++) {
    		out[i] = tmp.elementAt(i).byteValue();
    	}
    	return out;
	}
    */
	/*
    private static void write(byte mess[], String filename) {
    	FileOutputStream fos;
    	try {
    		fos = new FileOutputStream(filename);
        	fos.write(mess);
        	fos.close();
    	}
    	catch(IOException fnfe) {
    		System.out.println("File not found "+fnfe.getMessage());
    		return;
    	}
    }
    */
	
    /**
     * Converts an array of bytes into a dense string.
     * @param bytes Array to convert.
     * @return
     */
    public static String bytesToString(byte bytes[]) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            if  (b < 0) b+=256;
            String c = Integer.toHexString(b);
            if (c.length() == 1) c = "0"+c;
            result+=c;
        }
        return result;
    } 


    public static void main(String args[]) {
    	/*
    	byte mess1[] = {(byte) 0x7a, 0x7a, 0x7a, 0x0a, 0x79, 0x79, 0x79 };
    	byte mess2[] = formForReciept(mess1);
    	write(mess1, "file1.txt");
    	write(mess2, "file2.txt");
    	*/
    	byte buf[] = new byte[30];
    	for(int i = 0 ; i < buf.length ; i++) {
    		buf[i] = (byte) ((int) 256*Math.random());
    	}
    	String s = new String(buf);
    	boolean b1 = isUtf8Formed(s);
    	System.out.println("Formed1 = "+b1);
    	byte buf2[] = null;
    	try {
        	buf2 = s.getBytes("UTF-8");
    	}
    	catch(UnsupportedEncodingException uee) {
    		System.out.println("Got UnsupportedEncodingException: "+uee.getMessage());
    		System.exit(-1);
    	}
    	boolean b2 = isUtf8Formed(buf2);
    	System.out.println("Formed2 = "+b2);
    	//System.out.println("X:"+visualizeDense("K�re..."));
    }

}
