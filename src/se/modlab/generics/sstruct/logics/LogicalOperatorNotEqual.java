package se.modlab.generics.sstruct.logics;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public class LogicalOperatorNotEqual implements LogicalOperator
{
  public boolean compare(LogicalExpression left, LogicalExpression right, Scope s)
    throws IntolerableException
  {
    boolean l = left.evaluate(s);
    boolean r = right.evaluate(s);
    return (l != r);
  }
  
	public String reproduceExpression() {
		return "!=";
	}

}
