package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;
import se.modlab.generics.sstruct.variables.DoubleVariable;
import se.modlab.generics.sstruct.variables.LongVariable;

public class GeneralArithmeticValuePasser extends UntypedArgumentPasser
{
	private ArithmeticEvaluable ae = null;

	public GeneralArithmeticValuePasser(String _filename, int _line, int _column, ArithmeticEvaluable _ae) {
		super(_filename, _line, _column);
		ae = _ae;
	}

	public void passArgument(Scope from, Scope to, UntypedAliasing ua)
	throws IntolerableException
	{
		//System.out.println("GeneralArithmeticValuePasser.passArgument "+ua);
		if(ua.isReferenced()) {
			throw new UserCompiletimeError("Value from arithmetic expression is passed to referenced variable at "+getPlaceString());
		}
		if(ua.getTypename().compareTo("long") != 0 && ua.getTypename().compareTo("double") != 0) {
			throw new UserCompiletimeError("Value from arithmetic expression is passed to non arithmetic parameter at "+getPlaceString());
		}
		if(ua.isArray()) {
			throw new UserCompiletimeError("Value from arithmetic expression is passed to array parameter at "+getPlaceString());
		}
		sValue val = ae.evaluate(from);
		if(val instanceof sLong) {
			//System.out.println("GeneralArithmeticValuePasser.passArgument (val instanceof sLong) "+val);
			if(ua.getTypename().compareTo("double") == 0) {
				//System.out.println("GeneralArithmeticValuePasser.passArgument (val instanceof sLong) "+val+", type double");
				DoubleVariable dv = new DoubleVariable(ua.getVarname(), to.getFactory("double"), filename, line, column);
				dv.setInitialValue(val);
				to.addVariable(dv);
				return;
			}
			//System.out.println("GeneralArithmeticValuePasser.passArgument (val instanceof sLong) "+val+", type long");
			LongVariable lv = new LongVariable(ua.getVarname(), to.getFactory("long"), filename, line, column);
			lv.setInitialValue(val);
			to.addVariable(lv);
			return;
		}
		if(val instanceof sDouble) {
			//System.out.println("GeneralArithmeticValuePasser.passArgument (val instanceof sDouble) "+val+", type double");
			DoubleVariable dv = new DoubleVariable(ua.getVarname(), to.getFactory("double"), filename, line, column);
			dv.setInitialValue(val);
			to.addVariable(dv);
			return;
		}
		throw new InternalProgrammingError(
				"No handling of type "+val.getClass().getName()+" in class GeneralArithmeticValuePasser");
	}

	public String getPlaceString() {
		return "in file "+filename+", line "+line+", column "+column;
	}

}