package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sValue;

public class StringCompleteOnLeftToLength implements StringEvaluable {

	// Fields
	private StringEvaluable source;
	private ArithmeticEvaluable tolength;
	private StringEvaluable completion;

	// Constructors
	public StringCompleteOnLeftToLength(StringEvaluable _source, ArithmeticEvaluable _tolength, StringEvaluable _completion)
	{
		source = _source;
		tolength = _tolength;
		completion = _completion;
	}

	// Methods
	public String evaluate(Scope s) throws IntolerableException {
		try {
			String s_source = source.evaluate(s);
			sValue l_tolength = tolength.evaluate(s);
			String s_completion = completion.evaluate(s);
			String out = s_source;
			while(out.length() < l_tolength.getValue()) {
				if(out.length() + s_completion.length() < l_tolength.getValue()) {
					out = s_completion + out;
				}
				else {
					out = s_completion.substring(0, Math.round(((int)l_tolength.getValue())) - out.length()) + out;
				}
			}
			return out;
		}
		catch(IntolerableException ie) {
			sValue l_tolength = tolength.evaluate(s);
			String s_completion = completion.evaluate(s);
			StringBuffer sb = new StringBuffer();
			int repetitions = (int) Math.round(l_tolength.getDouble().doubleValue() / s_completion.length());
			for(int i = 0 ; i < repetitions ; i++) {
				sb.append(s_completion);
			}
			int remainder = (int) l_tolength.getLong().longValue() - sb.length();
			return sb.toString()+s_completion.substring(0, remainder);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		tolength.verify(s);
		completion.verify(s);
		source.verify(s);
	}
	
	public String reproduceExpression() {
		return "completeOnLeftToLength("+source.reproduceExpression()+", "+tolength.reproduceExpression()+", "+completion.reproduceExpression()+")";
	}

}

