package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.tables.*;
import se.modlab.generics.sstruct.values.*;

public class DoubleVariable extends Variable
{
  
  public DoubleVariable(String name, VariableType vt, String _filename, int _line, int _column)
  {
	    super(name, vt, _filename, _line, _column);
  }

  public void setDefaultInitialValue()
    throws IntolerableException
  {
    //System.out.println("SimDoubleVariable - setDefaultInitialValue - "+name);
    //new Throwable().printStackTrace();
    //System.exit(0);
    setInitialValue(new sDouble(0.0));
  }

  public void setInitialValue(Holder val) 
    throws IntolerableException
  {
    if(val instanceof LongHolder)
    {
      long l = ((LongHolder) val).getLong();
      setInitialValue(new sDouble((double)l));
      return;
    }
    if(val instanceof DoubleHolder)
    {
      double d = ((DoubleHolder) val).getDouble();
      setInitialValue(new sDouble(d));
      return;
    }
    if(val instanceof BooleanHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type double.\n"+
        "That is not properly defined."
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    if(val instanceof StringHolder)
    {
      throw new UserRuntimeError(
        "The "+val+" was supposed to match a variable of type double.\n"+
        "That is not properly defined."
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    throw new InternalError(
      "Logics in method " +
      "doubleVariable.setInitialValue(holder val)\n"+
      "is outdated.");
  }

  public void setInitialValue(sValue initialVal) 
    throws IntolerableException
  {
    //System.out.println("SimDoubleVariable - setInitialValue - "+name+
    //                   " - "+initialVal);
    //new Throwable().printStackTrace();
    //System.exit(0);
/*
    if(val != null)
    {
      throw new intolerableException(
        "Internal: Variable "+name+" had value "+val+"\n"+
        "prior to call to setInitialValue with value "+initialVal);         
    }
 */
    //String s = "Innan: "+this.toString();
    if(initialVal instanceof sBoolean) 
    {
      throw new UserRuntimeError(
        "Double variable "+name+" set to boolean value "+initialVal
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    if(initialVal instanceof sLong) 
    {
      initialVal = new sDouble(initialVal.getLong().longValue());
    }
    //initialValue = initialVal.copy();
    val = new sDouble(initialVal.getValue());
    //System.out.println("Double setInitialValue. "+s+", efter "+this.toString());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    if(this.val == null)
    {
      throw new InternalError(
        "Variable "+name+" had no value \n"+
        "prior to call to setValue with value "+val);         
    }
    if(val instanceof sDouble) 
    {
      this.val = val;
      return;
    }
    if(val instanceof sBoolean) 
    {
      throw new UserRuntimeError(
        "Double variable "+name+" set to boolean value "+val
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    double tmp = val.getValue(); // Long value is transformed.
    this.val = new sDouble(tmp);
  }
 
  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    if(!(si instanceof DoubleVariable))
    {
      throw new InternalError( 
        "doubleVariable set to value of \n"+
        "type "+si.getType().getTypesName());
    }
    setValue(((DoubleVariable) si).getValue());
  } 

  public void setInitialValue(VariableInstance si) 
    throws IntolerableException
  {
    sValue v = ((DoubleVariable) si).getValue();
    if(v instanceof sDouble) 
    {
      val = v;
      return;
    }
    if(val instanceof sBoolean) 
    {
      throw new UserRuntimeError(
        "Double variable "+name+" set to boolean value "+v
        +"\n"+"This happened to the variable declared "+getPlace());
    }
    double tmp = v.getValue(); // Long value is transformed.
    val = new sDouble(tmp);
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    doubleVariable dv = new doubleVariable(name, vt);
    dv.setInitialValue(getValue());
    return dv;
  }
 */

  public sDouble getDoubleValue()
  {
    return (sDouble) val;
  }

  public void setValue(double d)
    throws IntolerableException
  {
    setValue(new sDouble(d));
  }

  public Class<?> getValueClass()
  {
    return new sDouble(0).getClass();
  }

}
