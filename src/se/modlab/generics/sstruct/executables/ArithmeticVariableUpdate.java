package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class ArithmeticVariableUpdate extends VarUpdate
{

	private ArithmeticEvaluable ae = null;

	public ArithmeticVariableUpdate(VariableLookup vl,
			ArithmeticEvaluable _ae,
			String filename, int line, int column)
	{
		super(vl, filename, line, column);
		ae = _ae;
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
		sValue val = ae.evaluate(s);
		var.setValue(val);
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
		ae.verify(s);
	}

}
