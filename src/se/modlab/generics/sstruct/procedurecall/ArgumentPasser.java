package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;

public abstract class ArgumentPasser
{

  protected String name;
  protected String filename;
  protected int line;
  protected int column;
  
  ArgumentPasser(String _name, String _filename, int _line, int _column)
  {
    name = _name;
    filename = _filename;
    line = _line;
    column = _column;
  }

  public abstract void passArgument(Scope from, Scope to)
    throws IntolerableException;
  
  public String getPlaceString() {
	  return "in file "+filename+", line "+line+", column "+column;
  }

}