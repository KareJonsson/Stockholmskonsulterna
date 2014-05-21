package se.modlab.generics.sstruct.comparisons;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.variables.*;

public interface VariableLookup 
{
  public VariableInstance getInstance(Scope s) throws IntolerableException;
  public Variable getVariable(Scope s) throws IntolerableException;
  public int getLine();
  public int getColumn();
  public String getFilename();
  public void verify(Scope s) throws IntolerableException;
  public String reproduceExpression();
  
}

