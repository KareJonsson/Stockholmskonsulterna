package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class StructVariableAliasing extends ArgumentPasser
{
  private VariableLookup oldname;

  public StructVariableAliasing(String newname, String _filename, int _line, int _column, VariableLookup oldname)
  {
    super(newname, _filename, _line, _column);
    this.oldname = oldname;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    VariableInstance si = oldname.getInstance(from);
    if(si == null)
    {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not known in this scope.");
    }
    if(si instanceof Variable)
    {
      throw new UserCompiletimeError(
        "Instance "+oldname+" "+getPlaceString()+" is of predefined type in contradiction to demand in\n"+
        "procedure declaration.");
    }
    to.addComplexInstance(si, name);
  }

}