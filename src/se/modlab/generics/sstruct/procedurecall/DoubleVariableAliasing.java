package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class DoubleVariableAliasing extends ArgumentPasser
{
  private VariableLookup oldname;

  public DoubleVariableAliasing(String newname, String _filename, int _line, int _column, VariableLookup oldname)
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
    if(!(var instanceof DoubleVariable))
    {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not double as demanded in\n"+
        "procedure declaration.");
    }
    to.addVariable(var, name);
  }

}