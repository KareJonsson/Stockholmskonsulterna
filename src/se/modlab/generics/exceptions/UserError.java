package se.modlab.generics.exceptions;

import se.modlab.generics.files.*;

public abstract class UserError extends IntolerableException
{
 
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private Throwable t;

  public UserError(String message)
  {
    this(message, null, null);
  }

  public UserError(String message, Throwable t)
  {
    this(message, t, null);
  }

  public UserError(String message, FileCollector cls[])
  {
    this(message, null, cls);
  }

  public UserError(String message, Throwable t, FileCollector cls[])
  {
    super(message, cls);
    this.t = t;
  }

  public Throwable getThrowable()
  {
    return t;
  }

}
