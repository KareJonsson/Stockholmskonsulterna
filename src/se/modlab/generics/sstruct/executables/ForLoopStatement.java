package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.logics.*;

public class ForLoopStatement extends ProgramStatement
{

	private ProgramStatement var_inst;
	private LogicalExpression le; 
	private ProgramStatement var_upda;
	private ProgramBlock loopsInstructions;
	private ScopeFactory sf;

	public ForLoopStatement(ProgramStatement _var_inst, 
			LogicalExpression _le, 
			ProgramStatement _var_upda, 
			ProgramBlock _loopsInstructions,
			ScopeFactory _sf)
	{
		var_inst = _var_inst;
		le = _le;
		var_upda = _var_upda;
		loopsInstructions = _loopsInstructions;
		sf = _sf;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		Scope loopsScope = sf.getInstance(s);
		if(var_inst != null)
		{
			var_inst.execute(loopsScope);
		}
		try
		{
			while(le.evaluate(loopsScope))
			{
				try
				{
					loopsInstructions.execute(loopsScope);
					if(var_upda != null)
					{
						var_upda.execute(loopsScope);
					}
				}
				catch(ContinueException e)
				{
					if(var_upda != null)
					{
						var_upda.execute(loopsScope);
					}
				}
			}
		}
		catch(BreakException e)
		{
		}
	}

	public void verify(Scope s) throws IntolerableException {
		Scope loopsScope = sf.getInstance(s);
		if(var_inst != null)
		{
			var_inst.verify(loopsScope);
		}
		le.verify(loopsScope);
		loopsInstructions.verify(loopsScope);
		if(var_upda != null)
		{
			var_upda.verify(loopsScope);
		}
	}

}
