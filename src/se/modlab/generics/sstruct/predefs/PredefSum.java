package se.modlab.generics.sstruct.predefs;

import java.util.Vector;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;

public class PredefSum implements ArithmeticEvaluable 
{

	Vector<ArithmeticEvaluable> aes = new Vector<ArithmeticEvaluable>();

	public PredefSum(ArithmeticEvaluable ae)
	{
		aes.add(ae);
		//System.out.println("predefSum: ctor. Length is "+aes.size());
	}
	
	public void add(ArithmeticEvaluable ae) {
		aes.add(ae);
		//System.out.println("predefSum: add. Length is "+aes.size());
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		sValue sum = aes.get(0).evaluate(s);
		//System.out.println("predefSum: 0 : "+aes.get(0).reproduceExpression());
		for(int i = 1 ; i < aes.size() ; i++) {
			ArithmeticEvaluable ae = aes.get(i);
			//System.out.println("predefSum: "+i+" : "+aes.get(i).reproduceExpression());
			sValue val = ae.evaluate(s);
			if((sum instanceof sLong) && (val instanceof sLong)) {
				long l1 = sum.getLong();
				long l2 = val.getLong();
				sum = new sLong(l1 + l2);
				//System.out.println("predefSum: sLong "+sum);
				continue; 
			}
			double d1 = sum.getDouble();
			double d2 = val.getDouble();
			sum = new sDouble(d1 + d2);
			//System.out.println("predefSum: sDouble "+sum);
		}
		return sum;
	}

	public void verify(Scope s) throws IntolerableException {
		  for(int i = 1 ; i < aes.size(); i++) {
			  aes.get(i).verify(s);
		  }
	}
	
	  public String reproduceExpression() {
		  StringBuffer sb = new StringBuffer();
		  sb.append("sum("+aes.get(0).reproduceExpression());
		  for(int i = 1 ; i < aes.size(); i++) {
			  sb.append(", "+aes.get(i));
		  }
		  
		  return sb.toString()+")";
	  }

}
