package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;

public class StringReplaceAll implements StringEvaluable {

	// Fields
	private StringEvaluable source;
	private StringEvaluable replace;
	private StringEvaluable substitute;

	// Constructors
	public StringReplaceAll(StringEvaluable _source, StringEvaluable _replace, StringEvaluable _substitute) {
		source = _source;
		replace = _replace;
		substitute = _substitute;
	}

	// Methods
	public String evaluate(Scope s) throws IntolerableException
	{
		String src = source.evaluate(s);
		String rep = replace.evaluate(s);
		String sub = substitute.evaluate(s);
		try {
			return src.replaceAll(rep, sub);
		}
		catch(Exception e) {
			throw new UserCompiletimeError(
					"replaceall function unable to evaluate. The source string was "+src+", replacement "+rep+", substitute "+sub, e);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		source.verify(s);
	}
	
	public String reproduceExpression() {
		return "replaceall("+source.reproduceExpression()+", "+replace.reproduceExpression()+", "+substitute.reproduceExpression()+")";
	}

}
