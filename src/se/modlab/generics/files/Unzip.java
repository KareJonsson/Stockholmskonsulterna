package se.modlab.generics.files;

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipException;

public class Unzip {

     public static ZipFile zf;

     public static final int EOF = -1;

     public static void main( String argv[] ) {

          Enumeration<?> _enum;

          if( argv.length == 1 ) {
              try {
                   zf = new ZipFile( argv[0] );
                   _enum = zf.entries();

                   while( _enum.hasMoreElements() ) {

                         ZipEntry target = (ZipEntry)_enum.nextElement();
                         System.out.print( target.getName() + " ." );
                         saveEntry( target );
                         System.out.println( ". unpacked" );
                   }
              }
              catch( FileNotFoundException e ){
                     System.out.println( "zipfile not found" );
              }
              catch( ZipException e ){
                     System.out.println( "zip error..." );
              }
              catch( IOException e ){
                     System.out.println( "IO error..." );
              }
         }
         else {
               System.out.println( "Usage:java UnZip zipfile" );
         }
   }

     public static void unzipit( File f ) {

          Enumeration<?> _enum;

              try {
                   zf = new ZipFile( f );
                   _enum = zf.entries();

                   while( _enum.hasMoreElements() ) {

                         ZipEntry target = (ZipEntry)_enum.nextElement();
                         //System.out.print( target.getName() + " ." );
                         saveEntry( target );
                         //System.out.println( ". unpacked" );
                   }
              }
              catch( FileNotFoundException e ){
                     System.out.println( "zipfile not found" );
              }
              catch( ZipException e ){
                     System.out.println( "zip error..." );
              }
              catch( IOException e ){
                     System.out.println( "IO error..." );
              }
   }

     public static void saveEntry( ZipEntry target )
                                   throws ZipException,IOException {

            try {
                 File file = new File( target.getName() );
                 if( target.isDirectory() ) {
                     file.mkdirs(); 
                 }
                 else {
                     InputStream is = zf.getInputStream( target );
                     BufferedInputStream bis = new BufferedInputStream( is );
                     File dir = new File( file.getParent() );
                     dir.mkdirs();  
                     FileOutputStream fos = new FileOutputStream( file );
                     BufferedOutputStream bos = new BufferedOutputStream( fos );

                     int c;
                     while( ( c = bis.read() ) != EOF ) {
                          bos.write( (byte)c );
                     }
                     bos.close();
                     fos.close();
                 }
            }
            catch( ZipException e ){
                   throw e;
            }
            catch( IOException e ){
                   throw e;
            }
      }
}
