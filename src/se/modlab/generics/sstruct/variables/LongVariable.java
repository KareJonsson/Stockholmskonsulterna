package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.tables.*;
import se.modlab.generics.sstruct.values.*;

public class LongVariable extends Variable
{
  
  public LongVariable(String name, VariableType vt, String _filename, int _line, int _column)
  {
    super(name, vt, _filename, _line, _column);
  }

  public void setDefaultInitialValue()
    throws IntolerableException
  {
    setInitialValue(new sLong(0));
  }

  public void setInitialValue(Holder val) 
    throws IntolerableException
  {
    if(val instanceof LongHolder)
    {
      long l = ((LongHolder) val).getLong();
      //System.out.println("SL l "+val+" ger "+l);
      setInitialValue(new sLong(l));
      return;
    }
    if(val instanceof DoubleHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type long.\n"+
        "That is not properly defined."
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    if(val instanceof StringHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type long.\n"+
        "That is not properly defined."
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    if(val instanceof BooleanHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type long.\n"+
        "That is not properly defined."
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    throw new InternalError(
      "Logics in method "+
      "longVariable.setInitialValue(holder val)\n"+
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
    if(initialVal instanceof sBoolean) 
    {
      throw new UserRuntimeError(
        "Long variable "+name+" set to boolean value "+initialVal
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    //initialValue = initialVal.copy();
    if(!(initialVal instanceof sLong)) 
    {
      throw new UserRuntimeError(
        "Long variable "+name+" set to non long value "+val
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    val = new sLong(((sLong)initialVal).getLong().longValue());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    if(!(val instanceof sLong)) 
      throw new UserRuntimeError(
        "Long variable "+name+" set to non long value "+val
        +"\n"+"This happened to the variable declared "+getPlace());
    this.val = val;
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    if(!(si instanceof LongVariable))
    {
      throw new InternalError( 
        "longVariable set to value of \n"+
        "type "+si.getType().getTypesName());
    }
    setValue(((LongVariable) si).getValue());
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
    longVariable lv = new longVariable(name, vt);
    lv.setInitialValue(getValue());
    return lv;
  }
 */

  public sDouble getDoubleValue()
  {
    return null;
  }

  public void setValue(double d)
    throws IntolerableException
  {
    throw new UserRuntimeError(
      "Long variable "+name+" set to non long value "+d
      +"\n"+"This happened to the variable declared "+getPlace());
  }

  public Class<?> getValueClass()
  {
    return new sLong(0).getClass();
  }

}
