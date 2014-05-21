package se.modlab.generics.util;

import java.io.*;

public class simpleLogg
{

	  private static boolean debugStatus = false;
	  private static String debugFilename = null;
	  
	  public static void setDebugstatus(boolean status) {
		  debugStatus = status;
	  }

	  public static void setDebugFilename(String filename) {
		  debugFilename = filename;
	  }

	  public static void writeOnDebug(String mess)
	  {
	      if(!debugStatus) return;
		  System.out.println("Simplelogg.writeOnDebug("+mess+")");
		  if(debugFilename == null) return;
		  try {
			  FileWriter fw = new FileWriter(debugFilename);
			  fw.write(mess);
			  fw.close();
		  }
		  catch(IOException ioe) {
		  }
	  }

	  private static boolean logStatus = false;
	  private static String logFilename = null;
	  
	  public static void setLogstatus(boolean status) {
		  logStatus = status;
	  }

	  public static void setLogFilename(String filename) {
		  logFilename = filename;
	  }

	  public static void logg(String mess) {
	      if(!logStatus) return;
		  System.out.println("Simplelogg.log("+mess+")");
		  if(mess.contains("ROOT")) {
			  System.out.println("Simplelogg contains ROOT");
			  new Throwable().printStackTrace();
		  }
		  if(logFilename == null) return;
		  try {
			  FileWriter fw = new FileWriter(logFilename);
			  fw.write(mess);
			  fw.close();
		  }
		  catch(IOException ioe) {
		  }
	  }

}
