package se.modlab.generics.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;

public class Services2
{

  public static UnknownEncryptedFormatHolder readEncrypted(InputStream is)
  {
    UnknownEncryptedFormatHolder uefh = new UnknownEncryptedFormatHolder();
    try
    {
      ObjectInputStream ois = new ObjectInputStream(is);
      // Formats number
      Object o = ois.readObject();
      if(!(o instanceof Integer))
      {
        uefh.errorMessage = 
          "Unknown format. Only format numbers 2 - 3 are known.\n"+
          "This was entirely different.\n";
        if(o instanceof BigInteger)
        {
          uefh.errorMessage = uefh.errorMessage+
            "It is possible that it is an outdated crypto format.\n";
        }
        return uefh;
      }
      Integer format = (Integer) o;
      uefh.format = format.intValue();
      if(uefh.format == 2)
      {
        uefh.f2 = readEncrypted_f2(ois, format);
        return uefh;
      }
      if(uefh.format == 3)
      {
        uefh.f3 = readEncrypted_f3(ois, format);
        return uefh;
      }
      uefh.errorMessage = 
        "Unknown format. Only numbers 2 - 3 are known.\n"+
        "This was number "+uefh.format;
      return uefh;
    }
    catch(IOException ioe)
    {
      uefh.errorMessage = "Format read not successful\n"+ioe.toString();
    }
    catch(ClassNotFoundException cnfe)
    {
      uefh.errorMessage = "Format read not successful\n"+cnfe.toString();
    }
    catch(ClassCastException cce)
    {
      uefh.errorMessage = "Format read not successful\n"+cce.toString();
    }
    catch(Exception e)
    {
      uefh.errorMessage = "Format read not successful\n"+e.toString();
    }
    return uefh;
  }

  public static format3_encryptedHolder readEncrypted_f3(
    ObjectInputStream ois,
    Integer format)
  {
    format3_encryptedHolder f3eh = new format3_encryptedHolder();
    try
    {
      MD5State md5_checksum = new MD5State();
      //ObjectInputStream ois = new ObjectInputStream(is);

      // Formats number
      //Integer format = (Integer) ois.readObject();
      if(format.intValue() != 3) 
      {
        f3eh.errorMessage = "Wrong crypto format version";
        return f3eh;
      }
      byte by[] = format.toString().getBytes();
      md5_checksum.update(by);

      // Senders E
      f3eh.senders_modulus = (BigInteger) ois.readObject();
      md5_checksum.update(f3eh.senders_modulus.toByteArray());

      // Receivers E
      f3eh.receivers_modulus = (BigInteger) ois.readObject();
      md5_checksum.update(f3eh.receivers_modulus.toByteArray());

      // Salt encrypted
      f3eh.salt_encrypted = (byte[]) ois.readObject();;
      md5_checksum.update(f3eh.salt_encrypted);

      // popupmessage encrypted
      f3eh.popupmessage_encrypted = (byte[]) ois.readObject();
      md5_checksum.update(f3eh.popupmessage_encrypted);

      // Internal md5 checksum encrypted
      f3eh.internal_md5_checksum_encrypted = (byte[]) ois.readObject();
      md5_checksum.update(f3eh.internal_md5_checksum_encrypted);

      // MD5 checksum
      byte md5_checksum_read[] = (byte[]) ois.readObject();
      byte md5_checksum_calculated[] = md5_checksum.digest();

      if(md5_checksum_read.length != 
         md5_checksum_calculated.length)
      {
        f3eh.errorMessage = "Checksum length error";
        return f3eh;
      }

      for(int i = 0 ; i < md5_checksum_read.length ; i++)
      {
        if(md5_checksum_read[i] != md5_checksum_calculated[i])
        {
          f3eh.errorMessage = 
            "Checksum error. The file has been manipulated.";
          return f3eh;
        }
      }

      f3eh.errorMessage = null;
      return f3eh;
    }
    catch(ClassCastException cce)
    {
      f3eh.errorMessage = "Format read not successful\n"+cce.toString();
    }
    catch(IOException ioe)
    {
      f3eh.errorMessage = "Format read not successful\n"+ioe.toString();
    }
    catch(ClassNotFoundException cnfe)
    {
      f3eh.errorMessage = "Format read not successful\n"+cnfe.toString();
    }
    catch(Exception e)
    {
      f3eh.errorMessage = "Format read not successful\n"+e.toString();
    }
    return f3eh;
  }

