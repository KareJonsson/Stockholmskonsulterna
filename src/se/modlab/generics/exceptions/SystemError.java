package se.modlab.generics.exceptions;

import se.modlab.generics.files.*;

public class SystemError extends IntolerableException
{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Throwable t;
 
  public SystemError(String message)
  {
    this(message, null, null);
  }

  public SystemError(String message, Throwable t)
  {
    this(message, t, null);
  }

  public SystemError(String message, FileCollector cls[])
  {
    this(message, null, cls);
  }

  public SystemError(String message, Throwable t, FileCollector cls[])
  {
    super(message, cls);
    this.t = t;
  }

  public Throwable getThrowable()
  {
    return t;
  }

}
