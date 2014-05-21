package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;

public class LogicalExpressionBoolean implements LogicalExpression
{

	boolean val;

	public LogicalExpressionBoolean(boolean val)
	{
		this.val = val;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		return val;
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	public String reproduceExpression() {
		return ""+val;
	}



}
