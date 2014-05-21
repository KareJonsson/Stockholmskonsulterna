package se.modlab.generics.bshro.fs;

import java.io.File;
import java.io.InputStream;

import se.modlab.generics.bshro.ifc.HierarchyBranch;
import se.modlab.generics.bshro.ifc.HierarchyLeaf;
import se.modlab.generics.bshro.ifc.HierarchyObject;

public class Main {

	
	public static void print(HierarchyBranch branch, String paths) {
		try {
			HierarchyObject obj = branch.getChild(paths);
			if(obj == null) {
				System.err.println("Fel. Barnet finns inte");
				return;
			}
			if(obj.isBranch()) {
				System.err.println("Fel. Det Šr en katalog");
				return;
			}
			HierarchyLeaf leaf = (HierarchyLeaf) obj;
			InputStream is = leaf.getInputStream();
			int len = -1;
			byte bbuf[] = new byte[1000];
			while((len = is.read(bbuf)) != -1) {
				System.out.println(new String(bbuf, 0, len));
			}
			is.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String arg[]) throws Exception {
		File f = new File("/Users/karejonsson/tmp/john");
		if(!f.exists()) {
			System.err.println("Nej");
			return;
		}
		DiskFilesystemHierarchy hier = new DiskFilesystemHierarchy("MODLAB", null, f);
		//ZippedFilesystemHierarchy hier = new ZippedFilesystemHierarchy("Topnode", new FileInputStream("/Users/karejonsson/tmp/john/modlab.zip"));

		print(hier, "../props/log.properties");
	}
	

}
