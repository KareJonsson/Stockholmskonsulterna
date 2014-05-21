package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class StringVariableUpdate extends VarUpdate
{

	private StringEvaluable se = null;

	public StringVariableUpdate(VariableLookup vl, 
			StringEvaluable _se,
			String filename, int line, int column)
	{
		super(vl, filename, line, column);
		se = _se;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		//System.out.println("stringVariableUpdate - XXX");
		Variable var = vl.getVariable(s);
		if(var == null)
		{
			throw new UserRuntimeError(
					"Variable "+vl+" declared in file "+vl.getFilename()+", on line "+
					vl.getLine()+", column "+
					vl.getColumn()+" not in scope. "+s);
		}
		String str = se.evaluate(s);
		var.setValue(new sString(str));
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = vl.getVariable(s);
		if(var == null)
		{
			throw new UserCompiletimeError(
					"Variable "+vl+" declared in file "+vl.getFilename()+", on line "+
					vl.getLine()+", column "+
					vl.getColumn()+" not in scope. "+s);
		}
		se.verify(s);
	}

}
