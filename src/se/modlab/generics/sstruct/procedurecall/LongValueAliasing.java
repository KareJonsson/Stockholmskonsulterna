package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class LongValueAliasing extends ArgumentPasser
{

  private ArithmeticEvaluable ae;

  public LongValueAliasing(String name, String _filename, int _line, int _column, ArithmeticEvaluable ae)
  {
    super(name, _filename, _line, _column);
    this.ae = ae;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    sValue val = ae.evaluate(from);
    if(!(val instanceof sLong))
    {
      throw new UserCompiletimeError(
        "The expression within brackets was not evaluated to a long value.\n"+
        "Hint: Try with predefined function trunc around it.");
    }
    Variable var = new LongVariable(name, from.getFactory("long"), filename, line, column);
    var.setInitialValue(val);
    to.addVariable(var);
  }

}