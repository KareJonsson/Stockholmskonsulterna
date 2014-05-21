package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.BreakException;
import se.modlab.generics.exceptions.ContinueException;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.exceptions.ReturnException;
import se.modlab.generics.exceptions.StopException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.comparisons.VariableLookup;
import se.modlab.generics.sstruct.strings.StringEvaluable;
import se.modlab.generics.sstruct.values.sString;
import se.modlab.generics.sstruct.variables.StringVariable;
import se.modlab.generics.sstruct.variables.VariableInstance;

public class AppendStringStatement extends ProgramStatement {
	
	private VariableLookup vl;
	private StringEvaluable ev;
	
	public AppendStringStatement(VariableLookup _vl, StringEvaluable _ev) {
		vl = _vl;
		ev = _ev;
	}

	public void execute(Scope s) throws ReturnException, IntolerableException,
			StopException, BreakException, ContinueException {
		VariableInstance vi = vl.getInstance(s);
		if(vi == null) {
			throw new UserCompiletimeError("Variable "+vl+" is not defined in this scope.");
		}
		if(!(vi instanceof StringVariable)) {
			throw new UserCompiletimeError("Variable "+vl+" is not of string type.");
		}
		StringVariable sv = (StringVariable) vi;
		String str = ev.evaluate(s);
		sv.append(new sString(str));
	}

	public void verify(Scope s) throws IntolerableException {
		VariableInstance vi = vl.getInstance(s);
		if(vi == null) {
			throw new UserCompiletimeError("Variable "+vl+" is not defined in this scope.");
		}
		if(!(vi instanceof StringVariable)) {
			throw new UserCompiletimeError("Variable "+vl+" is not of string type.");
		}
	}

}
