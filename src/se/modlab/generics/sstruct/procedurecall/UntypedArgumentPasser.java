package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;

public abstract class UntypedArgumentPasser {

	  protected String filename;
	  protected int line;
	  protected int column;
	  //protected boolean isReferencePassing = false;
	  //protected String typename = null;
	  
	  UntypedArgumentPasser(String _filename, int _line, int _column)//, boolean _isReferencePassing, String _typename)
	  {
	    filename = _filename;
	    line = _line;
	    column = _column;
	    //isReferencePassing = _isReferencePassing;
	    //typename = _typename;
	  }

	  public abstract void passArgument(Scope from, Scope to, UntypedAliasing alias)
	    throws IntolerableException;
	  
	  public String getPlaceString() {
		  return "in file "+filename+", line "+line+", column "+column;
	  }

	}