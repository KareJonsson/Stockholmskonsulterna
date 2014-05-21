package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public abstract class StringOperator
{
	private String filename;
	private int line;
	private int column;
	
	public StringOperator(String _filename, int _line, int _column) 
	{
		filename = _filename;
		line = _line;
		column = _column;
	}

	abstract public boolean compare(StringEvaluable left,
                         			StringEvaluable right,
                         			Scope s)
    throws IntolerableException;
	
	public abstract String reproduceExpression();
	
	public String getPlace() {
		return "file "+filename+", line "+line+", column "+column;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}

}