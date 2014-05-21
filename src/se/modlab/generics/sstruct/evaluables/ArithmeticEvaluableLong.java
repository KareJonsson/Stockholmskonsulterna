package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class ArithmeticEvaluableLong implements ArithmeticEvaluable
{

	private long val = 0; 

	public ArithmeticEvaluableLong(long val)
	{
		this.val = val;
	}

	public sValue evaluate(Scope s)
	{
		return new sLong(val);
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	public String reproduceExpression() {
		return ""+val;
	}


}
