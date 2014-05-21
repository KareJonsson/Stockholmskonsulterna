package se.modlab.generics.exceptions;

import se.modlab.generics.files.*;

public abstract class IntolerableException extends Exception
{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private FileCollector cls[];
  private String action;
 
  public IntolerableException(String message)
  {
    this(message, null);
  }

  public IntolerableException(String message, FileCollector cls[])
  {
    super(message);
    this.cls = cls;
  }

  public void setCollectors(FileCollector cls[])
  {
    this.cls = cls;
  }

  public FileCollector[] getCollectors()
  {
    return cls;
  }

  public void setAction(String _action)
  {
    action = _action;
  }

  public String getAction()
  {
    return action;
  }

}