package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.strings.*;

public class LogicalExpressionStartsWith implements LogicalExpression
{

	private StringEvaluable toSearchFor;
	private StringEvaluable toSearchIn;

	public LogicalExpressionStartsWith(StringEvaluable _toSearchIn, StringEvaluable _toSearchFor)
	{
		toSearchIn = _toSearchIn;
		toSearchFor = _toSearchFor;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		String searchIn = toSearchIn.evaluate(s);
		String searchFor = toSearchFor.evaluate(s);
		//boolean b = searchIn.startsWith(searchFor)
		//System.out.println("STARTSWITH search for "+searchFor+" , in "+searchIn);
		return searchIn.startsWith(searchFor);
	}

	public void verify(Scope s) throws IntolerableException {
		toSearchFor.verify(s);
		toSearchIn.verify(s);
	}

	public String reproduceExpression() {
		return "startsWith("+toSearchIn.reproduceExpression()+", "+toSearchFor.reproduceExpression()+")";
	}

	public static void main(String args[]) {
		String in = "R:\\a\\b";
		String x = "R:\\";
		System.out.println("in "+in+", x "+x+" : "+in.startsWith(x));
	}
	
}