package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.strings.*;

public class LogicalExpressionEvaluable implements LogicalExpression
{

	private StringEvaluable toEvaluate;

	public LogicalExpressionEvaluable(StringEvaluable _toEvaluate)
	{
		toEvaluate = _toEvaluate;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		StringValue sVal = new StringValue(toEvaluate);
		try
		{
			sVal.evaluate(s);
			return true;
		}
		catch(IntolerableException ie)
		{
		}
		return false;
	}

	public void verify(Scope s) throws IntolerableException {
		toEvaluate.verify(s);
	}
	
	public String reproduceExpression() {
		return "evaluable("+toEvaluate.reproduceExpression()+")";
	}


}
