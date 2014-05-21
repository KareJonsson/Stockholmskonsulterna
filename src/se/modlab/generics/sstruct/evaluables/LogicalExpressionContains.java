package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.strings.*;

public class LogicalExpressionContains implements LogicalExpression
{

	private StringEvaluable toSearchFor;
	private StringEvaluable toSearchIn;

	public LogicalExpressionContains(StringEvaluable _toSearchFor, StringEvaluable _toSearchIn)
	{
		this.toSearchFor = _toSearchFor;
		this.toSearchIn = _toSearchIn;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		String searchFor = toSearchFor.evaluate(s);
		String searchIn = toSearchIn.evaluate(s);
		return searchIn.indexOf(searchFor) != -1;
	}

	public void verify(Scope s) throws IntolerableException {
		toSearchFor.verify(s);
		toSearchIn.verify(s);
	}
	
	public String reproduceExpression() {
		return "contains("+toSearchFor.reproduceExpression()+", "+toSearchIn.reproduceExpression()+")";
	}


}