package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;

public class PredefRound implements ArithmeticEvaluable 
{

	ArithmeticEvaluable ae;

	public PredefRound(ArithmeticEvaluable ae) {
		this.ae = ae;
	}

	private static long performArithmetic(double d) {
		long l = Math.round(d);
		return l;
	}

	public sValue evaluate(Scope s) throws IntolerableException {
		sValue val = ae.evaluate(s);
		return new sLong(performArithmetic(val.getValue()));
	}

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}

	public String reproduceExpression() {
		return "trunc("+ae.reproduceExpression()+")";
	}

	private static void doit(double d) {
		System.out.println("d="+d+", out="+performArithmetic(d));
	}
	
	public static void main(String args[]) {
		doit(0.001);
		doit(0.5);
		doit(1.5);
		doit(1.500001);
		doit(2.500001);
		doit(1.5001);
		doit(2.5001);
		doit(1.51);
		doit(2.51);
		doit(1.61);
		doit(2.71);
		doit(2.81);
		doit(2.91);
		doit(0.499);
		doit(1.499);
	}

}
