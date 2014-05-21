package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.strings.*;

public class LogicalExpressionLitteralComparison implements LogicalExpression
{

	StringEvaluable se_l = null;
	StringOperator so = null;
	StringEvaluable se_r = null;

	public LogicalExpressionLitteralComparison(StringEvaluable _se_l,
			StringOperator _so,
			StringEvaluable _se_r)
	{
		se_l = _se_l;
		so = _so;
		se_r = _se_r;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		return so.compare(se_l, se_r, s);
	}

	public void verify(Scope s) throws IntolerableException {
		se_l.verify(s);
		se_r.verify(s);
	}
	
	public String reproduceExpression() {
		return "("+se_l.reproduceExpression()+" "+so.reproduceExpression()+" "+se_r.reproduceExpression()+")";
	}


}
