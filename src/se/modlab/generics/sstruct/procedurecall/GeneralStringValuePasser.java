package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.strings.StringEvaluable;
import se.modlab.generics.sstruct.values.sString;
import se.modlab.generics.sstruct.variables.StringVariable;

public class GeneralStringValuePasser extends UntypedArgumentPasser
{
	private StringEvaluable se = null;

	public GeneralStringValuePasser(String _filename, int _line, int _column, StringEvaluable _se) {
		super(_filename, _line, _column);
		se = _se;
	}

	public void passArgument(Scope from, Scope to, UntypedAliasing ua)
	throws IntolerableException
	{
		if(ua.isReferenced()) {
			throw new UserCompiletimeError("Value from literal expression is passed to referenced variable at "+getPlaceString());
		}
		if(!ua.getTypename().toUpperCase().equals("STRING")) {
			throw new UserCompiletimeError("Value from literal expression is passed to non literal parameter at "+getPlaceString());
		}
		if(ua.isArray()) {
			throw new UserCompiletimeError("Value from literal expression is passed to array parameter at "+getPlaceString());
		}
		String val = se.evaluate(from);
		StringVariable sv = new StringVariable(ua.getVarname(), to.getFactory("string"), filename, line, column);
		sv.setInitialValue(new sString(val));
		to.addVariable(sv);
	}

	public String getPlaceString() {
		return "in file "+filename+", line "+line+", column "+column;
	}

}