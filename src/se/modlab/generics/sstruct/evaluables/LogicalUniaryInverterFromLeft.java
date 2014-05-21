package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;

public class LogicalUniaryInverterFromLeft implements LogicalExpression
{

	private LogicalExpression le;

	public LogicalUniaryInverterFromLeft(LogicalExpression le)
	{
		this.le  = le;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		return !le.evaluate(s);
	}

	public void verify(Scope s)
	throws IntolerableException
	{
		le.verify(s);
	}
	
	public String reproduceExpression() {
		return "!"+le.reproduceExpression();
	}


}

