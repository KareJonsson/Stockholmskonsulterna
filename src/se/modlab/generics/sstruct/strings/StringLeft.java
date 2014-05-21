package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;

public class StringLeft implements StringEvaluable {

	// Fields
	private StringEvaluable source;
	private ArithmeticEvaluable len;

	// Constructors
	public StringLeft(StringEvaluable _source, ArithmeticEvaluable _len)
	{
		source = _source;
		len = _len;
	}

	// Methods
	public String evaluate(Scope s) throws IntolerableException
	{
		String src;
		Long L_len = len.evaluate(s).getLong();
		if(L_len == null)
		{
			return "";
		}
		src = source.evaluate(s);
		try
		{
			if(src.length() <= L_len.intValue()) {
				return src;
			}
			String out = src.substring(0, L_len.intValue());
			//System.out.println("ccLeft returns "+out+", full value is <"+src+">");
			return out;
		}
		catch(Exception e)
		{
			throw new UserCompiletimeError(
					"Left function unable to evaluate. The string was " + src +
					".\n length evaluated to " + L_len.intValue(), e);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		len.verify(s);
		source.verify(s);
	}
	
	public String reproduceExpression() {
		return "left("+source.reproduceExpression()+", "+len.reproduceExpression()+")";
	}

}

