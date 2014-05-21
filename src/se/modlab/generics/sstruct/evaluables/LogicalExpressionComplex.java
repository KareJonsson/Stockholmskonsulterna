package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;

public class LogicalExpressionComplex implements LogicalExpression
{

	LogicalExpression le_l = null;
	LogicalOperator lo = null;
	LogicalExpression le_r = null;

	public LogicalExpressionComplex(LogicalExpression le_l,
			LogicalOperator lo,
			LogicalExpression le_r)
	{
		this.le_l = le_l;
		this.lo = lo;
		this.le_r = le_r;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		return lo.compare(le_l, le_r, s);
	}

	public void verify(Scope s) throws IntolerableException {
		le_l.verify(s);
		le_r.verify(s);
	}
	
	public String reproduceExpression() {
		return "("+le_l.reproduceExpression()+" "+lo.reproduceExpression()+" "+le_r.reproduceExpression()+")";
	}
}
