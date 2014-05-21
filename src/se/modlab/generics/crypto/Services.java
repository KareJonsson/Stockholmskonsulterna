package se.modlab.generics.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;


public class Services
{

	public static byte[] addTrailingBytes(byte message[]) {
	  byte trail = (byte) (8 - message.length % 8);
	  if(trail == 0) {
	    trail = 8;
	  }
	  //System.out.println("add trail, trail = "+trail);
	  byte out[] = new byte[message.length + trail];
	  System.arraycopy(message, 0, out, 0, message.length);
	  /*for(int i =  message.length ; i < message.length + trail - 1; i++)
	  {
	    out[i] = (byte) (256*Math.random());
	  }*/
	  out[out.length - 1] = trail;
	  //System.out.println("add trail. Last = "+message[message.length - 1]);
	  return out;
	}

  public static byte[] addTrailingBytes8(byte message[], byte pad)
  {
    int trail = message.length % 8;
    if(trail == 0) return message;
    int pad_trail = 8-trail;
    byte out[] = new byte[message.length + pad_trail];
    System.arraycopy(message, 0, out, 0, message.length);
    for(int i =  message.length ; i < message.length + pad_trail ; i++)
    {
      out[i] = pad;
    }
    return out;
  }

  public static byte[] removeTrailingBytes(byte message[], int bytes)
  {
    if(bytes == 0) return message;
    byte out[] = new byte[message.length - bytes];
    System.arraycopy(message, 0, out, 0, message.length - bytes);
    return out;
  }

  public static boolean putCleartextInFile(byte contents[], OutputStream os)
  { 
    try
    {
      os.write(contents);
      os.close();
      return true;
    }  
    catch(IOException e)
    {
    }
    return false;
  }

  public static boolean putCleartextInFile(byte contents[], String filename)
  { 
    try
    { 
      return putCleartextInFile(contents, new FileOutputStream(filename));
    }  
    catch(IOException e)
    {
    }
    return false;
  }

  public static byte[] getCleartextFromFile(InputStream is)
  { 
    try
    { 
      final int len = 4096;
      byte[] buff = new byte[len];
      int nbt = 0;
      int accum = nbt;
      Vector<byte[]> v = new Vector<byte[]>();
      //long before = System.currentTimeMillis();
      //long before_keep = before;
      //long after = 0;
      while((nbt = is.read(buff, 0, buff.length)) != -1) 
      {
    	//after = System.currentTimeMillis();
    	//System.out.println("Services.getCleartextFromFile: Read "+nbt+" bytes in "+(after-before)+" milliseconds.");
        byte tmp[] = new byte[nbt];
        System.arraycopy(buff, 0, tmp, 0, nbt);
        v.addElement(tmp);
        accum += nbt;
        //before = after;
      }
      is.close();
      byte out[] = new byte[accum];
      //System.out.println("Services.getCleartextFromFile: Read "+accum+" bytes in "+(after-before_keep)+" milliseconds.");
      nbt = 0;
      //System.out.println("Lne = "+v.size());
      for(int i = 0 ; i < v.size() ; i++)
      {
        byte tmp[] = (byte[]) v.elementAt(i);
        System.arraycopy(tmp, 0, out, nbt, tmp.length);
        nbt += tmp.length;
      }
      return out;
    }  
    catch(IOException e)
    {
    }
    return null;
  }

  public static byte[] getCleartextFromFile(String filename)
  { 
    try
    {
      return getCleartextFromFile(new FileInputStream(filename));
    }
    catch(FileNotFoundException e)
    {
    } 
    return null;
  }

  public static byte[] getCleartextFromFile(File f)
  { 
    try
    {
      return getCleartextFromFile(new FileInputStream(f));
    }
    catch(FileNotFoundException e)
    {
    } 
    return null;
  }

  public static void byte2hex(byte b, StringBuffer buf) 
  {
    char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    int high = ((b & 0xf0) >> 4);
    int low = (b & 0x0f);
    buf.append("(byte)0x");
    buf.append(hexChars[high]);
    buf.append(hexChars[low]);
  }

  public static void byte2denseHex(byte b, StringBuffer buf) 
  {
    char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    int high = ((b & 0xf0) >> 4);
    int low = (b & 0x0f);
    buf.append(hexChars[high]);
    buf.append(hexChars[low]);
  }

  /*
   * Converts a byte array to hex string
   */
  public static String toHexString(byte[] block) 
  {
    StringBuffer buf = new StringBuffer("    { ");
    int lenminusone = block.length-1;
 
    for(int i = 0; i < lenminusone; i++) 
    {
      if((i % 4) == 0) buf.append("\n      ");
      byte2hex(block[i], buf);
      buf.append(", ");
    } 
    byte2hex(block[lenminusone], buf);
    buf.append("\n    }");
    return buf.toString();
  }

