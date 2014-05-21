package se.modlab.generics.sstruct.comparisons;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.variables.*;

public class ScopeStruct extends VariableInstance implements Complex
{

  protected Scope s = null;

  public ScopeStruct(Scope _s, String _name)
    throws IntolerableException {
    this(_s, _name, true, "No filename", -1, -1);
  }

  public ScopeStruct(Scope _s, String _name, boolean addOneself, String filename, int line, int column)
    throws IntolerableException {
    super(_name, new ScopeStructType(_name), filename, line, column);
    s = _s;
    if(addOneself) s.addComplexInstance(this);
  }

  public void setInitialValue(VariableInstance _val) throws IntolerableException {
    throw new InternalError(
      "ScopeStruct.setInitialValue(variableInstance)\n"+
      "called with object of class "+_val.getClass().getName()+".\n"+
      "This method should not be called at all!");
  }
  
  public boolean isNull() {
	  return false;
  }

  public void setDefaultInitialValue() throws IntolerableException {
  }

  public void setValue(VariableInstance si) throws IntolerableException {
    throw new InternalError(
      "ScopeStruct.setValue was called\n"+
      "on scope reference "+name);
  }

  public void addMember(VariableInstance si) throws IntolerableException {
    throw new InternalError("Added member in scope reference named "+name);
  }

  public int getSize() {
    return s.table_variables.size();
  }

  public VariableInstance getMember(int i) throws IntolerableException {
    return (VariableInstance) s.table_variables.entrySet().toArray()[i];
    //throw new internalError("Get member in scope reference named "+name);
  }

  public VariableInstance getMember(String name) throws IntolerableException {
    //System.out.println("scope Reference: Get member "+name);
    return s.getComplexInstance(name);
  }

  public String toString() {
    return "Scope Struct";
  }

  private static class ScopeStructType implements VariableType {
    private String name;
    public ScopeStructType(String _name)
    {
      name = _name;
    }
    public String getTypesName()
    {
      return name;
    }
  }

	public boolean valueEquals(VariableInstance sv) throws IntolerableException {
		if(!(sv instanceof Complex)) {
			return false;
		}
		Complex other = (Complex) sv;
		if(getSize() != other.getSize()) {
			return false;
		}
		for(int i = 0 ; i < getSize() ; i++) {
			VariableInstance vi = getMember(i);
			VariableInstance viother = other.getMember(vi.getName());
			if(viother == null) {
				return false;
			}
			if(!vi.valueEquals(viother)) {
				return false;
			}
		}
		return true;
	}

}
