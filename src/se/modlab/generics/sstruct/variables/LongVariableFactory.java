package se.modlab.generics.sstruct.variables;

public class LongVariableFactory implements VariableFactory
{

  public LongVariableFactory()
  {
    super();
  }

  public VariableInstance getInstance(String name, String filename, int line, int column)
  {
    return new LongVariable(name, this, filename, line, column);
  }

  public String getTypesName()
  {
    return "long";
  }
  
  public String toString() {
	  return "Type "+getTypesName();
  }


}