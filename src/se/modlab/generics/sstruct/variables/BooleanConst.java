package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class BooleanConst extends BooleanVariable implements VariableConst
{
  
  public BooleanConst(String name, VariableType vt, String _filename, int _line, int _column)
  {
    super(name, vt, _filename, _line, _column);
  }

  public BooleanConst(BooleanVariable bv)
    throws IntolerableException
  {
    super(bv.getName(), bv.getType(), bv.getFilename(), bv.getLine(), bv.getColumn());
    super.setValue(bv.getValue());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Boolean constant "+name+" declared "+getPlace()+" set to new value "+val);
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Boolean constant "+name+" declared "+getPlace()+" set to new value "+si);
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    booleanConst bc = new booleanConst(name, vt);
    bc.setInitialValue(getValue());
    return bc;
  }
 */

}
