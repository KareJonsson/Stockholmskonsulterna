package se.modlab.generics.exceptions;

//import se.xyz.generics.*;

public class UserBreak extends IntolerableException
{
 
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public UserBreak()
  {
    super("Terminmated on users request", null);
  }

}