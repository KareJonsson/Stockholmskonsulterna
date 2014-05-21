package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.strings.*;

public class StopSimulation extends ProgramStatement
{

	StringContainer ccc;

	public StopSimulation(StringContainer _ccc)
	{
		ccc = _ccc;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		throw new StopException(ccc.evaluate(s));
	}

	public void verify(Scope s) throws IntolerableException {
	}

}
