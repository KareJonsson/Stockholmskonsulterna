package se.modlab.generics.exceptions;

import se.modlab.generics.files.FileCollector;

public class UserCompiletimeError extends UserError {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserCompiletimeError(String message)
	  {
	    this(message, null, null);
	  }

	  public UserCompiletimeError(String message, Throwable t)
	  {
	    this(message, t, null);
	  }

	  public UserCompiletimeError(String message, FileCollector cls[])
	  {
	    this(message, null, cls);
	  }

	  public UserCompiletimeError(String message, Throwable t, FileCollector cls[])
	  {
	    super(message, t, cls);
	  }

}
