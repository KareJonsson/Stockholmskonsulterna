package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.logics.*;

public class IfStatement extends ProgramStatement
{

	private LogicalExpression le;
	private ProgramBlock tc; 
	private ProgramBlock fc;

	public IfStatement(LogicalExpression _le, 
			ProgramBlock _tc, 
			ProgramBlock _fc)
	{
		le = _le;
		tc = _tc;
		fc = _fc;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		boolean b = le.evaluate(s);
		if(b)
		{
			tc.execute(s);
			return; 
		}
		if(fc == null) return;
		fc.execute(s);
	}

	public void verify(Scope s) throws IntolerableException {
		le.verify(s);
		tc.verify(s);
		if(fc != null) {
			fc.verify(s);
		}
	}

}
