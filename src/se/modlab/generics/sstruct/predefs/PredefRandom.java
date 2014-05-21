package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class PredefRandom implements ArithmeticEvaluable 
{

	public PredefRandom()
	{
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		return new sDouble(Math.random());
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	  public String reproduceExpression() {
		  return "random()";
	  }


}
