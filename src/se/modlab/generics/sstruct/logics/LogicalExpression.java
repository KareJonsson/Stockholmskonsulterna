package se.modlab.generics.sstruct.logics;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public interface LogicalExpression 
{
  
	  public boolean evaluate(Scope s)
	    throws IntolerableException;

	  public void verify(Scope s)
	    throws IntolerableException;
	  
	  public String reproduceExpression();

}
