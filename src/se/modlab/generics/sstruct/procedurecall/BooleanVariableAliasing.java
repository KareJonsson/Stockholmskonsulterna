package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class BooleanVariableAliasing extends ArgumentPasser
{

  private VariableLookup oldname;

  public BooleanVariableAliasing(String newname, String _filename, int _line, int _column, VariableLookup oldname)
  {
    super(newname, _filename, _line, _column);
    this.oldname = oldname;
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
    if(!(var instanceof BooleanVariable))
    {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not boolean as demanded in\n"+
        "procedure declaration.");
    }
    to.addVariable(var, name);
  }

}