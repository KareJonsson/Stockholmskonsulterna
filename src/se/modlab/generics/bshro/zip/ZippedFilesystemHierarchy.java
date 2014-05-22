package se.modlab.generics.bshro.zip;

// Skriver detta i iss53
// En rad till i iss53

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import se.modlab.generics.bshro.ifc.HierarchyBranch;
import se.modlab.generics.bshro.ifc.HierarchyLeaf;
import se.modlab.generics.bshro.ifc.HierarchyObject;

public class ZippedFilesystemHierarchy extends HierarchyBranch {
	
	private HierarchyObject getWithCreate(String name, boolean finalLeaf) {
		String path[] = getChopped(name);
		if(path.length == 1) {
			if(finalLeaf) {
				return addLeafChild(path[0]);
			}
			else {
				return addBranchChild(path[0]);
			}
		}
		HierarchyBranch climb = this;
		HierarchyObject next = null;
		for(int i = 0 ; i < path.length - 1 ; i++) {
			next = climb.getChild(path[i]);
			if(next == null) {
				next = climb.addBranchChild(path[i]);
			}
			else {
				if(!next.isBranch()) {
					// ?
				}
			}
			climb = (HierarchyBranch) next;
		}
		if(finalLeaf) {
			return climb.addLeafChild(path[path.length - 1]);
		}
		else {
			return climb.addBranchChild(path[path.length - 1]);
		}
	}
	
	private HierarchyBranch getBranchWithCreate(String name) {
		return (HierarchyBranch) getWithCreate(name, false);
	}

	private HierarchyLeaf getLeafWithCreate(String name) {
		return (HierarchyLeaf) getWithCreate(name, true);
	}

	public ZippedFilesystemHierarchy(String name, InputStream is) {
		super(name, null);
		try {
			unZipIt(is);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void unZipIt(InputStream is) {

		byte[] buffer = new byte[1024];

		try{

			//create output directory is not exists
			/*
			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}
			*/
			
			//get the zip file content
			ZipInputStream zis = (is instanceof ZipInputStream) ? (ZipInputStream) is : new ZipInputStream(is);
			//get the zipped file list entry
			ZipEntry zipentry = null;
			//int i;
			//byte[] buff = new byte[1024];
			try {
				zipentry = zis.getNextEntry();
				if (zipentry == null) {
					zis.close();
					throw new Exception("Theme bundle should be a zip file");
				}
			} catch (IOException e) {
				e.printStackTrace();
				zis.close();
				return;
			}

			while (zipentry != null) {
				String entryName = zipentry.getName();
				if (zipentry.isDirectory()) {
					//System.out.println("Directory "+entryName);
					getBranchWithCreate(entryName);
				} 
				else {
					HierarchyLeaf node = getLeafWithCreate(entryName);
					int size;
					ByteArrayOutputStream boas = new ByteArrayOutputStream();
					
					while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
						boas.write(buffer, 0, size);
					}
					byte array[] = boas.toByteArray();
					//System.out.println("Leaf "+entryName+", size "+array.length);
					node.setByteArray(array);
					//System.out.println("Path "+node.getPath());
				}
				zipentry = zis.getNextEntry();
			}
			zis.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void _main(String args[]) throws Exception {
		new ZippedFilesystemHierarchy("Topnode", new FileInputStream("/Users/karejonsson/tmp/john/modlab.zip"));
		//String[] x = getChopped("a//\\b/../c");
		//System.out.println(show(x));
	}

	public boolean isBranch() {
		return true;
	}

}
