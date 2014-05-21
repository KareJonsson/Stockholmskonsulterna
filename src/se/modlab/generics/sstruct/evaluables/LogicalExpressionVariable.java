package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.variables.*;

public class LogicalExpressionVariable implements LogicalExpression
{

	private VariableLookup vl; 

	public LogicalExpressionVariable(VariableLookup _vl)
	{
		vl = _vl;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		Variable var = vl.getVariable(s);
		if(var == null) 
		{
			throw new UserRuntimeError(
					"Boolean variable "+vl+" not in scope.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());
		}
		if(!(var instanceof BooleanVariable))
		{
			throw new UserRuntimeError(
					"Variable "+vl+" is not of type boolean.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());      
		}
		return var.getValue().getBoolean().booleanValue();
	}

	public VariableLookup getVariableReference()
	{
		return vl;
	}

	public int getLine()
	{
		return vl.getLine();
	}

	public int getColumn()
	{
		return vl.getColumn();
	}

	public String toString()
	{
		return "Name "+vl+" in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn();
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = vl.getVariable(s);
		if(var == null) 
		{
			throw new UserCompiletimeError(
					"Boolean variable "+vl+" not in scope.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());
		}
		if(!(var instanceof BooleanVariable))
		{
			throw new UserCompiletimeError(
					"Variable "+vl+" is not of type boolean.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());      
		}
	}
	
	public String reproduceExpression() {
		return vl.reproduceExpression();
	}

}

