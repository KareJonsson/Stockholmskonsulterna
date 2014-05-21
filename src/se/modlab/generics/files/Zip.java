package se.modlab.generics.files;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Zip {

     protected static final int EOF = -1;

      protected File dir;

      protected String parentName;

      Zip( String parentName, File dir ) {

          this.parentName = parentName;
          this.dir = dir;
      }  

     public static void main( String argv[] ) {

          if( argv.length != 1 ) {
               System.err.println( "Usage 1:java Zip filename" );
               System.err.println( "Usage 2:java Zip dirname" );
               System.exit(0);
          }
          else {
               try {
                    File file = new File( argv[0] );
                    File zipFile = new File( argv[0] + ".zip" );
                    ZipOutputStream zos
                        = new ZipOutputStream(
                               new FileOutputStream( zipFile ) );

                    if( file.isFile() ) {

                          addTargetFile( zos, file );
                    }
                    else if( file.isDirectory() ) {

                         Zip origin = new Zip( argv[0], file );
                         Vector<?> vec = origin.pathNames();
                         Enumeration<?> enu = vec.elements();
                         while( enu.hasMoreElements() ) {
                              File f = new File(
                                  (String)enu.nextElement() );
                              addTargetFile( zos, f );
                         }
                    }
                    zos.close();
               }
               catch( FileNotFoundException e ){
                      System.err.println( "file not found" );
               }
               catch( ZipException e ){
                      System.err.println( "Zip error..." );
               }
               catch( IOException e ){
                      System.err.println( "IO error..." );
               }
          }
     }

     public static void zipit(File toZip, File toCreate) {

               try {
                    File file = toZip;
                    //File zipFile = new File( argv[0] + ".zip" );
                    ZipOutputStream zos
                        = new ZipOutputStream(
                               new FileOutputStream( toCreate ) );

                    if( file.isFile() ) {

                          addTargetFile( zos, file );
                    }
                    else if( file.isDirectory() ) {

                         Zip origin = new Zip( file.getName(), file );
                         Vector<?> vec = origin.pathNames();
                         Enumeration<?> enu = vec.elements();
                         while( enu.hasMoreElements() ) {
                              File f = new File(
                                  (String)enu.nextElement() );
                              addTargetFile( zos, f );
                         }
                    }
                    zos.close();
               }
               catch( FileNotFoundException e ){
                      System.err.println( "file not found" );
               }
               catch( ZipException e ){
                      System.err.println( "Zip error..." );
               }
               catch( IOException e ){
                      System.err.println( "IO error..." );
               }
     }

      protected Vector<String> pathNames()
                throws FileNotFoundException, IOException {

          try {

               String[] list = dir.list();
               File[] files = new File[list.length];
               Vector<String> vec = new Vector<String>();
               for( int i=0; i<list.length; i++ ) {
 
                    String pathName = parentName
                           + File.separator + list[i];
                    files[i] = new File( pathName );
                    if( files[i].isFile() )
                        vec.addElement( files[i].getPath() );
               } 
 
               for( int i=0; i<list.length; i++ ) {
 
                  if( files[i].isDirectory() ) {
 
                        String name = parentName
                           + File.separator + list[i];
                        Zip sub = new Zip( name, files[i] );
                        Vector<String> sv = sub.pathNames();
                        Enumeration<String> enu = sv.elements();
                        while( enu.hasMoreElements() )
                           vec.addElement( enu.nextElement() );
                    }
               }
               return vec;
          }
          catch( FileNotFoundException e ){
                 throw e;
          }
          catch( IOException e ){
                 throw e;
          }
     }


     public static void addTargetFile(
                         ZipOutputStream zos, File file )
                         throws FileNotFoundException,
                                ZipException,IOException {
          try {
               BufferedInputStream bis
                     = new BufferedInputStream(
                           new FileInputStream( file ) );

               ZipEntry target = new ZipEntry( file.getPath() );
               zos.putNextEntry( target );  

               byte buf[] = new byte[1024];
               int count;
               while( ( count = bis.read( buf, 0, 1024 ) ) != EOF ) {
                    zos.write( buf, 0, count );
               }

               bis.close();
               zos.closeEntry();  
          }
          catch( FileNotFoundException e ){
                 throw e;
          }
          catch( ZipException e ){
                 throw e;
          }
          catch( IOException e ){
                 throw e;
          }
     }
}
