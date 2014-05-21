package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class StringValue implements ArithmeticEvaluable
{

	private StringEvaluable cse;

	public StringValue(StringEvaluable _cse)
	{
		this.cse = _cse;
	}

	private Integer getAsInteger(String s)
	{
		try
		{
			return new Integer(Integer.parseInt(s));
		}
		catch(Exception e)
		{
			return null;
		}
	}

	private Double getAsDouble(String s)
	{
		try
		{
			return new Double(Double.parseDouble(s));
		}
		catch(Exception e)
		{
			return null;
		}
	}

	private int getFirstOccurranceOfDigit(String fragment)
	{
		for(int i = 0 ; i < fragment.length() ; i++)
		{
			char c = fragment.charAt(i);
			if((c >= '0') &&
					(c <= '9'))
				return i;
		}
		return -1;
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		String _line = cse.evaluate(s);
		if(_line == null)
		{
			throw new UserRuntimeError("String worth statement in value(...) not evaluable.");
		}
		String line = _line.trim();

		int firstDigit = getFirstOccurranceOfDigit(line);
		if(firstDigit == -1)
		{
			throw new UserRuntimeError("value("+line+") makes no sence!");
		}

		line = line.substring(firstDigit);

		//System.out.println("VALUE : >"+line+"<");
		for(int i = line.length() ; i > 0 ; i--)
		{
			Integer I = getAsInteger(line.substring(0, i));
			if(I != null) return new sLong(I.intValue());
			Double D = getAsDouble(line.substring(0, i));
			if(D != null) return new sDouble(D.doubleValue());
		}
		throw new UserRuntimeError("Line "+line+" not interpretable as arithmetic value");
	}

	public void verify(Scope s) throws IntolerableException {
		cse.verify(s);
	}
	
	  public String reproduceExpression() {
		  return cse.reproduceExpression(); 
	  }

}