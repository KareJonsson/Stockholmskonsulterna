package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class BooleanVariableUpdate extends VarUpdate
{

	private LogicalExpression le = null;

	public BooleanVariableUpdate(VariableLookup vl, LogicalExpression _le,
			String filename, int line, int column)
	{
		super(vl, filename, line, column);
		le = _le;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		Variable var = vl.getVariable(s);
		if(var == null)
		{
			throw new UserRuntimeError(
					"Variable "+vl+" declared in file "+vl.getFilename()+", on line "+
					vl.getLine()+", column "+
					vl.getColumn()+" not in scope. "+s);
		}
		boolean b = le.evaluate(s);
		var.setValue(new sBoolean(b));
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
		le.verify(s);
	}

}
