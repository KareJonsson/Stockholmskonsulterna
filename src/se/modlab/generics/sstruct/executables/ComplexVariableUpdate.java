package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class ComplexVariableUpdate extends VarUpdate
{

	private VariableLookup give;

	public ComplexVariableUpdate(VariableLookup receive,
			VariableLookup _give,
			String filename, int line, int column)
	{
		super(receive, filename, line, column);
		give = _give;
	}

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		VariableInstance si_give = give.getInstance(s);
		VariableInstance si_receive = vl.getInstance(s);
		if(si_give.getType() != si_receive.getType())
		{
			throw new UserRuntimeError(
					"Variable "+vl+" declared in file "+vl.getFilename()+", on line "+
					vl.getLine()+", column "+
					vl.getColumn()+" was evaluated to be of type\n"+
					si_receive.getType().getTypesName()+
					" but the evaluated variables type was "+si_give.getType().getTypesName());
		}
		si_receive.setValue(si_give);
	}

	public void verify(Scope s) throws IntolerableException {
		VariableInstance si_give = give.getInstance(s);
		VariableInstance si_receive = vl.getInstance(s);
		if(si_give.getType() != si_receive.getType())
		{
			throw new UserCompiletimeError(
					"Variable "+vl+" declared in file "+vl.getFilename()+", on line "+
					vl.getLine()+", column "+
					vl.getColumn()+" was evaluated to be of type\n"+
					si_receive.getType().getTypesName()+
					" but the evaluated variables type was "+si_give.getType().getTypesName());
		}
	}
	
}

