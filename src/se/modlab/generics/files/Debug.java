package se.modlab.generics.files;

//import java.io.*;

public class Debug
{

  public static boolean DEBUG_MODE = true;
  
  public static void println(String str) 
  {
    if(DEBUG_MODE) 
    {
       StackTraceElement ste[] = new Throwable().getStackTrace();
       System.out.println(ste[1].toString()+" "+str);
    }
  }

  public static void quit(String str)
  {
    if(DEBUG_MODE)
    {
      StackTraceElement ste[] = new Throwable().getStackTrace();
      for(int i = 1 ; i < ste.length ; i++) 
      {
        System.out.println(ste[i]);
      }
      System.out.println("Message was "+str);
      System.exit(0);
    }
  }

  public static void main(String[] args)
  {
    Debug.println("sfjhsdfhsfj");
  }

}
