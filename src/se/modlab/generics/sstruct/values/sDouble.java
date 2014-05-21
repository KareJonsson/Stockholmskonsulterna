package se.modlab.generics.sstruct.values;

import java.util.Date;

import se.modlab.generics.exceptions.*;

public class sDouble extends sValue
{

  public static final Class<?> _class = new sDouble().getClass();
  private double val;

  public sDouble()
  {
  }

  public sDouble(double d)
  {
    val = d;
  }

  public double getValue()
    throws IntolerableException
  {
    return val;
  }

  public Double getDouble()
  {
    return new Double(val);
  }

  public Long getLong()
  {
    return null;
  }

  public Boolean getBoolean()
  {
    return null;
  }

  public String getString()
  {
    return null;
  }

  public Date getDate()
  {
    return null;
  }

  public void setValue(double d)
  {
    val = d;
  }

  public sValue copy()
  {
    sDouble d = new sDouble(val);
    d.setTime(getTime());
    return d;
  }

  public boolean valueEquals(sValue sv)
    throws IntolerableException
  {
    if(sv instanceof sDouble)
    {
      return val == ((sDouble) sv).val;
    } 
    if(sv instanceof sLong)
    {
      return val == ((sLong) sv).getValue();
    } 
    throw new UserCompiletimeError(
      "You cannot operate with equality operators on\n"+
      this+" and "+sv);
  }

  public boolean less(sValue sv)
    throws IntolerableException
  {
    if(sv instanceof sDouble)
    {
      return val < ((sDouble) sv).val;
    } 
    if(sv instanceof sLong)
    {
      return val < ((sLong) sv).getValue();
    } 
    throw new UserCompiletimeError(
      "You cannot operate with magnitude operators on\n"+
      this+" and "+sv);
  }

  public boolean lessEq(sValue sv)
    throws IntolerableException
  {
    if(sv instanceof sDouble)
    {
      return val <= ((sDouble) sv).val;
    } 
    if(sv instanceof sLong)
    {
      return val <= ((sLong) sv).getValue();
    } 
    throw new UserCompiletimeError(
      "You cannot operate with magnitude operators on\n"+
      this+" and "+sv);
  }

  public Object getObject() {
	  return val;
  }

  public String toString()
  { 
    return ""+val+", type Double. Time "+getTime();
  }

}
