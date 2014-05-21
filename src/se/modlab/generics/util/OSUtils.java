package se.modlab.generics.util;

public class OSUtils {

   private static String OS = null;
   
   public static String getOsNameLowerCase()
   {
      if(OS == null) { 
    	  OS = System.getProperty("os.name").toLowerCase();
      }
      return OS;
   }

   public static boolean isWindows() {
      return getOsNameLowerCase().contains("wind");
   }

   public static boolean isLinux() {
	  return getOsNameLowerCase().contains("linux");
   }
   
   public static boolean isHPUX() {
	  return getOsNameLowerCase().replaceAll("-", "").contains("hpux");
   }

   public static boolean isSolaris() {
	   String os = getOsNameLowerCase();
	  return os.contains("solar") || os.contains("sun");
   }
   
   public static boolean isAix() {
	   String os = getOsNameLowerCase();
	  return os.contains("aix");
   }
   
   public static boolean isIrix() {
	   String os = getOsNameLowerCase();
	  return os.contains("irix");
   }
   
   public static boolean isMacOS() {
	  return getOsNameLowerCase().contains("mac");
   }

   public static boolean isCommonUnix() {
	   if(isLinux()) return true;
	   if(isMacOS()) return true;
	   if(isHPUX()) return true;
	   if(isSolaris()) return true;
	   if(isAix()) return true;
	   if(isIrix()) return true;
	   return false;
   }

   public static boolean isProbablyUnix() {
	   return getOsNameLowerCase().contains("x");
   }

   public static boolean isProbablyWindows() {
	   return getOsNameLowerCase().contains("w");
   }

   public static boolean unixMoreLikleyTahnWindows() {
	   return !getOsNameLowerCase().contains("w");
   }

}
