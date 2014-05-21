package se.modlab.generics.sstruct.variables;

public class StringVariableFactory implements VariableFactory
{
  public StringVariableFactory()
  {
    super();
  }

  public VariableInstance getInstance(String name, String filename, int line, int column)
  {
    return new StringVariable(name, this, filename, line, column);
  }

  public String getTypesName()
  {
    return "string";
  }

  public String toString() {
	  return "Type "+getTypesName();
  }

}