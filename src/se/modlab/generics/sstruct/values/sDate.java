package se.modlab.generics.sstruct.values;

import java.util.Date;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;

public class sDate extends sValue
{

	  public static final Class<?> _class = Date.class;
	  private Date val;

	  public sDate()
	  {
	  }

	  public sDate(Date d)
	  {
	    val = d;
	  }

	  public double getValue()
	    throws IntolerableException
	  {
	    return (Double) null;
	  }

	  public Double getDouble()
	  {
	    return null;
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
	    return val;
	  }

	  public void setValue(Date d)
	  {
	    val = d;
	  }

	  public sValue copy()
	  {
	    sDate d = new sDate(val);
	    d.setTime(getTime());
	    return d;
	  }

	  public boolean valueEquals(sValue sv)
	    throws IntolerableException
	  {
	    if(sv instanceof sDate) {
	      return val == ((sDate) sv).val;
	    } 
	    throw new UserCompiletimeError(
	      "You cannot operate with equality operators on\n"+
	      this+" and "+sv);
	  }

	  public boolean less(sValue sv)
	    throws IntolerableException
	  {
	    if(sv instanceof sDate)
	    {
	      return val.getTime() < ((sDate) sv).val.getTime();
	    } 
	    throw new UserCompiletimeError(
	      "You cannot operate with magnitude operators on\n"+
	      this+" and "+sv);
	  }

	  public boolean lessEq(sValue sv)
	    throws IntolerableException
	  {
	    if(sv instanceof sDate)
	    {
	      return val.getTime() <= ((sDate) sv).val.getTime();
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
	    return ""+val+", type Date. Time "+getTime();
	  }

	}
