package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;

public class StringLowerCase implements StringEvaluable {

	// Fields
	private StringEvaluable source;

	// Constructors
	public StringLowerCase(StringEvaluable _source)
	{
		source = _source;
	}

	// Methods
	public String evaluate(Scope s) throws IntolerableException
	{
		String src = source.evaluate(s);
		try
		{
			return src.toLowerCase();
		}
		catch(Exception e)
		{
			throw new UserCompiletimeError(
					"lowercase function unable to evaluate. The string was " + src, e);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		source.verify(s);
	}
	
	public String reproduceExpression() {
		return "lowercase("+source.reproduceExpression()+")";
	}

}
