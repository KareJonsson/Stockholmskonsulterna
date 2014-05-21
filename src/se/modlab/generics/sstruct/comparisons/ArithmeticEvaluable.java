package se.modlab.generics.sstruct.comparisons;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public interface ArithmeticEvaluable 
{

	  public sValue evaluate(Scope s)
	    throws IntolerableException;

	  public void verify(Scope s)
	    throws IntolerableException;

	  public String reproduceExpression();

}
