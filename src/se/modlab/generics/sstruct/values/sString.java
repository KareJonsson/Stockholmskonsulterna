package se.modlab.generics.sstruct.values;

import java.util.Date;

import se.modlab.generics.exceptions.*;

public class sString extends sValue
{

	public final static Class<?> _class = new sString().getClass();
	private String val;

	public sString()
	{
	}

	public sString(String _val)
	{
		this.val = _val;
	}

	public sValue copy()
	{
		sString s = new sString(val);
		return s;
	}

	public void setValue(String s)
	{
		val = s;
	}

	public double getValue()
			throws IntolerableException
			{
		throw new UserCompiletimeError("String value may not be "+
				"arithmetically evaluated.");
			}

	public String getString()
	{
		return val;
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
		return null;
	}

	public Date getDate()
	{
		return null;
	}

	public boolean equals(Object o)
	{
		if(o == null) {
			return false;
		}
		if(!(o instanceof sString)) return false;
		return val.compareTo(((sString) o).val) == 0;
	}

	public boolean valueEquals(sValue sv)
			throws IntolerableException
			{
		if(sv == null) {
			return val == null;
		}
		if(!(sv instanceof sString)) 
		{
			throw new UserCompiletimeError(
					"You cannot operate with equality operators on\n"+
							this+" and "+sv);
		}
		if(val == null) {
			if(((sString) sv).val == null) {
				return true; 
			}
			return false;
		}
		return val.compareTo(((sString) sv).val) == 0;
			}

	public boolean less(sValue sv)
			throws IntolerableException
			{
		if(val == null) {
			if(sv == null) {
				return false; // Equals hence not less
			}
			if(!(sv instanceof sString)) 
			{
				throw new UserCompiletimeError(
						"You cannot operate with magnitude operators on\n"+
								this+" and "+sv);
			}
			if(((sString) sv).val == null) {
				return false; // Equals, hence not less
			}
			return true;
		}
		if(sv == null) {
			return false;
		}
		if(!(sv instanceof sString)) 
		{
			throw new UserCompiletimeError(
					"You cannot operate with magnitude operators on\n"+
							this+" and "+sv);
		}
		if(((sString) sv).val == null) {
			return false; 
		}
		return val.compareTo(((sString) sv).val) < 0;
			}

	public boolean lessEq(sValue sv)
			throws IntolerableException {
		if(val == null) {
			return true;
		}
		if(sv == null) {
			return false;
		}
		if(!(sv instanceof sString)) 
		{
			throw new UserCompiletimeError(
					"You cannot operate with magnitude operators on\n"+
							this+" and "+sv);
		}
		if(((sString) sv).val == null) {
			return false; 
		}
		return val.compareTo(((sString) sv).val) <= 0;
	}

	public Object getObject() {
		return val;
	}

	public String toString()
	{
		return ""+val+", type String";
	}

}