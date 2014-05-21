package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;

public interface VariableFactory extends VariableType {

  public VariableInstance getInstance(String name, String filename, int line, int column) throws IntolerableException;
  
  public String getTypesName();
  
}