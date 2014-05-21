package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.comparisons.VariableLookup;
import se.modlab.generics.sstruct.evaluables.*;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;
import se.modlab.generics.sstruct.variables.*;

public class PredefLength implements ArithmeticEvaluable 
{ 

	protected ArithmeticEvaluable ae;

	public PredefLength(ArithmeticEvaluable _ae)
	{
		ae = _ae;
	}

	public sValue evaluate(Scope s) 
	throws IntolerableException
	{
		if(!(ae instanceof ArithmeticEvaluableVariable)) {
			//System.out.println("predefLength class "+ae.reproduceExpression());
			throw new UserRuntimeError(
					"Expression within lengths parenthesises must evaluate to a vector.");
		}
		VariableLookup _vl = ((ArithmeticEvaluableVariable) ae).getVariableReference();
		VariableInstance sv = _vl.getInstance(s);
		if(sv instanceof VariableVector) {
			VariableVector svv = (VariableVector) sv;
			long l = svv.getLength();
			//System.out.println("Class predeflength.evaluate vector class="+svv.getClass().getName()+" length="+l+", toString="+svv.toString());
			return new sLong(l);
		}
		if(sv instanceof VariableVectorFromFile) {
			VariableVectorFromFile svvff = (VariableVectorFromFile) sv;
			long l = svvff.getLength();
			return new sLong(l);
		}
		if(sv == null) {
			throw new UserCompiletimeError(
					"Variable "+_vl+" is not known");
		}
		throw new UserRuntimeError(
		"Expression within lengths parenthesises must evaluate to a vector.");
		//return null;
	}

	public void verify(Scope s) throws IntolerableException {
		if(!(ae instanceof ArithmeticEvaluableVariable))
		{
			throw new UserRuntimeError(
					"Expression within lengths parenthesises must evaluate to a vector.");
		}
		VariableLookup _vl = ((ArithmeticEvaluableVariable) ae).getVariableReference();
		VariableInstance sv = _vl.getInstance(s);
		if(sv instanceof VariableVector)
		{
			//VariableVector svv = (VariableVector) sv;
			//long l = svv.getLength();
			return;
		}
		if(sv instanceof VariableVectorFromFile)
		{
			//VariableVectorFromFile svvff = (VariableVectorFromFile) sv;
			//long l = svvff.getLength();
			return;
		}
		if(sv == null)
		{
			throw new UserCompiletimeError(
					"Variable "+_vl+" is not known");
		}
		throw new UserRuntimeError(
		"Expression within lengths parenthesises must evaluate to a vector.");
		//return null;
	}
	
	  public String reproduceExpression() {
		  return "length("+ae.reproduceExpression()+")";
	  }


}
