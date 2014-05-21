package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.logics.LogicalExpression;

public class StringLogicallyDeciding implements StringEvaluable {

	// Fields
	private LogicalExpression le;
	private StringEvaluable positive;
	private StringEvaluable negative;

	// Constructors
	public StringLogicallyDeciding(LogicalExpression _le, StringEvaluable _positive, StringEvaluable _negative)
	{
		le = _le;
		positive = _positive;
		negative = _negative;
	}

	public String evaluate(Scope s) throws IntolerableException {
		if(le.evaluate(s)) {
			return positive.evaluate(s);
		}
		return negative.evaluate(s);
	}

	public String reproduceExpression() {
		return "("+le.reproduceExpression()+") ? "+positive.reproduceExpression()+" : "+negative.reproduceExpression();
	}

	public void verify(Scope s) throws IntolerableException {
		le.verify(s);
		positive.verify(s);
		negative.verify(s);
	}

}
