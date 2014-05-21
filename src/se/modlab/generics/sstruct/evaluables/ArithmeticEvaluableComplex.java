package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.arithmetics.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;

public class ArithmeticEvaluableComplex implements ArithmeticEvaluable
{

	private ArithmeticEvaluable ae_l = null;
	private ArithmeticOperator ao = null;
	private ArithmeticEvaluable ae_r = null;

	public ArithmeticEvaluableComplex(ArithmeticEvaluable ae_l,
			ArithmeticOperator ao,
			ArithmeticEvaluable ae_r)
	{
		this.ae_l = ae_l;
		this.ao = ao;
		this.ae_r = ae_r;
	}

	public sValue evaluate(Scope s)
	throws IntolerableException
	{
		sValue l = ae_l.evaluate(s);
		sValue r = ae_r.evaluate(s);
		return ao.operate(l, r);
	}

	public void verify(Scope s) throws IntolerableException {
		ae_l.verify(s);
		ae_r.verify(s);
	}
	
	  public String reproduceExpression() {
		  return "("+ae_l.reproduceExpression()+ao.reproduceExpression()+ae_r.reproduceExpression()+")";
	  }


}
