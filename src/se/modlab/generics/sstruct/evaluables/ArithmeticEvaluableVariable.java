package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class ArithmeticEvaluableVariable implements ArithmeticEvaluable
{

	private VariableLookup vl; 

	public ArithmeticEvaluableVariable(VariableLookup vl)
	{
		this.vl = vl;
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		Variable var = vl.getVariable(s);
		if(var == null) {
			throw new UserCompiletimeError(
					"Variable "+vl+" not in scope.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());
		}
		//System.out.println("arithmeticEvaluableVariable: Evaluate variable "+vl+" to value "+var.getValue()+", variable is "+var);
		return var.getValue();
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
					"Variable "+vl+" not in scope.\n"+
					"Referenced in file "+vl.getFilename()+", line "+vl.getLine()+", column "+vl.getColumn());
		}
	}
	
	public String reproduceExpression() {
		return ""+vl.reproduceExpression();
	}


}

