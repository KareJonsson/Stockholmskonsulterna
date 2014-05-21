package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public class ReturnPrematurely extends ProgramStatement
{

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		throw new ReturnException();
	}

	public void verify(Scope s) throws IntolerableException {
	}

}
