package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.tables.*;
import se.modlab.generics.sstruct.values.*;

public class BooleanVariable extends Variable
{
  
  public BooleanVariable(String name, VariableType vt, String _filename, int _line, int _column)
  {
    super(name, vt, _filename, _line, _column);
  }
 
  public void setDefaultInitialValue()
    throws IntolerableException
  {
    setInitialValue(new sBoolean(true));
  }

  public void setInitialValue(Holder val) 
    throws IntolerableException
  {
    if(val instanceof LongHolder)
    {
      long l = ((LongHolder) val).getLong();
      if(l == 0)
      {
        setInitialValue(new sBoolean(false));
        return;
      }
      if(l == 1)
      {
        setInitialValue(new sBoolean(true));
        return;
      }
    }
    if(val instanceof DoubleHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type boolean.\n"+
        "That is not properly defined."+"\n"+"This happened to the variable declared "+getPlace());
    }
    if(val instanceof BooleanHolder)
    {
      boolean b = ((BooleanHolder) val).getBoolean();
      setInitialValue(new sBoolean(b));
      return;
    }
    if(val instanceof StringHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type long.\n"+
        "That is not properly defined."+"\n"+"This happened to the variable declared "+getPlace());
    }
    throw new InternalError(
      "Logics in method "+
      "booleanVariable.setInitialValue(holder val)\n"+
      "is outdated.");
  }

  public void setInitialValue(sValue initialVal) 
    throws IntolerableException
  {
/*
    if(val != null)
    {
      throw new intolerableException(
        "Internal: Variable "+name+" had value "+val+"\n"+
        "prior to call to setInitialValue with value "+initialVal);         
    }
 */
    if(!(initialVal instanceof sBoolean)) 
    {
      throw new UserRuntimeError(
        "Boolean variable "+name+" set to non boolean value "+initialVal
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    //initialValue = initialVal.copy();
    val = new sBoolean(((sBoolean)initialVal).getBoolean().booleanValue());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    if(!(val instanceof sBoolean)) 
      throw new UserRuntimeError("Boolean variable "+
                         name+" set to non boolean value "+val+
                         "\n"+"This happened to the variable declared "+getPlace());
    if(this.val == null)
    {
      throw new InternalError(
        "Variable "+name+" had no value \n"+
        "prior to call to SimBooleanVariable.setValue with value "+val
        +"\n"+"This happened to the variable declared "+getPlace());         
    }
    this.val = val;
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    if(!(si instanceof BooleanVariable))
    {
      throw new UserRuntimeError( 
        "Boolean variable "+name+" set to value of \n"+
        "type "+si.getType().getTypesName()
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    setValue(((BooleanVariable) si).getValue());
  }

  public void setInitialValue(VariableInstance val) 
    throws IntolerableException
  {
    setValue(val);
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    booleanVariable bv = new booleanVariable(name, vt);
    bv.setInitialValue(getValue());
    return bv;
  }
 */

  public sDouble getDoubleValue()
  {
    return null;
  }

  public void setValue(boolean b)
    throws IntolerableException
  {
    setValue(new sBoolean(b));
  }

  public Class<?> getValueClass()
  {
    return new sBoolean(true).getClass();
  }

}
