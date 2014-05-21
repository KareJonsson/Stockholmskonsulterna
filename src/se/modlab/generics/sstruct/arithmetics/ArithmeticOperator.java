package se.modlab.generics.sstruct.arithmetics;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public interface ArithmeticOperator 
{
  public sValue operate(sValue left, sValue right)
    throws IntolerableException;
  
  public String reproduceExpression();
  
}
