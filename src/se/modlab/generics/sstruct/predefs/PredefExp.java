package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefExp implements ArithmeticEvaluable 
{
  
  ArithmeticEvaluable ae;

  public PredefExp(ArithmeticEvaluable ae)
  {
    this.ae = ae;
  }

  public sValue evaluate(Scope s) throws IntolerableException
  {
    sValue val = ae.evaluate(s);
    return new sDouble(Math.exp(val.getValue()));
  }

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}
	
	  public String reproduceExpression() {
		  return "exp("+ae.reproduceExpression()+")";
	  }

}
