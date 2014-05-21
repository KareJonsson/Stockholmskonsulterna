package se.modlab.generics.files;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;


public class FileCollector 
{

  private String filename;
  private String fullFilename;
  private String filecontents;
  private File f;
  private boolean ok = true;
  private Exception e = null;

  public FileCollector(String _filename)
  {
    filename = _filename;
    fullFilename = _filename;
    try
    {
      f = new File(filename);
      Reader in = new FileReader(f);
      char[] buff = new char[4096];
      int nch;
      StringBuffer sb = new StringBuffer();
      while ((nch = in.read(buff, 0, buff.length)) != -1) 
      {
        sb = sb.append(new String(buff, 0, nch));
      }
      filecontents = sb.toString();
    }
    catch(Exception e)
    {
      ok = false;
      this.e = e;
    }
    int i = filename.lastIndexOf(File.separator);
    if(i != -1)
    {
      filename = filename.substring(i+1);
    }
  }

  public FileCollector(String _filename, String _filecontents)
  {
    filename = _filename;
    fullFilename = _filename;
    filecontents = _filecontents;
    try
    {
      f = new File(filename);
    }
    catch(Exception e)
    {
      ok = false;
      this.e = e;
    }
    int i = filename.lastIndexOf(File.separator);
    if(i != -1)
    {
      filename = filename.substring(i+1);
    }
  }

  public String getFilename()
  {
    //return fullFilename;
    return filename;
  }
  
  public String getFullFilename()
  {
    return fullFilename;
  }
  
  public String getFilecontents()
  {
    return filecontents;
  }

  public boolean getOk()
  {
    return ok;
  }

  public Exception getException()
  {
    return e;
  }

  public File getFile()
  {
    return f;
  }

}
