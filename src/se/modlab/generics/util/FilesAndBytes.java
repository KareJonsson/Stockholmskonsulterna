package se.modlab.generics.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import se.modlab.generics.crypto.Services;

public class FilesAndBytes {

	public static byte[] toByteArray(int value) {
		return  ByteBuffer.allocate(4).putInt(value).array();
	}

	public static byte[] toByteArray(long value) {
		return  ByteBuffer.allocate(8).putLong(value).array();
	}

	public static int intFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static long longFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	public static String getLastSectionOfFilename(String filename) {
		int idxForw = filename.lastIndexOf("/");
		int idxBackw = filename.lastIndexOf("\\");
		int max = Math.max(idxForw, idxBackw)+1;
		return filename.substring(max);
	}

	public static byte[] byteArrayFromFile(File f) {
		return byteArrayFromFile(f, getLastSectionOfFilename(f.getName()).getBytes());
	}
	
	public static byte[] byteArrayFromFile(File f, byte[] filenamesLastSection) {
		//byte[] filenamesLastSection = getLastSectionOfFilename(f.getName()).getBytes();
		byte[] filesContents = null;
		try {
			filesContents = Services.getCleartextFromFile(new FileInputStream(f));
		}
		catch(Exception e) {
			return null;
		}
		int size = 4 + filenamesLastSection.length + 4 + filesContents.length;
		byte[] out = new byte[size];
		System.arraycopy(toByteArray(filenamesLastSection.length), 0, out, 0, 4);
		System.arraycopy(filenamesLastSection, 0, out, 4, filenamesLastSection.length);
		int place = filenamesLastSection.length + 4;
		long length = f.length();
		//System.out.println("FilesAndBytes.byteArrayFromFile length = "+length);
		System.arraycopy(toByteArray((int)length), 0, out, place, 4);
		place += 4;
		System.arraycopy(filesContents, 0, out, place, filesContents.length);
		return out;
	}

	public static byte[] getFilenameFromArray(byte[] array) {
		return getFilenameFromMeta(getFilemetaFromArray(array));
	}

	public static int getFilesizeFromArray(byte[] array) {
		return getFilesizeFromMeta(getFilemetaFromArray(array));
	}

	public static byte[] getFilemetaFromArray(byte[] array) {
		byte[] buf = new byte[4];
		System.arraycopy(array, 0, buf, 0, 4);
		int namesize = intFromByteArray(buf);
		byte[] out = new byte[namesize+4];
		System.arraycopy(array, 4, out, 0, namesize+4);
		return out;
	}

	public static byte[] getFilenameFromMeta(byte[] array) {
		byte[] buf = new byte[array.length-4];
		System.arraycopy(array, 0, buf, 0, array.length-4);
		return buf;
	}

	public static int getFilesizeFromMeta(byte[] array) {
		byte[] buf = new byte[4];
		System.arraycopy(array, array.length-4, buf, 0, 4);
		return intFromByteArray(buf);
	}

	public static byte[] getFilecontentsFromArray(byte[] array) {
		byte[] buf = new byte[4];
		System.arraycopy(array, 0, buf, 0, 4);
		int namesize = intFromByteArray(buf);
		byte[] out = new byte[array.length-8-namesize];
		System.arraycopy(array, 8 + namesize, out, 0, array.length-8-namesize);
		return out;
	}

	public static byte[] readName(InputStream is) throws IOException {
		byte[] meta = readMeta(is);
		byte[] out = new byte[meta.length -4];
		System.arraycopy(meta, 0, out, 0, meta.length - 4);
		return out;
	}
	
	private static byte[] readStream(InputStream is, int len) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int totread = 0;
		while(totread < len) {
			int toread = len - totread;
			//System.out.println("FilesAndBytes.readStream(Stream, int "+len+") wants to read: toread = "+toread);
			byte[] buf = new byte[len - totread];
			int b = is.read(buf);
			if(b != toread) {
				//System.out.println("FilesAndBytes.readStream(Stream, int "+len+") b != toread, b = "+b+", toread = "+toread);
			}
			if(b != -1) {
				baos.write(buf, 0, b);
				totread += b;
			}
		}
		return baos.toByteArray();
	}

	public static byte[] readMeta(InputStream is) throws IOException {
		byte[] intbuf = readStream(is, 4);//is.read(intbuf);
		int namelen = FilesAndBytes.intFromByteArray(intbuf);
		return readStream(is, namelen+4);
		/*
		byte[] out = new byte[namelen+4];
		int actualMetaLen = is.read(out);
		if(actualMetaLen != namelen+4) {
			System.out.println("FilesAndBytes.readMeta(Stream) actualMetaLen != namelen+4, actualMetaLen = "+actualMetaLen+", namelen = "+namelen);
			return null;
		}
		return out;
		*/
	}

}
