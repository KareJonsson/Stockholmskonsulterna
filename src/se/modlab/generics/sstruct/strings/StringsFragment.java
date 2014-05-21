package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class StringsFragment implements StringEvaluable
{

	private StringEvaluable text;
	private ArithmeticEvaluable column;

	public StringsFragment(StringEvaluable _text, ArithmeticEvaluable _column)
	{
		this.text = _text;
		this.column = _column;
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		String line = text.evaluate(s);
		String piece[] = line.trim().split("[' '|'\t']+");
		sValue fragidx = column.evaluate(s);
		Long L_f = fragidx.getLong();
		if(L_f == null)
		{
			throw new UserCompiletimeError("Argument 2 to fragment did not evaluate to long (integer) value");
		}
		long f = L_f.longValue();
		if(f < 0)
		{
			throw new UserCompiletimeError("Argument 2 to fragment evaluated to negative value "+f);
		}
		if(piece.length <= f)
		{
			throw new UserCompiletimeError("Argument 2 to fragment evaluated to "+f+" but there are\n"+
					"only "+(piece.length-1)+" fragments. The text is "+line);
		}
		//System.out.println("FRAGMENT : "+piece[(int)f]);
		return piece[(int)f].trim();
	}

	public void verify(Scope s) throws IntolerableException {
		text.verify(s);
		column.verify(s);
	}
	
	public String reproduceExpression() {
		return "fragment("+text.reproduceExpression()+", "+column.reproduceExpression()+")";
	}


}