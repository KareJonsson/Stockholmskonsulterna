package se.modlab.generics.bshro.fs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import se.modlab.generics.bshro.ifc.HierarchyBranch;
import se.modlab.generics.bshro.ifc.HierarchyLeaf;
import se.modlab.generics.bshro.ifc.HierarchyObject;

public class DiskFilesystemFile extends HierarchyLeaf {

	private File file = null;

	public DiskFilesystemFile(String name, HierarchyBranch parent, File _file) {
		super(name, parent);
		file = _file;
	}

	public InputStream getInputStream() {
		try {
			return new FileInputStream(file);
		}
		catch(Exception e) {
		}
		return null;
	}
	
	public byte[] getByteArray() {
		int size = 0;
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		InputStream is = getInputStream();
		
		try {
			while ((size = is.read(buffer, 0, buffer.length)) != -1) {
				boas.write(buffer, 0, size);
			}
		} catch (IOException e) {
			if(boas != null) {
				try {
					boas.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return new byte[0];
		}
		if(boas != null) {
			try {
				boas.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return boas.toByteArray();
	}

	
	public static HierarchyLeaf getHierarchyLeaf(String filename) throws Exception {
		if(filename == null) {
			throw new Exception("Null filename provided");
		}
		if(filename.trim().length() == 0) {
			throw new Exception("No filename provided");
		}
		File f = new File(filename);
		if(!f.exists()) {
			throw new Exception("File "+filename+" does not exist.");
		}
		File p = f.getParentFile();
		int placeLastDelimiter = Math.max(filename.lastIndexOf("/"), filename.lastIndexOf("\\"));
		String filesName = filename.substring(placeLastDelimiter);
		DiskFilesystemHierarchy hier = new DiskFilesystemHierarchy("MODLAB", null, p);
		HierarchyObject ho = (HierarchyObject) hier.getChild(filesName);
		if(ho.isBranch()) {
			throw new Exception("File "+filename+" is not a leaf.");
		}
		return (HierarchyLeaf) ho;
	}

	
}