  public static format2_encryptedHolder readEncrypted_f2(
    ObjectInputStream ois,
    Integer format)
  {
    format2_encryptedHolder f2eh = new format2_encryptedHolder();
    try
    {
      MD5State md5_checksum = new MD5State();
      //ObjectInputStream ois = new ObjectInputStream(is);

      // Formats number
      //Integer format = (Integer) ois.readObject();
      if(format.intValue() != 2) 
      {
        f2eh.errorMessage = "Wrong crypto format version";
        return f2eh;
      }
      byte by[] = format.toString().getBytes();
      md5_checksum.update(by);

      // Senders E
      f2eh.senders_modulus = (BigInteger) ois.readObject();
      md5_checksum.update(f2eh.senders_modulus.toByteArray());

      // Receivers E
      f2eh.receivers_modulus = (BigInteger) ois.readObject();
      md5_checksum.update(f2eh.receivers_modulus.toByteArray());

      // Salt encrypted
      f2eh.salt_encrypted = (byte[]) ois.readObject();;
      md5_checksum.update(f2eh.salt_encrypted);

      // Infile contents encrypted
      f2eh.infile_contents_encrypted = (byte[]) ois.readObject();
      md5_checksum.update(f2eh.infile_contents_encrypted);

      // Filename and popupmessage encrypted
      f2eh.filename_popupmessage_encrypted = (byte[]) ois.readObject();
      md5_checksum.update(f2eh.filename_popupmessage_encrypted);

      // Internal md5 checksum encrypted
      f2eh.internal_md5_checksum_encrypted = (byte[]) ois.readObject();
      md5_checksum.update(f2eh.internal_md5_checksum_encrypted);

      // MD5 checksum
      byte md5_checksum_read[] = (byte[]) ois.readObject();
      byte md5_checksum_calculated[] = md5_checksum.digest();

      if(md5_checksum_read.length != 
         md5_checksum_calculated.length)
      {
        f2eh.errorMessage = "Checksum length error";
        return f2eh;
      }

      for(int i = 0 ; i < md5_checksum_read.length ; i++)
      {
        if(md5_checksum_read[i] != md5_checksum_calculated[i])
        {
          f2eh.errorMessage = 
            "Checksum error. The file has been manipulated.";
          return f2eh;
        }
      }

      f2eh.errorMessage = null;
      return f2eh;
    }
    catch(ClassCastException cce)
    {
      f2eh.errorMessage = "Format read not successful\n"+cce.toString();
    }
    catch(IOException ioe)
    {
      f2eh.errorMessage = "Format read not successful\n"+ioe.toString();
    }
    catch(ClassNotFoundException cnfe)
    {
      f2eh.errorMessage = "Format read not successful\n"+cnfe.toString();
    }
    catch(Exception e)
    {
      f2eh.errorMessage = "Format read not successful\n"+e.toString();
    }
    return f2eh;
  }

  public static boolean writeEncrypted_f2(OutputStream os, 
                                          format2_encryptedHolder f2eh)
  {
    try
    {
      MD5State md5_checksum = new MD5State();
      ObjectOutputStream oos = new ObjectOutputStream(os);
      Integer format = new Integer(2);

      oos.writeObject(format);
      byte by[] = format.toString().getBytes();
      md5_checksum.update(by);

      oos.writeObject(f2eh.senders_modulus);
      md5_checksum.update(f2eh.senders_modulus.toByteArray());

      oos.writeObject(f2eh.receivers_modulus);
      md5_checksum.update(f2eh.receivers_modulus.toByteArray());

      oos.writeObject(f2eh.salt_encrypted);
      md5_checksum.update(f2eh.salt_encrypted);

      oos.writeObject(f2eh.infile_contents_encrypted);
      md5_checksum.update(f2eh.infile_contents_encrypted);

      oos.writeObject(f2eh.filename_popupmessage_encrypted);
      md5_checksum.update(f2eh.filename_popupmessage_encrypted);

      oos.writeObject(f2eh.internal_md5_checksum_encrypted);
      md5_checksum.update(f2eh.internal_md5_checksum_encrypted);

      oos.writeObject(md5_checksum.digest());

      f2eh.errorMessage = null;
      return true; 
    }
    catch(IOException ioe)
    {
      f2eh.errorMessage = "Format read not successful\n"+ioe.toString();
    }
    catch(Exception e)
    {
      f2eh.errorMessage = "Format read not successful\n"+e.toString();
    }
    return false;
  }

  public static boolean writeEncrypted_f3(OutputStream os, 
                                          format3_encryptedHolder f3eh)
  {
    try
    {
      MD5State md5_checksum = new MD5State();
      ObjectOutputStream oos = new ObjectOutputStream(os);
      Integer format = new Integer(3);

      oos.writeObject(format);
      byte by[] = format.toString().getBytes();
      md5_checksum.update(by);

      oos.writeObject(f3eh.senders_modulus);
      md5_checksum.update(f3eh.senders_modulus.toByteArray());

      oos.writeObject(f3eh.receivers_modulus);
      md5_checksum.update(f3eh.receivers_modulus.toByteArray());

      oos.writeObject(f3eh.salt_encrypted);
      md5_checksum.update(f3eh.salt_encrypted);

      oos.writeObject(f3eh.popupmessage_encrypted);
      md5_checksum.update(f3eh.popupmessage_encrypted);

      oos.writeObject(f3eh.internal_md5_checksum_encrypted);
      md5_checksum.update(f3eh.internal_md5_checksum_encrypted);

      oos.writeObject(md5_checksum.digest());

      f3eh.errorMessage = null;
      return true; 
    }
    catch(IOException ioe)
    {
      f3eh.errorMessage = "Format read not successful\n"+ioe.toString();
    }
    catch(Exception e)
    {
      f3eh.errorMessage = "Format read not successful\n"+e.toString();
    }
    return false;
  }


  public static String getRandomTextualHexstyle(int len)
  {
    char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                        'r', 's', 't', 'u', 'v', 'x', 'y', 'z', 'A',
                        'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                        'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'w'};
    byte textual[] = new byte[len]; 
    for(int i = 0 ; i < len ; i++)
    {
      textual[i] = (byte) hexChars[((int) (hexChars.length*Math.random()))];
    }
    return new String(textual);
  }

}