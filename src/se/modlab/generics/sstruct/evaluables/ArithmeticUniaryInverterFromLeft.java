package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class ArithmeticUniaryInverterFromLeft implements ArithmeticEvaluable
{

	private ArithmeticEvaluable ae;

	public ArithmeticUniaryInverterFromLeft(ArithmeticEvaluable ae)
	{
		this.ae  = ae;
	}

	public sValue evaluate(Scope s)
	throws IntolerableException
	{
		sValue v = ae.evaluate(s);
		if(v instanceof sLong)
		{
			sLong sl = (sLong) v;
			long l = sl.getLong().longValue();
			return new sLong(-l);
		}
		sDouble sd = (sDouble) v;
		double d = sd.getDouble().doubleValue();
		return new sDouble(-d);
	}

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}
	
	public String reproduceExpression() {
		return "!"+ae.reproduceExpression();
	}

}
