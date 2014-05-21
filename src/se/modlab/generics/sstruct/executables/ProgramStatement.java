package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;

public abstract class ProgramStatement
{

	  public abstract void execute(Scope s) 
	    throws ReturnException, 
	           IntolerableException, 
	           StopException,
	           BreakException,
	           ContinueException;

	  public abstract void verify(Scope s) 
	    throws IntolerableException;

}
