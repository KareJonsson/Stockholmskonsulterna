package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class DoubleConst extends DoubleVariable implements VariableConst
{
  
  public DoubleConst(String name, VariableType vt, String _filename, int _line, int _column)
  {
	    super(name, vt, _filename, _line, _column);
  }

  public DoubleConst(DoubleVariable dv)
    throws IntolerableException
  {
    super(dv.getName(), dv.getType(), dv.getFilename(), dv.getLine(), dv.getColumn());
    super.setValue(dv.getValue());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Double constant "+name+" set to new value "+val
      +"\n"+"This happened to the variable declared "+getPlace());
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Double constant "+name+" set to new value "+si
      +"\n"+"This happened to the variable declared "+getPlace());
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    doubleConst dc = new doubleConst(name, vt);
    dc.setInitialValue(getValue());
    return dc;
  }
 */

}
