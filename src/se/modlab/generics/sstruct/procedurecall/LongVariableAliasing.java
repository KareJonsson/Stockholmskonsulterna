package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class LongVariableAliasing extends ArgumentPasser
{
  private VariableLookup oldname;

  public LongVariableAliasing(String name, String _filename, int _line, int _column, VariableLookup oldname)
  {
    super(name, _filename, _line, _column);
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
    if(!(var instanceof LongVariable))
    {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not long as demanded in\n"+
        "procedure declaration.");
    }
    to.addVariable(var, name);
  }

}