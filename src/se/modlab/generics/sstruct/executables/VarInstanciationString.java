package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class VarInstanciationString extends VarInstanciation
{

	private StringEvaluable cse;

	public VarInstanciationString(String varName,
			String _filename, int _line, int _column,
			StringEvaluable _cse)
	{
		super(varName, _filename, _line, _column);
		cse = _cse;
	}

	public void execute(Scope s)
	throws ReturnException,
	IntolerableException,
	StopException,
	BreakException,
	ContinueException
	{
		//System.out.println("varInstanciationString name <"+this.varName+">");
		Variable var = new StringVariable(varName, s.getFactory("string"), filename, line, column);
		String val = null;
		if(cse != null)
		{
			val = cse.evaluate(s);
		}
		else
		{
			val = "";
		}
		var.setInitialValue(new sString(val));
		s.addVariable(var);
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = new StringVariable(varName, s.getFactory("string"), filename, line, column);
		if(cse != null)
		{
			cse.verify(s);
		}
		var.setDefaultInitialValue();
		s.addVariable(var);
	}

}