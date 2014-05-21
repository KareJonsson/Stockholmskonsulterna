package se.modlab.generics.sstruct.values;

import java.util.Date;

import se.modlab.generics.exceptions.*;

public class sLong extends sValue
{

  public final static Class<?> _class = new sLong().getClass();
  private long val;
  
  public sLong()
  {
  }
 
  public sLong(long l)
  {
    val = l;
  }
 
  public double getValue()
    throws IntolerableException
  {
    return (double) val;
  }

  public Long getLong()
  {
    return new Long(val);
  }

  public Double getDouble()
  {
    return new Double(val);
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

  public void setValue(long l)
  {
    val = l;
  }

  public sValue copy()
  {
    sLong l = new sLong(val);
    l.setTime(getTime());
    return l;
  }

  public boolean valueEquals(sValue sv)
    throws IntolerableException
  {
    if(sv instanceof sDouble)
    {
      return val == ((sDouble) sv).getValue();
    } 
    if(sv instanceof sLong)
    {
      return val == ((sLong) sv).val;
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
      return val < ((sDouble) sv).getValue();
    } 
    if(sv instanceof sLong)
    {
      return val < ((sLong) sv).val;
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
      return val <= ((sDouble) sv).getValue();
    } 
    if(sv instanceof sLong)
    {
      return val <= ((sLong) sv).val;
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
    return ""+val+", type Long. Time "+getTime();
  }

}
