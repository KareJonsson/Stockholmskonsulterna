package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class StringConst extends StringVariable implements VariableConst
{
  
  public StringConst(String name, VariableType vt, String _filename, int _line, int _column)
  {
	    super(name, vt, _filename, _line, _column);
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "String constant "+name+" set to new value "+val
      +"\n"+"This happened to the variable declared "+getPlace());
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "String constant "+name+" set to new value "+si
      +"\n"+"This happened to the variable declared "+getPlace());
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    stringConst sc = new stringConst(name, vt);
    sc.setInitialValue(getValue());
    return sc;
  }
 */

}
