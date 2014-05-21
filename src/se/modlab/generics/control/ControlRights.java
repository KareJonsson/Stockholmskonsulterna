package se.modlab.generics.control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import se.modlab.generics.crypto.KeyPairWrapper;
import se.modlab.generics.crypto.PublicKeyWrapper;
import se.modlab.generics.crypto.Services2;
import se.modlab.generics.crypto.Services3;
import se.modlab.generics.crypto.UnknownEncryptedFormatHolder;
import se.modlab.generics.crypto.format3_encryptedHolder;


public class ControlRights
{

  public static String expireyDate = null;
  public static String person = null;
  public static String company = null;
  public static String vendor = null;
  public static boolean valid = false;
  public static boolean doneAlready = false;
  private static String rootDirectory = null;
  public static String errorMessage = 
    "There has been no error detection";
   
  public static void main(String args[])
  {
    performInitialControl();
    System.out.println("Datum "+expireyDate);
    System.out.println("Pers "+person);
    System.out.println("Firma "+company);
    System.out.println("Saljare "+vendor);
    System.out.println("errorMessage "+errorMessage);
     }
  
  public static String decrypt_license(
		    KeyPairWrapper kpw, PublicKeyWrapper pkw,
		    format3_encryptedHolder f3eh)
		  {
	  try {
	  byte salt[] = Services3.decryptRSA_asym(f3eh.salt_encrypted, kpw.getPrivateKey(), pkw.getPublicKey());
	  byte popupmessage_padded[] =  Services3.decryptMessage(salt, f3eh.popupmessage_encrypted);
	  String lic =  new String(Services3.removeTrailingBytes8AndSome(popupmessage_padded));
	  int idx = lic.indexOf("--------");
	  if(idx != -1) {
		  lic = lic.substring(0, idx);
	  }
	  return lic;
	  }
	  catch(Exception e) {
		  return null;
	  }
}



  public static void performInitialControl()
  {
    if(doneAlready) return;
    doneAlready = true;
    try
    {
      String dir = System.getProperty("user.dir")+File.separator+
                   ".."+File.separator+"certs"+File.separator;
      File misc = new File(dir);
      if(!misc.exists())
      {
        errorMessage = "Cannot find misc catalog. ("+dir+")";
        return;
      }
      rootDirectory = new File(System.getProperty("user.dir")+File.separator+
                   ".."+File.separator).getCanonicalPath();
      FilenameFilter ff = 
        new FilenameFilter()
        {
          public boolean accept(File dir, String name)
          {
            return name.endsWith(".lic.crp");
          }
        };
      File children[] = misc.listFiles(ff);
      if(children.length != 1) 
      {
        errorMessage = "There are "+children.length+" license files. Must be exactly one.";
        return;
      }
      KeyPairWrapper kpw = /*TmpRSACipher*/Services3.getKeyPair(new ByteArrayInputStream(SsimInternal.key), "No filename");
      PublicKeyWrapper pkw = /*TmpRSACipher*/Services3.getPublicKey(new ByteArrayInputStream(Issuer.key), "No filename");
      //byte licenseFileEncrypted[] = Services.getCleartextFromFile(new FileInputStream(children[0]));
      /*
      byte file_decrypted[] = Services3.decrypt(kpw, pkw, licenseFileEncrypted);
      if(file_decrypted == null)
      {
        errorMessage = "Decrypting the licence files content failed.";
        return;
      }
      String licensFileDecrypted = new String(file_decrypted);
      */
      
      UnknownEncryptedFormatHolder uefh = null;
	    try
	    {
	      uefh = Services2.readEncrypted(new FileInputStream(children[0]));
	    }
	    catch(FileNotFoundException fnfe)
	    {
	      //DefaultIdeMenuHandler.error(null, "File "+f.getAbsolutePath()+" not found",
	      //                 "Decryption unsuccessful");
	      return;
	    }
      
      String licensFileDecrypted = decrypt_license(
  		      kpw,
  		      pkw,
  		      uefh.f3);
      //System.out.println("License files contents \n"+licensFileDecrypted);
      LicenseInformationNode top = ControlLicenceParser.getParsedStructure(licensFileDecrypted);
      if(top == null)
      {
        errorMessage = "Parsing the decrypted content failed.";
        return;
      }
      expireyDate = top.getChildAt(0).getContents();
      person = top.getChildAt(1).getContents();
      company = top.getChildAt(2).getContents();
      vendor = pkw.getClientName();
/*
      System.out.println("Datum "+expireyDate);
      System.out.println("Pers "+person);
      System.out.println("Firma "+company);
      System.out.println("Saljare "+vendor);
      */
      valid = true;
    
    }
    catch(NullPointerException npe)
    {
      errorMessage = 
        "Something unexpected happened when verifying license.\n"+
        "Exception:\n"+npe;
      npe.printStackTrace();
      return;
    }
    catch(Throwable t)
    {
      errorMessage = "Exception: "+t;
      t.printStackTrace();
     return;
    }
    valid = true;
  }

  public static String getRootDirectory()
  {
    return rootDirectory;
  }

}