package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public class StringLinefeed implements StringEvaluable
{

	public StringLinefeed()
	{
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		return "\n";
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	public String reproduceExpression() {
		return "<LF>";
	}


}