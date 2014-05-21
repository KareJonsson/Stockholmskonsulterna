package se.modlab.generics.bshro.ifc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HierarchyLeaf extends HierarchyObject {
	
	private byte array[] = null;
	
	public HierarchyLeaf(String name, HierarchyBranch parent) {
		super(name, parent);
	}
	
	public void setByteArray(byte _array[]) {
		array = _array;
	}
	
	public byte[] getByteArray() {
		return array;
	}
	
	public InputStream getInputStream() {
		return new ByteArrayInputStream(array);
	}

	public boolean isBranch() {
		return false;
	}

	public HierarchyObject getChild(String name) {
		//System.out.println("HierarchyLeaf.getChild("+name+")");
		return null;
	}

}
