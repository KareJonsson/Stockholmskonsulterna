package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefAsin implements ArithmeticEvaluable 
{
  
  ArithmeticEvaluable ae;

  public PredefAsin(ArithmeticEvaluable ae)
  {
    this.ae = ae;
  }

  public sValue evaluate(Scope s) throws IntolerableException
  {
    sValue val = ae.evaluate(s);
    return new sDouble(Math.asin(val.getValue()));
  }
	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}
	
	  public String reproduceExpression() {
		  return "asin("+ae.reproduceExpression()+")";
	  }


}
