package se.modlab.generics.sstruct.strings;

import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class StringsSubstring implements StringEvaluable {

	// Fields
	private StringEvaluable source;
	private ArithmeticEvaluable to;
	private ArithmeticEvaluable from;

	// Constructors
	public StringsSubstring(StringEvaluable _source, ArithmeticEvaluable _from, ArithmeticEvaluable _to)
	{
		source = _source;
		from = _from;
		to = _to;
	}

	// Methods
	public String evaluate(Scope s) throws IntolerableException
	{
		Long L_f;
		Long L_t;
		String src;
		sValue fromidx = from.evaluate(s);
		L_f = fromidx.getLong();
		if(L_f == null)
		{
			throw new UserCompiletimeError(
					"Argument 2 to indexOf did not evaluate to long (integer) value");
		}
		sValue toidx = to.evaluate(s);
		L_t = toidx.getLong();
		if(L_t == null)
			throw new UserCompiletimeError("Argument 3 to indexOf did not evaluate to long (integer) value");
		src = source.evaluate(s);
		try
		{
			return src.substring(L_f.intValue(), L_t.intValue());
		}
		catch(Exception e)
		{
			throw new UserCompiletimeError(
					"Substring function unable to evalueate. The string was " + src +
					".\n First index evaluated to " + L_f.intValue() +
					".\n Second index evaluated to " + L_t.intValue() +
					".\n The length of the source string is " + src.length(), e);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		from.verify(s);
		to.verify(s);
		source.verify(s);
	}
	
	public String reproduceExpression() {
		return "substring("+source.reproduceExpression()+", "+from.reproduceExpression()+", "+to.reproduceExpression()+")";
	}

}

