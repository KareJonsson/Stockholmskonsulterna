package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.logics.LogicalExpression;
import se.modlab.generics.sstruct.values.sBoolean;
import se.modlab.generics.sstruct.variables.BooleanVariable;

public class GeneralLogicalValuePasser extends UntypedArgumentPasser
{
	private LogicalExpression le = null;

	public GeneralLogicalValuePasser(String _filename, int _line, int _column, LogicalExpression _le) {
		super(_filename, _line, _column);
		le = _le;
	}

	public void passArgument(Scope from, Scope to, UntypedAliasing ua)
	throws IntolerableException
	{
		if(ua.isReferenced()) {
			throw new UserCompiletimeError("Value from logical expression is passed to refrenced variable at "+getPlaceString());
		}
		if(!ua.getTypename().toUpperCase().equals("BOOLEAN")) {
			throw new UserCompiletimeError("Value from logical expression is passed to non boolean parameter at "+getPlaceString());
		}
		if(ua.isArray()) {
			throw new UserCompiletimeError("Value from logical expression is passed to array parameter at "+getPlaceString());
		}
		boolean val = le.evaluate(from);
		BooleanVariable bv = new BooleanVariable(ua.getVarname(), to.getFactory("boolean"), filename, line, column);
		bv.setInitialValue(new sBoolean(val));
		to.addVariable(bv);
	}

	public String getPlaceString() {
		return "in file "+filename+", line "+line+", column "+column;
	}

}
