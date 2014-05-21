package se.modlab.generics.exceptions;

import se.modlab.generics.files.*;

public class InternalProgrammingError extends IntolerableException
{

	private static final long serialVersionUID = 1L;
	
	private Throwable t;

	public InternalProgrammingError(String message)
	{
		this(message, null, null);
	}

	public InternalProgrammingError(String message, Throwable t)
	{
		this(message, t, null);
	}

	public InternalProgrammingError(String message, FileCollector cls[])
	{
		this(message, null, cls);
	}

	public InternalProgrammingError(String message, Throwable t, FileCollector cls[])
	{
		super(message, cls);
		this.t = t;
	}

	public Throwable getThrowable()
	{
		return t;
	}

}
