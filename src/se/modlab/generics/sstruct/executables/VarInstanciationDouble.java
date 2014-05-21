package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class VarInstanciationDouble extends VarInstanciation
{

	private ArithmeticEvaluable ae;

	public VarInstanciationDouble(String varName, 
			String _filename, int _line, int _column,
			ArithmeticEvaluable _ae)
	{
		super(varName, _filename, _line, _column);
		ae = _ae;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		Variable var = new DoubleVariable(varName, s.getFactory("double"), filename, line, column);
		sValue val = null;
		if(ae != null)
		{
			val = ae.evaluate(s);
		}
		else
		{
			val = new sDouble(0);
		}
		var.setInitialValue(val);
		s.addVariable(var);
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = new DoubleVariable(varName, s.getFactory("double"), filename, line, column);
		if(ae != null)
		{
			ae.verify(s);
		}
		var.setDefaultInitialValue();
		s.addVariable(var);
	}

}
