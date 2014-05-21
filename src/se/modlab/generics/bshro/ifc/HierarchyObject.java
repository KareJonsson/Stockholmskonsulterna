package se.modlab.generics.bshro.ifc;

import java.io.File;

import se.modlab.generics.bshro.fs.DiskFilesystemFile;
import se.modlab.generics.bshro.fs.DiskFilesystemHierarchy;

public abstract class HierarchyObject {
	
	private String name = null;
	private HierarchyBranch parent = null;
	
	public HierarchyObject(String _name, HierarchyBranch _parent) {
		name = _name;
		parent = _parent;
	}
	
	public String getName() {
		return name;
	}
	
	public HierarchyBranch getParent() {
		return parent;
	}
	
	abstract public boolean isBranch();
	abstract public HierarchyObject getChild(String name);
	
	public String getPath() {
		if(parent != null) {
			return parent.getPath()+"/"+name;
		}
		return "/"+name;
	}
	
	  private static HierarchyObject referenceFile = null;

	  public static void setReferenceFile(File file) {
		  File parentFile = file.getParentFile();
		  String ap = file.getAbsolutePath();
		  int idx = ap.lastIndexOf(File.separator);
		  String name = ap.substring(idx);
		  DiskFilesystemHierarchy dfh = new DiskFilesystemHierarchy(parentFile.getAbsolutePath(), null, parentFile);
		  HierarchyObject ho = dfh.getChild(name);
		  referenceFile = ho;
	  }

	  public static void setReferenceFile(HierarchyObject f) {
		  referenceFile = f;
	  }

	  public static HierarchyObject getReferenceFile() {
		return referenceFile;
		/*
		if(referenceFile != null) {
		    return referenceFile;
	    }
    	return new File(System.getProperty("user.dir"));
    	*/
	  }

	  public static String getReferenceFilePath() {
	    return getReferenceFile().getPath()+File.separator;
	  }


}
