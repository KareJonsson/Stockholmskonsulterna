package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;

public class LogicalExpressionComparison implements LogicalExpression
{

	ArithmeticEvaluable ae_l = null;
	ComparisonOperator co = null;
	ArithmeticEvaluable ae_r = null;

	public LogicalExpressionComparison(ArithmeticEvaluable ae_l,
			ComparisonOperator co,
			ArithmeticEvaluable ae_r)
	{
		this.ae_l = ae_l;
		this.co = co;
		this.ae_r = ae_r;
	}

	public boolean evaluate(Scope s)
	throws IntolerableException
	{
		return co.compare(ae_l, ae_r, s);
	}

	public void verify(Scope s) throws IntolerableException {
		ae_l.verify(s);
		ae_r.verify(s);
	}
	
	public String reproduceExpression() {
		return "("+ae_l.reproduceExpression()+" "+co.reproduceExpression()+" "+ae_r.reproduceExpression()+")";
	}

}
