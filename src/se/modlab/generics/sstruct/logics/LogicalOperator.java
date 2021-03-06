package se.modlab.generics.sstruct.logics;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public interface LogicalOperator 
{
  public boolean compare(LogicalExpression left, 
                         LogicalExpression right, 
                         Scope s)
    throws IntolerableException;
  
	public String reproduceExpression();

}
