package se.modlab.generics.sstruct.variables;

public class DoubleVariableFactory implements VariableFactory
{
  public DoubleVariableFactory()
  {
    super();
  }

  public VariableInstance getInstance(String name, String filename, int line, int column)
  {
    return new DoubleVariable(name, this, filename, line, column);
  }

  public String getTypesName()
  {
    return "double";
  }

  public String toString() {
	  return "Type "+getTypesName();
  }

}