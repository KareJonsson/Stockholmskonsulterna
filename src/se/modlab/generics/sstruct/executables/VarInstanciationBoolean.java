package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class VarInstanciationBoolean extends VarInstanciation
{
  
  private LogicalExpression le;
  
  public VarInstanciationBoolean(String varName, 
		                         String _filename, int _line, int _column,
                                 LogicalExpression _le)
  {
    super(varName, _filename, _line, _column);
    le = _le;
  }

  public void execute(Scope s) 
    throws ReturnException, 
           IntolerableException, 
           StopException,
           BreakException,
           ContinueException
  {
    Variable var = new BooleanVariable(varName, s.getFactory("boolean"), filename, line, column);
    sValue val = null;
    if(le != null)
    {
      val = new sBoolean(le.evaluate(s));
    }
    else
    {
      val = new sBoolean(true);
    }
    var.setInitialValue(val);
    s.addVariable(var);
  }

public void verify(Scope s) throws IntolerableException {
    Variable var = new BooleanVariable(varName, s.getFactory("boolean"), filename, line, column);
    if(le != null)
    {
      le.verify(s);
    }
    var.setDefaultInitialValue();
    s.addVariable(var);
}

}
