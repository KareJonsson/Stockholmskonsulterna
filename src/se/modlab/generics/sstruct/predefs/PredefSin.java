package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefSin implements ArithmeticEvaluable 
{

	ArithmeticEvaluable ae;

	public PredefSin(ArithmeticEvaluable ae)
	{
		this.ae = ae;
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		sValue val = ae.evaluate(s);
		return new sDouble(Math.sin(val.getValue()));
	}

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}

	  public String reproduceExpression() {
		  return "sin("+ae.reproduceExpression()+")";
	  }

}
