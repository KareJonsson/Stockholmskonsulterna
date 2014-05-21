package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.executables.*;

public class UntypedProcedureCallStatement extends ProgramStatement
{

	private String name;
	private UntypedArgumentPasser aps[];
	private ScopeFactory sf;

	public UntypedProcedureCallStatement(String _name,
			UntypedArgumentPasser _aps[],
			ScopeFactory _sf)
	{
		name = _name;
		aps = _aps;
		sf = _sf;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException 
	{
		UntypedProcedureInstance pi = (UntypedProcedureInstance) s.getProcedure(name);
		if(pi == null) {
			throw new UserCompiletimeError("Procedure "+name+" not known. Scope "+s.toString());
		}
		if(aps.length != pi.getNoAliases()) {
			throw new UserCompiletimeError("Procedure "+name+" is called with "+aps.length+" parameters but declared with "+pi.getNoAliases());
		}
		Scope scopeForProcedure = sf.getInstance(s.getBottomScope(), "procedure "+name);
		for(int i = 0 ; i < aps.length ; i++) {
			aps[i].passArgument(s, scopeForProcedure, pi.getAlias(i));
		}
		ProgramBlock p = pi.getProgram();
		try
		{
			p.execute(scopeForProcedure);
		}
		catch(ReturnException re) {
		}
	}

	public void verify(Scope s) throws IntolerableException {
		UntypedProcedureInstance pi = (UntypedProcedureInstance) s.getProcedure(name);
		if(pi == null) 
		{
			throw new UserCompiletimeError("Procedure "+name+" not known.");
		}
		Scope scopeForProcedure = sf.getInstance(s);
		for(int i = 0 ; i < aps.length ; i++)
		{
			aps[i].passArgument(s, scopeForProcedure, pi.getAlias(i));
		}
		ProgramBlock p = pi.getProgram();
		p.verify(scopeForProcedure);	
	}

}