package se.modlab.generics.bshro.fs;

import java.io.File;

import se.modlab.generics.bshro.ifc.HierarchyBranch;

public class DiskFilesystemHierarchy extends DiskFilesystemFolder {
	
	public DiskFilesystemHierarchy(String name, HierarchyBranch parent, File file) {
		super(name, parent, file);
	}
	
}
