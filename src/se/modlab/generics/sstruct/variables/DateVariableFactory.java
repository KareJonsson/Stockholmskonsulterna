package se.modlab.generics.sstruct.variables;

public class DateVariableFactory implements VariableFactory
{
	public DateVariableFactory() {
		super();
	}

	public VariableInstance getInstance(String name, String filename, int line, int column) {
		return new DateVariable(name, this, filename, line, column);
	}

	public String getTypesName() {
		return "datetime";
	}

	  public String toString() {
		  return "Type "+getTypesName();
	  }

}