package se.modlab.generics.sstruct.comparisons;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class ComparisonOperatorNotEqual extends ComparisonOperator
{

	public ComparisonOperatorNotEqual(String _filename, int _line, int _column)
	{
		super("!=", _filename, _line, _column);
	}

	public boolean compare(ArithmeticEvaluable ae_l, ArithmeticEvaluable ae_r, Scope s) 
			throws IntolerableException {
		sValue v_l = ae_l.evaluate(s);
		sValue v_r = ae_r.evaluate(s);
		if(v_l == null && v_r == null) {
			return false;
		}
		if(v_l != null && v_r != null) {
			try {
				return !v_l.valueEquals(v_r);
			}
			catch(UserRuntimeError ue) {
				throw new UserRuntimeError(this.toString()+"\n\n"+"Happened on "+getPlace()+"\n"+
						ue.getMessage(),
						ue.getThrowable(),
						ue.getCollectors());
			}
			catch(UserCompiletimeError ue) {
				throw new UserCompiletimeError(this.toString()+"\n\n"+"Happened on "+getPlace()+"\n"+
						ue.getMessage(),
						ue.getThrowable(),
						ue.getCollectors());
			}
			catch(Exception e) {
				throw new UserRuntimeError(this.toString()+"\n\n"+"Happened on "+getPlace()+"\n"+
						e.getMessage(), e);
			}
		}
		return true;
	}

}
