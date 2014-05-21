package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.variables.*;

public class StructValueAliasing extends ArgumentPasser
{

  private VariableLookup oldname;

  public StructValueAliasing(String name, String _filename, int _line, int _column, VariableLookup _oldname)
  {
	super(name, _filename, _line, _column);
    this.oldname = _oldname;
  }

  public void passArgument(Scope from, Scope to)
    throws IntolerableException
  {
    VariableInstance originating_instance = oldname.getInstance(from);
    VariableFactory vf = (VariableFactory) originating_instance.getType();
    VariableInstance si_new = vf.getInstance(name, filename, line, column);
    si_new.setDefaultInitialValue();
    si_new.setValue(originating_instance);
    to.addComplexInstance(si_new);
  }

}