package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class LongConst extends LongVariable implements VariableConst
{
  
  public LongConst(String name, VariableType vt, String _filename, int _line, int _column)
  {
    super(name, vt, _filename, _line, _column);
  }

  public LongConst(LongVariable lv)
    throws IntolerableException
  {
    super(lv.getName(), lv.getType(), lv.getFilename(), lv.getLine(), lv.getColumn());
    super.setValue(lv.getValue());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Long constant "+name+" set to new value "+val
      +"\n"+"This happened to the variable declared "+getPlace());
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    longConst lc = new longConst(name, vt);
    lc.setInitialValue(getValue());
    return lc;
  }
 */

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Long constant "+name+" set to new value "+si
      +"\n"+"This happened to the variable declared "+getPlace());
  }

}
