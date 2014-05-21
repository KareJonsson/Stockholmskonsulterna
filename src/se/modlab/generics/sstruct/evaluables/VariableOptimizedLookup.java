package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class VariableOptimizedLookup implements VariableLookup
{
	private String name;
	private String filename;
	private int beginLine;
	private int beginColumn;

	public VariableOptimizedLookup(String name, String filename,
			int beginLine, int beginColumn)
					throws IntolerableException
					{
		this.name = name;
		this.filename = filename;
		this.beginLine = beginLine;
		this.beginColumn = beginColumn;
	}

	public VariableInstance getInstance(Scope s) throws IntolerableException
	{
		return s.getComplexInstance(name);
	}

	public Variable getVariable(Scope s) throws IntolerableException
	{
		return s.getVariable(name);
	}

	public String getName()
	{
		return name;
	}

	public String getFilename()
	{
		return filename;
	}

	public int getLine()
	{
		return beginLine;
	}

	public int getColumn()
	{
		return beginColumn;
	}
	
	public String getPlace() {
		return "file "+filename+", line "+beginLine+", column "+beginColumn;
	}

	public String toString() {
		return name+" "+getPlace();
	}

	public String reproduceExpression() {
		return name;
	}

	public void verify(Scope s) throws IntolerableException {
		if(s.getVariable(name) == null) {
			throw new UserCompiletimeError("Variable "+name+" referred at "+getPlace()+" is not known.");
		}
	}


}

