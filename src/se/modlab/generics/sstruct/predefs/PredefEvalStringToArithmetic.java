package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.strings.StringEvaluable;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;

public class PredefEvalStringToArithmetic implements ArithmeticEvaluable 
{ 

	protected StringEvaluable ccsl;

	public PredefEvalStringToArithmetic(StringEvaluable _ccsl)
	{
		ccsl = _ccsl;
	}

	public sValue evaluate(Scope s) 
	throws IntolerableException
	{
		String str = ccsl.evaluate(s);
		try {
			long out = Long.parseLong(str);
			return new sLong(out);
		}
		catch(Exception e) {
			
		}
		try {
			double out = Double.parseDouble(str);
			return new sDouble(out);
		}
		catch(Exception e) {
			
		}
		throw new UserRuntimeError(
		"Unalbe to evaluate "+str+" to arithmetic value");
		//return null;
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	  public String reproduceExpression() {
		  return "evalstringtolong("+ccsl.reproduceExpression()+")";
	  }


}
