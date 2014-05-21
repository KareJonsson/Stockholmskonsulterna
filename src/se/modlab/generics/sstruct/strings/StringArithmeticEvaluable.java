package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class StringArithmeticEvaluable implements StringEvaluable
{

	private ArithmeticEvaluable ae;

	public StringArithmeticEvaluable(ArithmeticEvaluable ae)
	{
		this.ae = ae;
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		sValue val = ae.evaluate(s);
		//System.out.println("ccArithmeticEvaluable. "+ae+"\n"+
		//                   "Class: "+ae.getClass().getName()+"\n"+
		//                   "Value: "+val);
		Long l = val.getLong();
		if(l != null) return l.toString();
		Double d = val.getDouble();
		if(d != null) return d.toString();
		throw new InternalError("No contents in value from arithmetic expression. "+ae.reproduceExpression());
		//return null;
	}

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}
	
	public String reproduceExpression() {
		  return ae.reproduceExpression(); 
	}

}