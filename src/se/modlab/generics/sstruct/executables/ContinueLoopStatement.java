package se.modlab.generics.sstruct.executables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public class ContinueLoopStatement extends ProgramStatement
{

	private String filename;
	private int line;
	private int column;

	public ContinueLoopStatement(String _filename,
			int _line,
			int _column) 
	{
		filename = _filename;
		line = _line;
		column = _column;
	}

	public ContinueLoopStatement() 
	{
		this("Name not set", -1, -1);
	}

	public String getFilename()
	{
		return filename;
	}  

	public int getLine()
	{
		return line;
	}  

	public int getColumn()
	{
		return column;
	}  

	public void execute(Scope s) 
	throws ReturnException, 
	IntolerableException, 
	StopException,
	BreakException,
	ContinueException
	{
		throw new ContinueException();
	}

	public void verify(Scope s) throws IntolerableException {
	}

}
