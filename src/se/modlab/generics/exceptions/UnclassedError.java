package se.modlab.generics.exceptions;

import se.modlab.generics.files.*;

public class UnclassedError extends IntolerableException {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Throwable t;
 
  public UnclassedError(String message) {
    this(message, null, null);
  }

  public UnclassedError(String message, Throwable t) {
    this(message, t, null);
  }

  public UnclassedError(String message, FileCollector cls[]) {
    this(message, null, cls);
  }

  public UnclassedError(String message, Throwable _t, FileCollector cls[]) {
    super(message, cls);
    t = _t;
  }

  public Throwable getThrowable() {
    return t;
  }

}
