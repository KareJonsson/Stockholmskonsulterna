package se.modlab.generics.sstruct.arithmetics;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class ArithmeticOperatorModulo implements ArithmeticOperator
{

  public sValue operate(sValue left, sValue right)
    throws IntolerableException
  {

    if((left instanceof sLong) &&
       (right instanceof sLong))
    {
      return new sLong(left.getLong().longValue()%right.getLong().longValue()); 
    }
    return new sDouble(left.getValue()%right.getValue());

  }

	public String reproduceExpression() {
		return "%";
	}

}
