package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public interface VariableConst
{

  public void setValue(sValue val) 
    throws IntolerableException;

  public void setValue(VariableInstance si)
    throws IntolerableException;

}
