package se.modlab.generics.sstruct.variables;

import java.util.Date;

import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.tables.BooleanHolder;
import se.modlab.generics.sstruct.tables.Holder;
import se.modlab.generics.sstruct.tables.LongHolder;
import se.modlab.generics.sstruct.tables.StringHolder;
import se.modlab.generics.sstruct.tables.DatetimeHolder;
import se.modlab.generics.sstruct.values.sBoolean;
import se.modlab.generics.sstruct.values.sDate;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sString;
import se.modlab.generics.sstruct.values.sValue;

public class DateVariable extends Variable
{
	  
	  public DateVariable(String name, VariableType vt, String _filename, int _line, int _column)
	  {
		    super(name, vt, _filename, _line, _column);
	  }

	  public void setDefaultInitialValue()
	    throws IntolerableException
	  {
	    //System.out.println("SimDoubleVariable - setDefaultInitialValue - "+name);
	    //new Throwable().printStackTrace();
	    //System.exit(0);
	    setInitialValue(new sDate()); 
	  }

	  public void setInitialValue(Holder val) 
	    throws IntolerableException
	  {
	    if(val instanceof DatetimeHolder)
	    {
	      Date d = new Date(((DatetimeHolder) val).getDatetime());
	      setInitialValue(new sDate(d));
	      return;
	    }
	    if(val instanceof LongHolder)
	    {
	      Long l = ((LongHolder) val).getLong();
	      setInitialValue(new sDate(new Date(l)));
	      return;
	    }
	    if(val instanceof BooleanHolder)
	    {
	      throw new UserRuntimeError(
	        "The "+val+" was supposed to match a variable of type Date.\n"+
	        "That is not properly defined."
	        +"\n"+"This happened to the variable declared "+getPlace());
	    }
	    if(val instanceof StringHolder)
	    {
	      throw new UserRuntimeError(
	        "The "+val+" was supposed to match a variable of type Date.\n"+
	        "That is not properly defined."
	        +"\n"+"This happened to the variable declared "+getPlace());
	    }
	    throw new InternalProgrammingError(
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
	    if(initialVal instanceof sBoolean) {
	      throw new UserRuntimeError(
	        "Date variable "+name+" set to boolean value "+initialVal
	        +"\n"+"This happened to the variable declared "+getPlace());
	    }
	    if(initialVal instanceof sLong) {
	    	initialVal = new sDate(new Date(initialVal.getLong().longValue()));
	        val = initialVal.copy();
	    	return;
	    }
	    if(initialVal instanceof sDate) {
	    	initialVal = (sDate) initialVal;//new sDate(new Date(initialVal.getLong().longValue()));
	        val = initialVal.copy();
	    	return;
	    }
	    //initialValue = initialVal.copy();
	    //val = new sDate(initialVal.getValue());
	    //System.out.println("Double setInitialValue. "+s+", efter "+this.toString());
	      throw new InternalProgrammingError(
	  	        "Internal: Variable "+name+" had value "+val+"\n"+
	  	        "prior to call to setInitialValue with value "+initialVal);         
	  }

	  public void setValue(sValue val) 
	    throws IntolerableException
	  {
	    if(this.val == null)
	    {
	      throw new InternalProgrammingError(
	        "Variable "+name+" had no value \n"+
	        "prior to call to setValue with value "+val);         
	    }
	    if(val instanceof sDate) {
	      this.val = val;
	      return;
	    }
	    if(val instanceof sLong) {
	      this.val = new sDate(new Date(((sLong)val).getLong()));
	      return;
	    }
	    if(val instanceof sBoolean) {
		      throw new UserRuntimeError(
		        "Date variable "+name+" set to boolean value "+val
		        +"\n"+"This happened to the variable declared "+getPlace());
		    }
	    if(val instanceof sDouble) {
		      throw new UserRuntimeError(
		        "Date variable "+name+" set to double value "+val
		        +"\n"+"This happened to the variable declared "+getPlace());
		}
	    if(val instanceof sString) {
		      throw new UserRuntimeError(
		        "Date variable "+name+" set to string value "+val
		        +"\n"+"This happened to the variable declared "+getPlace());
		}
	      throw new InternalProgrammingError(
			        "Date variable "+name+" set to unhandled value "+val
			        +"\n"+"This happened to the variable declared "+getPlace());
	  }
	 
	  public void setValue(VariableInstance si)
	    throws IntolerableException
	  {
	    if(!(si instanceof DateVariable))
	    {
	      throw new InternalProgrammingError( 
	        "doubleVariable set to value of \n"+
	        "type "+si.getType().getTypesName());
	    }
	    setValue(((DateVariable) si).getValue());
	  }

	  public void setInitialValue(VariableInstance si) 
	    throws IntolerableException
	  {
	    sValue v = ((DateVariable) si).getValue();
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

	  public sDate getDateValue()
	  {
	    return (sDate) val;
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
