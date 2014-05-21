package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class BooleanValueAliasing extends ArgumentPasser
{

  private LogicalExpression le;

  public BooleanValueAliasing(String name, String _filename, int _line, int _column, LogicalExpression _le)
  {
    super(name, _filename, _line, _column);
    le = _le;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    sValue val = new sBoolean(le.evaluate(from));
    Variable var = new BooleanVariable(name, from.getFactory("boolean"), filename, line, column);
    var.setInitialValue(val);
    to.addVariable(var);
  }

}