package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;

public interface Complex
{
  public void addMember(VariableInstance si)
    throws IntolerableException;

  public int getSize();

  public VariableInstance getMember(int i)
    throws IntolerableException;

  public VariableInstance getMember(String name)
    throws IntolerableException;

  public VariableType getType();
  
  public boolean valueEquals(VariableInstance vi) throws IntolerableException;

}
