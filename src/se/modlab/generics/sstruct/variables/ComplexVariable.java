package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;

import java.util.*;

public class ComplexVariable extends VariableInstance
                             implements Complex
{

  private Vector<VariableInstance> members = new Vector<VariableInstance>();

  public ComplexVariable(String name, VariableType vt, String _filename, int _line, int _column)
  {
    super(name, vt, _filename, _line, _column);
  }

  public void addMember(VariableInstance si)
    throws IntolerableException
  {
    members.addElement(si);
  }
  
  public boolean isNull() {
	  return false;
  }
 
  public int getSize()
  {
    return members.size();
  }

  public VariableInstance getMember(int i)
    throws IntolerableException
  {
    if(i < 0) return null;
    if(i >= members.size()) return null;
    return (VariableInstance) members.elementAt(i);
  }

  public VariableInstance getMember(String name)
    throws IntolerableException
  {
    for(int i = 0 ; i < members.size() ; i++)
    {
      VariableInstance si = (VariableInstance) members.elementAt(i);
      if(name.compareTo(si.getName()) == 0) return si;
    }
    return null;
  }

  public void setDefaultInitialValue()
    throws IntolerableException
  {
    for(int i = 0 ; i < members.size() ; i++)
    {
      VariableInstance si = (VariableInstance) members.elementAt(i);
      si.setDefaultInitialValue();
    }
  }

  public void setScopesName(String scopesName)
  {
    for(int i = 0 ; i < members.size() ; i++)
    {
      VariableInstance si = (VariableInstance) members.elementAt(i);
      si.setScopesName(scopesName);
    }
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    if(si.getType() != vt)
    {
      throw new InternalError( 
        "Complex variable "+name+" of type "+
        vt.getTypesName()+" set to value of \n"+
        "type "+si.getType().getTypesName());
    }
    ComplexVariable scv_there = (ComplexVariable) si;
    for(int i = 0 ; i < members.size() ; i++)
    {
      VariableInstance si_here = (VariableInstance) members.elementAt(i);
      si_here.setValue(scv_there.getMember(si_here.getName()));
    }
  }

  public void setInitialValue(VariableInstance si) 
    throws IntolerableException
  {
    if(si.getType() != vt)
    {
      throw new InternalError( 
        "Complex variable "+name+" of type "+
        vt.getTypesName()+" set to value of \n"+
        "type "+si.getType().getTypesName());
    }
    ComplexVariable scv_there = (ComplexVariable) si;
    for(int i = 0 ; i < members.size() ; i++)
    {
      VariableInstance si_here = (VariableInstance) members.elementAt(i);
      si_here.setInitialValue(scv_there.getMember(si_here.getName()));
    }
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer(name+", members are "+members.size()+"\n");
    for(int i = 0 ; i < members.size(); i++)
    {
      sb.append(members.elementAt(i).toString()+"\n");
    }
    return sb.toString();
  }
  
	public boolean valueEquals(VariableInstance sv) throws IntolerableException {
		if(!(sv instanceof ComplexVariable)) {
			return false;
		}
		ComplexVariable other = (ComplexVariable) sv;
		for(int i = 0 ; i < members.size() ; i++) {
			VariableInstance vi = members.elementAt(i);
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
