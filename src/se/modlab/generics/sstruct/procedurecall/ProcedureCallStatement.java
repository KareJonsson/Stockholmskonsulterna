package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.executables.*;

public class ProcedureCallStatement extends ProgramStatement
{

	private String name;
	private ArgumentPasser aps[];
	private ScopeFactory sf;

	public ProcedureCallStatement(String name,
			ArgumentPasser aps[],
			ScopeFactory _sf)
	{
		this.name = name;
		this.aps = aps;
		sf = _sf;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		ProcedureInstance pi = (ProcedureInstance) s.getProcedure(name);
		if(pi == null) 
		{
			throw new UserCompiletimeError("Procedure "+name+" not known. Scope "+s.toString());
		}
		Scope scopeForProcedure = sf.getInstance(s);
		for(int i = 0 ; i < aps.length ; i++)
		{
			aps[i].passArgument(s, scopeForProcedure);
		}
		ProgramBlock p = pi.getProgram();
		try
		{
			p.execute(scopeForProcedure);
		}
		catch(ReturnException re)
		{
		}
	}

	public void verify(Scope s) throws IntolerableException {
		ProcedureInstance pi = (ProcedureInstance) s.getProcedure(name);
		if(pi == null) 
		{
			throw new UserCompiletimeError("Procedure "+name+" not known.");
		}
		Scope scopeForProcedure = sf.getInstance(s);
		for(int i = 0 ; i < aps.length ; i++)
		{
			aps[i].passArgument(s, scopeForProcedure);
		}
		ProgramBlock p = pi.getProgram();
		p.verify(scopeForProcedure);	
	}

}