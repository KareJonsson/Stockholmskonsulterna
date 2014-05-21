package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class StringValueAliasing extends ArgumentPasser
{

  private StringEvaluable se;

  public StringValueAliasing(String name, String _filename, int _line, int _column, StringEvaluable _se)
  {
    super(name, _filename, _line, _column);
    se = _se;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    sValue val = new sString(se.evaluate(from));
    Variable var = new StringVariable(name, from.getFactory("string"), filename, line, column);
    var.setInitialValue(val);
    to.addVariable(var);
  }

}
