package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class StringVariableAliasing extends ArgumentPasser
{

  private VariableLookup oldname;

  public StringVariableAliasing(String newname, String _filename, int _line, int _column, VariableLookup _oldname)
  {
    super(newname, _filename, _line, _column);
    oldname = _oldname;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    Variable var = oldname.getVariable(from);
    if(var == null)
    {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not known in this scope.");
    }
    if(!(var instanceof StringVariable))
    {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not string as demanded in\n"+
        "procedure declaration.");
    }
    to.addVariable(var, name);
  }

}
