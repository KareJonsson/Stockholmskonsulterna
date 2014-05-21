package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;

public interface StringEvaluable 
{

	  public String evaluate(Scope s)
	    throws IntolerableException;

	  public void verify(Scope s)
	    throws IntolerableException;

	  public String reproduceExpression();
	  
}
