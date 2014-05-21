package se.modlab.generics.exceptions;

import se.modlab.generics.files.FileCollector;

public class UserRuntimeError extends UserError {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public UserRuntimeError(String message)
	  {
	    this(message, null, null);
	  }

	  public UserRuntimeError(String message, Throwable t)
	  {
	    this(message, t, null);
	  }

	  public UserRuntimeError(String message, FileCollector cls[])
	  {
	    this(message, null, cls);
	  }

	  public UserRuntimeError(String message, Throwable t, FileCollector cls[])
	  {
	    super(message, t, cls);
	  }

}
