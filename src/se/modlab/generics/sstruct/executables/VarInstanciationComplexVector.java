package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class VarInstanciationComplexVector extends VarInstanciation
{

	private String type;
	private ArithmeticEvaluable ae;

	public VarInstanciationComplexVector(String varName, 
			String _filename, int _line, int _column,
			String _type,
			ArithmeticEvaluable _ae)
	{
		super(varName, _filename, _line, _column);
		type = _type;
		ae = _ae;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		VariableFactory vf = s.getFactory(type);
		int length = ae.evaluate(s).getLong().intValue();
		VariableFactory _vf = (VariableFactory) s.addFactoryUniquely(
				new VariableVectorFactory(vf, length));
		VariableInstance si = _vf.getInstance(varName, filename, line, column);
		si.setDefaultInitialValue();
		s.addComplexInstance(si);
	}

	public void verify(Scope s) throws IntolerableException {
		VariableFactory vf = s.getFactory(type);
		int length = ae.evaluate(s).getLong().intValue();
		VariableFactory _vf = (VariableFactory) s.addFactoryUniquely(
				new VariableVectorFactory(vf, length));
		VariableInstance si = _vf.getInstance(varName, filename, line, column);
		si.setDefaultInitialValue();
		s.addComplexInstance(si);
	}

}
