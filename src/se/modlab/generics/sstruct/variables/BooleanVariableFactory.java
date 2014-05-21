package se.modlab.generics.sstruct.variables;

public class BooleanVariableFactory implements VariableFactory {
 
  public BooleanVariableFactory() {
    super();
  }

  public VariableInstance getInstance(String name, String filename, int line, int column) {
    return new BooleanVariable(name, this, filename, line, column);
  }

  public String getTypesName() {
    return "boolean";
  }

  public String toString() {
	  return "Type "+getTypesName();
  }

}