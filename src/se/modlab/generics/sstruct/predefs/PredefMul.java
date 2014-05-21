package se.modlab.generics.sstruct.predefs;

import java.util.Vector;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;

public class PredefMul implements ArithmeticEvaluable 
{

	private Vector<ArithmeticEvaluable> aes = new Vector<ArithmeticEvaluable>();
	private String filename = null;
	private int line = -1;
	private int column = -1;

	public PredefMul(ArithmeticEvaluable ae, String filename, int line, int column) {
		aes.add(ae);
		//System.out.println("predefSum: ctor. Length is "+aes.size());
		this.filename = filename;
		this.line = line;
		this.column = column;
	}

	public int getLine()
	{
		return line;
	}

	public String getFilename()
	{
		return filename;
	}

	public int getColumn()
	{
		return column;
	}

	public String getPlace() {
		return "file "+filename+", line "+line+", column "+column;
	}

	public void add(ArithmeticEvaluable ae) {
		aes.add(ae);
	}

	public sValue evaluate(Scope s) throws IntolerableException
	{
		ArithmeticEvaluable ae1 = aes.get(0);
		sValue prod = ae1.evaluate(s);
		if(prod == null) {
			throw new UserRuntimeError("No value for zeroeth argument to mul(...) at "+getPlace()+". Expression is "+ae1.reproduceExpression());
		}
		//System.out.println("predefSum: 0 : "+aes.get(0).reproduceExpression());
		for(int i = 1 ; i < aes.size() ; i++) {
			ArithmeticEvaluable ae = aes.get(i);
			//System.out.println("predefSum: "+i+" : "+aes.get(i).reproduceExpression());
			sValue val = ae.evaluate(s);
			if(val == null) {
				throw new UserRuntimeError("No value for argument number "+i+" to mul(...) at "+getPlace()+". Expression is "+ae1.reproduceExpression());
			}
			if((prod instanceof sLong) && (val instanceof sLong)) {
				long l1 = prod.getLong();
				long l2 = val.getLong();
				prod = new sLong(l1 * l2);
				//System.out.println("predefSum: sLong "+sum);
				continue;
			}
			double d1 = prod.getDouble();
			double d2 = val.getDouble();
			prod = new sDouble(d1 * d2);
			//System.out.println("predefSum: sDouble "+sum);
		}
		return prod;
	}

	public void verify(Scope s) throws IntolerableException {
		for(int i = 1 ; i < aes.size(); i++) {
			aes.get(i).verify(s);
		}
	}

	public String reproduceExpression() {
		StringBuffer sb = new StringBuffer();
		sb.append("mul("+aes.get(0).reproduceExpression());
		for(int i = 1 ; i < aes.size(); i++) {
			sb.append(", "+aes.get(i));
		}

		return sb.toString()+")";
	}

}

