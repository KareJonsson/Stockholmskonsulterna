package se.modlab.generics.sstruct.values;

import java.util.Date;

import se.modlab.generics.exceptions.*;

public class sBoolean extends sValue
{

  public final static Class<?> _class = new sBoolean().getClass();
  private boolean val;
  
  public sBoolean()
  {
  }
 
  public sBoolean(boolean b)
  {
    val = b;
  }
 
  public double getValue()
    throws IntolerableException
  {
    //new Throwable().printStackTrace();
    throw new UserCompiletimeError("Boolean value may not be "+
                        "arithmetically evaluated.");
  }

  public Long getLong()
  {
    return null;
  }

  public Double getDouble()
  {
    return null;
  }

  public Boolean getBoolean()
  {
    return new Boolean(val);
  }

  public String getString()
  {
    return null;
  }

  public Date getDate()
  {
    return null;
  }

  public void setValue(boolean b)
  {
    val = b;
  }

  public sValue copy()
  {
    sBoolean b = new sBoolean(val);
    b.setTime(getTime());
    return b;
  }

  public boolean valueEquals(sValue sv)
    throws IntolerableException
  {
    if(!(sv instanceof sBoolean)) 
    {
      throw new UserCompiletimeError(
        "You cannot operate with equality operators on\n"+
        this+" and "+sv);
    }
    return val == ((sBoolean) sv).val;
  }

  public boolean less(sValue sv)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "You cannot operate with magnitude operators on\n"+
      this+" and "+sv);
  }

  public boolean lessEq(sValue sv)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "You cannot operate with magnitude operators on\n"+
      this+" and "+sv);
  }
  
  public Object getObject() {
	  return val;
  }

  public String toString()
  { 
    return ""+val+", type Boolean. Time "+getTime();
  }

}
