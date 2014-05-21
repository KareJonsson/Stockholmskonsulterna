package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;

public class StringRight implements StringEvaluable {

	// Fields
	private StringEvaluable source;
	private ArithmeticEvaluable len;

	// Constructors
	public StringRight(StringEvaluable _source, ArithmeticEvaluable _len)
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
				// Cant take more than what there is. Then right evaluates to all of it
				return src;
			}
			//int nochars = src.length();
			//int length = L_len.intValue();
			if(src.length() - L_len.intValue() <= 0) {
				// Cant start before character zero.
				return src;
			}
			int n1 = src.length() - L_len.intValue();
			int n2 = L_len.intValue() + n1;
			//System.out.println("n1 = "+n1+", n2 = "+n2);
			return src.substring(n1, n2);
		}
		catch(Exception e)
		{
			throw new UserCompiletimeError(
					"Right function unable to evaluate. The string was <" + src + ">" +
					".\n length evaluated to " + L_len.intValue(), e);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		len.verify(s);
		source.verify(s);
	}
	
	public String reproduceExpression() {
		return "right("+source.reproduceExpression()+", "+len.reproduceExpression()+")";
	}

}

