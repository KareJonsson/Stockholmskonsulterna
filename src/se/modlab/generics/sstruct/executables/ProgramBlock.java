package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

import java.util.*;

public class ProgramBlock extends ProgramStatement
{

	protected Vector<ProgramStatement> statements = new Vector<ProgramStatement>();
	protected ScopeFactory sf;

	public ProgramBlock(ScopeFactory _sf)
	{
		sf = _sf;
	}

	public void addStatement(ProgramStatement ps)
	{
		statements.addElement(ps);
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		Scope tmp = sf.getInstance(s, "Temporary program");
		for(int i = 0 ; i < statements.size() ; i++)
		{
			ProgramStatement ps = (ProgramStatement) statements.elementAt(i);
			//System.out.println("program - "+ps.getClass().getName());
			ps.execute(tmp);
		}
	}

	public void verify(Scope s) throws IntolerableException {
		Scope tmp = sf.getInstance(s, "Temporary program");
		for(int i = 0 ; i < statements.size() ; i++)
		{
			ProgramStatement ps = (ProgramStatement) statements.elementAt(i);
			//System.out.println("program - "+ps.getClass().getName());
			ps.verify(tmp);
		}
	}

}
