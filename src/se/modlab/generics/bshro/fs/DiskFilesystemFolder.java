package se.modlab.generics.bshro.fs;

import java.io.File;

import se.modlab.generics.bshro.ifc.HierarchyBranch;
import se.modlab.generics.bshro.ifc.HierarchyObject;

public class DiskFilesystemFolder extends HierarchyBranch {

	private File file = null;

	public DiskFilesystemFolder(String name, HierarchyBranch parent, File _file) {
		super(name, parent);
		file = _file;
	}
	
	public HierarchyObject getChild(String name) {
		//System.out.println("DiskFilesystemFolder.getChild("+name+")");
		String path[] = getChopped(name);
		if(path.length == 1) {
			if(path[0].compareTo("..") == 0) {
				HierarchyBranch parent = getParent();
				if(parent != null) {
					return parent;
				}
				return new DiskFilesystemFolder("..", null, new File(file, ".."));
			}
			HierarchyObject out = hierarchy.get(path[0]);
			if(out != null) {
				return out;
			}
			File f = new File(file, path[0]);
			if(!f.exists()) {
				return null;
			}
			if(f.isDirectory()) {
				out = new DiskFilesystemFolder(path[0], this, f);
			}
			else {
				out = new DiskFilesystemFile(path[0], this, f);
			}
			hierarchy.put(path[0], out);
			return out;
		}
		return getChild(path);
	}
	
	public String[] getChildrensNames() {
		File subs[] = file.listFiles();
		String out[] = new String[subs.length];
		for(int i = 0 ; i < subs.length ; i++) {
			out[i] = subs[i].getName();
		}
		return out;
	}
	
	public String getPath() {
		
		if(getParent() != null) {
			return super.getPath();
		}
		try {
			return file.getCanonicalPath();
		}
		catch(Exception e) {
			return file.getAbsolutePath();
		}
	}

}
