package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefLog implements ArithmeticEvaluable 
{

	ArithmeticEvaluable ae;

	public PredefLog(ArithmeticEvaluable ae)
	{
		this.ae = ae;
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		sValue val = ae.evaluate(s);
		return new sDouble(Math.log(val.getValue()));
	}

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}
	
	  public String reproduceExpression() {
		  return "log("+ae.reproduceExpression()+")";
	  }


}
