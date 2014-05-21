package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class VarInstanciationComplex extends VarInstanciation
{

	private String type;
	private VariableLookup vl;

	public VarInstanciationComplex(String varName, 
			String _filename, int _line, int _column,
			String _type,
			VariableLookup _vl)
	{
		super(varName, _filename, _line, _column);
		type = _type;
		vl = _vl;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		VariableFactory vf = s.getFactory(type);
		VariableInstance si = vf.getInstance(varName, filename, line, column);
		if(vl == null)
		{
			si.setDefaultInitialValue();
		}
		else
		{
			VariableInstance vi = vl.getInstance(s);
			si.setInitialValue(vi);
		}
		s.addComplexInstance(si);
	}

	public void verify(Scope s) throws IntolerableException {
		VariableFactory vf = s.getFactory(type);
		VariableInstance si = vf.getInstance(varName, filename, line, column);
		if(vl != null)
		{
			try  {
				vl.getInstance(s);
			}
			catch(UserRuntimeError ure) {
			}
		}
		si.setDefaultInitialValue();
		s.addComplexInstance(si);
	}

}
