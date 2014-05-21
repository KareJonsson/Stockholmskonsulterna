package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefMin implements ArithmeticEvaluable 
{

	ArithmeticEvaluable ae_1;
	ArithmeticEvaluable ae_2;

	public PredefMin(ArithmeticEvaluable ae_1, ArithmeticEvaluable ae_2)
	{
		this.ae_1 = ae_1;
		this.ae_2 = ae_2;
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		sValue val_1 = ae_1.evaluate(s);
		sValue val_2 = ae_2.evaluate(s);
		if(val_1.getValue() < val_2.getValue()) return val_1.copy();
		return val_2.copy();
	}

	public void verify(Scope s) throws IntolerableException {
		ae_1.verify(s);
		ae_2.verify(s);
	}
	
	  public String reproduceExpression() {
		  return "min("+ae_1.reproduceExpression()+", "+ae_2.reproduceExpression()+")";
	  }

}
