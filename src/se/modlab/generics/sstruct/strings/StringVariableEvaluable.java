package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class StringVariableEvaluable implements StringEvaluable
{

	private VariableLookup vl; 

	public StringVariableEvaluable(VariableLookup vl)
	{
		this.vl = vl;
	}

	public VariableLookup getVariableReference()
	{
		return vl;
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		Variable var = vl.getVariable(s);
		if(var == null)
		{
			throw new UserRuntimeError("Variable "+vl+" not known in this scope.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());
		}
		if(!(var instanceof StringVariable))
		{
			throw new InternalError(
					"Assumed string variable "+vl+" was not just that.\n"+
					"Actual class was "+var.getClass().getName()+"\n"+
			"Error occurred in ccStringVariableEvaluable.evaluate\n");
		}
		StringVariable svar = (StringVariable) var;
		sString val = (sString) svar.getValue();
		return val.getString();
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = vl.getVariable(s);
		if(var == null)
		{
			throw new UserCompiletimeError("Variable "+vl+" not known in this scope.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());
		}
		if(!(var instanceof StringVariable))
		{
			throw new InternalError(
					"Assumed string variable "+vl+" was not just that.\n"+
					"Actual class was "+var.getClass().getName()+"\n"+
			"Error occurred in ccStringVariableEvaluable.evaluate\n");
		}
	}
	
	public String reproduceExpression() {
		return vl.reproduceExpression();
	}

}