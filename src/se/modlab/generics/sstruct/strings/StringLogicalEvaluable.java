package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;

public class StringLogicalEvaluable implements StringEvaluable
{

	private LogicalExpression le;

	public StringLogicalEvaluable(LogicalExpression _le)
	{
		this.le = _le;
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		boolean b = le.evaluate(s);
		if(b) return "true";
		return "false";
	}

	public void verify(Scope s) throws IntolerableException {
		le.verify(s);
	}
	
	public String reproduceExpression() {
		return le.reproduceExpression();
	}


}
