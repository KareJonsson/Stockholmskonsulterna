package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class DoubleValueAliasing extends ArgumentPasser
{

  private ArithmeticEvaluable ae;

  public DoubleValueAliasing(String name, String _filename, int _line, int _column, ArithmeticEvaluable _ae)
  {
    super(name, _filename, _line, _column);
    ae = _ae;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    sValue val = ae.evaluate(from);
    Variable var = new DoubleVariable(name, from.getFactory("double"), filename, line, column);
    var.setInitialValue(val);
    to.addVariable(var);
  }

}