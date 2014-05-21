package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefAcos implements ArithmeticEvaluable 
{
  
  ArithmeticEvaluable ae;

  public PredefAcos(ArithmeticEvaluable ae)
  {
    this.ae = ae;
  }

  public sValue evaluate(Scope s) throws IntolerableException
  {
    sValue val = ae.evaluate(s);
    return new sDouble(Math.acos(val.getValue()));
  }

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}
	
	  public String reproduceExpression() {
		  return "acos("+ae.reproduceExpression()+")";
	  }

}
