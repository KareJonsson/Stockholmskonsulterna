package se.modlab.generics.sstruct.arithmetics;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class ArithmeticOperatorDiv implements ArithmeticOperator
{

  public sValue operate(sValue left, sValue right)
    throws IntolerableException
  {
    return new sDouble(left.getValue()/right.getValue());
  }
  
  
	public String reproduceExpression() {
		return "/";
	}

}
