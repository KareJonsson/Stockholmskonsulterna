package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class ArithmeticEvaluableDouble implements ArithmeticEvaluable
{

	private double val = 0; 

	public ArithmeticEvaluableDouble(double val)
	{
		this.val = val;
	}

	public sValue evaluate(Scope s)
	{
		return new sDouble(val);
	}

	public void verify(Scope s) throws IntolerableException {
	}
	
	public String reproduceExpression() {
		return ""+val;
	}

}
