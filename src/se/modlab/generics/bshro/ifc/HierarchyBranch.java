package se.modlab.generics.bshro.ifc;

import java.util.Hashtable;
import java.util.Set;

public class HierarchyBranch extends HierarchyObject {
	
	protected Hashtable<String, HierarchyObject> hierarchy = new Hashtable<String, HierarchyObject>();
	
	public HierarchyBranch(String name, HierarchyBranch parent) {
		super(name, parent);
	}
	
	public HierarchyObject getChild(String name) {
		//System.out.println("HierarchyBranch.getChild("+name+")");
		String path[] = getChopped(name);
		if(path.length == 1) {
			if(path[0].compareTo("..") == 0) {
				return getParent();
			}
			return hierarchy.get(name);
		}
		return getChild(path);
	}
	 
	protected static String show(String in[]) {
		if(in == null) {
			return "<NULL>";
		}
		if(in.length == 0) {
			return "[]";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("["+in[0]);
		for(int i = 1 ; i < in.length ; i++) {
			sb.append(", "+in[i]);
		}
		return sb.toString()+"]";
	}

	protected HierarchyObject getChild(String name[]) {
		//System.out.println("HierarchyBranch.getChild("+show(name)+")");
		HierarchyObject climb = this;
		for(int i = 0 ; i < name.length ; i++) {
			climb = climb.getChild(name[i]);
			if(climb == null) {
				return null;
			}
		}
		return climb;
	}
	
	public HierarchyBranch addBranchChild(String name) {
		HierarchyBranch child = new HierarchyBranch(name, this);
		hierarchy.put(name, child);
		return child;
	}

	public HierarchyLeaf addLeafChild(String name) {
		HierarchyLeaf child = new HierarchyLeaf(name, this);
		hierarchy.put(name, child);
		return child;
	}
	
	public boolean isBranch() {
		return true;
	}
	
	public static String[] getChopped(String path) {
		path = path.trim();
		if(path.length() == 0) {
			return new String[0];
		}
		String unifiedPathSeparator = path.replace('\\', '/');
		while(unifiedPathSeparator.contains("//")) {
			unifiedPathSeparator = unifiedPathSeparator.replaceAll("//", "/");
		}
		return unifiedPathSeparator.split("/");
	}

	public String[] getChildrensNames() {
		Set<String> names = hierarchy.keySet();
		String out[] = new String[names.size()];
		int pos = 0;
		for(String name : names) {
			out[pos++] = name;
		}
		return out;
	}

}
