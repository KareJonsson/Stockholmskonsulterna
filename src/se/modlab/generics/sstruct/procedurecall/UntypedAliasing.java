package se.modlab.generics.sstruct.procedurecall;

public class UntypedAliasing {

	private String typename;
	private String varname;
	private boolean isReferenced;
	private boolean isArray;
	private String filename;
	private int line;
	private int column;

	public UntypedAliasing(String _typename, String _varname, boolean _isReferenced, boolean _isArray, String _filename, int _line, int _column) {
		typename = _typename;
		varname = _varname;
		isReferenced = _isReferenced;
		isArray = _isArray;
		filename = _filename;
		line = _line;
		column = _column;
	}

	public String getTypename() {
		return typename;
	}

	public String getVarname() {
		return varname;
	}

	public boolean isReferenced() {
		return isReferenced;
	}

	public boolean isArray() {
		return isArray;
	}

	public String getPlaceString() {
		return "in file "+filename+", line "+line+", column "+column;
	}

}
