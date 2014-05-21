package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefTime implements ArithmeticEvaluable 
{
	public PredefTime()
	{
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		return new sLong(System.currentTimeMillis());
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	  public String reproduceExpression() {
		  return "time()";
	  }


}
