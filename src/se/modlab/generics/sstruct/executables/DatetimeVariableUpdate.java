package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.BreakException;
import se.modlab.generics.exceptions.ContinueException;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.exceptions.ReturnException;
import se.modlab.generics.exceptions.StopException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.comparisons.VariableLookup;
import se.modlab.generics.sstruct.datetimeevaluables.DatetimeEvaluable;
import se.modlab.generics.sstruct.values.sDate;
import se.modlab.generics.sstruct.variables.Variable;

public class DatetimeVariableUpdate extends VarUpdate
{

	private DatetimeEvaluable de = null;

	public DatetimeVariableUpdate(VariableLookup vl, 
			DatetimeEvaluable _de,
			String filename, int line, int column) {
		super(vl, filename, line, column);
		de = _de;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException {
		//System.out.println("stringVariableUpdate - XXX");
		Variable var = vl.getVariable(s);
		if(var == null) {
			throw new UserRuntimeError(
					"Variable "+vl+" declared in file "+vl.getFilename()+", on line "+
					vl.getLine()+", column "+
					vl.getColumn()+" not in scope. "+s);
		}
		sDate da = de.evaluate(s);
		var.setValue(da);
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
		de.verify(s);
	}

}