  /*
   * Converts a byte array to dense hex string
   */
  public static String toDenseHexString(byte[] block) 
  {
    StringBuffer buf = new StringBuffer();
    int lenminusone = block.length-1;
 
    for(int i = 0; i < lenminusone; i++) {
    	byte2denseHex(block[i], buf);
    } 
    byte2denseHex(block[lenminusone], buf);
    return buf.toString();
  }

/*
  public static void main(String args[])
  {
    System.out.println("services - main - memory");
    KeyPairWrapper kpw_kare = RSACipher.getKeyPair("kare.pair");
    if(kpw_kare == null) System.out.println("services - kare pair");
    KeyPairWrapper kpw_tv = RSACipher.getKeyPair("tekniskaverken.pair");
    if(kpw_tv == null) System.out.println("services - tv pair");
    PublicKeyWrapper pkw_kare = RSACipher.getPublicKey("kare.pub");
    if(pkw_kare == null) System.out.println("services - kare pub");
    PublicKeyWrapper pkw_tv = RSACipher.getPublicKey("tekniskaverken.pub");
    if(pkw_tv == null) System.out.println("services - tv pub");

    String message = "23456781234567812345678abcdefgh";
    byte message_bytes[] = message.getBytes();

    byte message_encrypted[] = encrypt(kpw_kare, pkw_tv, message_bytes);

    byte message_decrypted[] = decrypt(kpw_tv, pkw_kare, message_encrypted);
    
    System.out.println("IN: '"+new String(message_bytes)+"'");
    System.out.println("CPT: '"+new String(message_encrypted)+"'");
    System.out.println("UT: '"+new String(message_decrypted)+"'");
  }
 */
/*
  public static void main(String args[])
  {
    System.out.println("services - main - file");
    KeyPairWrapper kpw_kare = RSACipher.getKeyPair("kare.pair");
    if(kpw_kare == null) System.out.println("services - kare pair");
    KeyPairWrapper kpw_tv = RSACipher.getKeyPair("tekniskaverken.pair");
    if(kpw_tv == null) System.out.println("services - tv pair");
    PublicKeyWrapper pkw_kare = RSACipher.getPublicKey("kare.pub");
    if(pkw_kare == null) System.out.println("services - kare pub");
    PublicKeyWrapper pkw_tv = RSACipher.getPublicKey("tekniskaverken.pub");
    if(pkw_tv == null) System.out.println("services - tv pub");

    String message = "Kares exemple med grisig text ^��~";
    byte message_bytes[] = message.getBytes();

    if(!encrypt_putInFile(kpw_kare, pkw_tv, message_bytes, "file.crp"))
    {
      System.out.println("Encrypt failure");
      System.exit(-1);
    }

    byte message_decrypted[] = decrypt_getFromFile(kpw_tv, pkw_kare, "file.crp");
    if(message_decrypted == null)
    {
      System.out.println("Decrypt failure");
      System.exit(-1);
    }
        
    System.out.println("IN: '"+new String(message_bytes)+"'");
    System.out.println("UT: '"+new String(message_decrypted)+"'");
  }
 */
/*
  public static void main(String args[])
  {
    System.out.println("services - main - file ONLY UP");
    KeyPairWrapper kpw_tv = RSACipher.getKeyPair("tekniskaverken.pair");
    if(kpw_tv == null) System.out.println("services - tv pair");
    PublicKeyWrapper pkw_kare = RSACipher.getPublicKey("kare.pub");
    if(pkw_kare == null) System.out.println("services - kare pub");

    byte message_decrypted[] = decrypt_getFromFile(kpw_tv, pkw_kare, "file.crp");
    if(message_decrypted == null)
    {
      System.out.println("Decrypt failure");
      System.exit(-1);
    }
        
    System.out.println("UT: '"+new String(message_decrypted)+"'");
  }
 */
/*
  public static void main(String args[])
  {
    System.out.println("services - main - encrypt file");
    KeyPairWrapper kpw_tv = RSACipher.getKeyPair("tekniskaverken.pair");
    if(kpw_tv == null) System.out.println("services - tv pair");
    PublicKeyWrapper pkw_kare = RSACipher.getPublicKey("kare.pub");
    if(pkw_kare == null) System.out.println("services - kare pub");
 
    String filename = "RSACipher.java";

    byte filecontents[] = getCleartextFromFile(filename);
    System.out.println("Contents:\n"+new String(filecontents));

    boolean b = encrypt_putInFile(kpw_tv, pkw_kare, filecontents, 
                                  filename+".crp");
    if(!b)
    {
      System.out.println("Encrypt failure");
      System.exit(-1);
    }
        
  }
 */
  /*
  public static void main(String args[])
  {
    System.out.println("services - main - decrypt file");
    KeyPairWrapper kpw_kare = RSACipher.getKeyPair("kare.pair");
    if(kpw_kare == null) System.out.println("services - kare pair");
    PublicKeyWrapper pkw_tv = RSACipher.getPublicKey("tekniskaverken.pub");
    if(pkw_tv == null) System.out.println("services - tv pub");
 
    String filename = "RSACipher.java.crp";
    byte message_decrypted[] = decrypt_getFromFile(kpw_kare, pkw_tv, filename);
    if(message_decrypted == null)
    {
      System.out.println("Decrypt failure");
      System.exit(-1);
    }

    boolean b = putCleartextInFile(message_decrypted, "tmp.java");
    if(!b)
    {
      System.out.println("Decrypt failure");
      System.exit(-1);
    }
  }
*/
}