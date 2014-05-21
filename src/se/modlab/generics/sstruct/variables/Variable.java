package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.tables.*;
import se.modlab.generics.sstruct.values.*;

abstract public class Variable extends VariableInstance
{

  //protected SimValue initialValue;
  protected sValue val;

  public Variable(String name, VariableType vt, String _filename, int _line, int _column) {
    super(name, vt, _filename, _line, _column);
  }

  public sValue getValue() throws IntolerableException {
    if(val == null) {
      throw new InternalError( 
        "Variable.getValue called with no value set prior to get.\n"+
        "Happened in class "+getClass().getName()+".\nName of variable "+
        "is "+name);
    }
    return val;
  }
  
  public boolean isNull() {
	  return val == null;
  }

  abstract public void setInitialValue(sValue val) throws IntolerableException;

  abstract public void setInitialValue(Holder val) throws IntolerableException;

  abstract public void setValue(sValue val) throws IntolerableException;

  public String toString() {
	  //variableType vt = getType();
    return "Name "+name+", type "+((vt != null) ? vt.getTypesName() : "not set")+", value "+val;
  }

  public abstract Class<?> getValueClass();
  
  public boolean valueEquals(VariableInstance sv) throws IntolerableException {
	  if(!(sv instanceof Variable)) {
		  return false;
	  }
	  Variable other = (Variable) sv;
	  return val.valueEquals(other.getValue());
  }

}
