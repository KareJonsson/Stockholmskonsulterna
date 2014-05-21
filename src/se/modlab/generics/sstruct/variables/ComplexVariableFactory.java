package se.modlab.generics.sstruct.variables;

import java.util.*;

import se.modlab.generics.exceptions.*;

public class ComplexVariableFactory implements VariableFactory
{
  protected String name;
  private Vector<holder> members = new Vector<holder>();

  public ComplexVariableFactory(String name)
  {
    super();
    this.name = name;
  }

  public void addMember(VariableFactory vf, String name)
    throws IntolerableException
  {
    members.addElement(new holder(vf,name));
  }

  public VariableInstance getInstance(String name, String filename, int line, int column)
    throws IntolerableException
  {
    //System.out.println("Name = "+name);
    ComplexVariable scv = new ComplexVariable(name, this, filename, line, column);
    for(int i = 0 ; i < members.size() ; i++) {
      holder h = members.elementAt(i);
      //System.out.println("member = "+h.name);
      VariableInstance si = h.vf.getInstance(h.name, filename, line, column);
      scv.addMember(si);
    }
    return scv;
  }

  public String getTypesName()
  {
    return name;
  }

  public int getNoMembers()
  {
    return members.size();
  }

  public VariableFactory getMembersType(int i)
  {
    if(i < 0) return null;
    if(i >= members.size()) return null;
    return members.elementAt(i).vf;
  }

  public String getMembersName(int i)
  {
    if(i < 0) return null;
    if(i >= members.size()) return null;
    return members.elementAt(i).name;
  }
  
  public VariableFactory getMembersType(String typename) {
	    for(int i = 0 ; i < members.size() ; i++) {
	        holder h = members.elementAt(i);
	        if(h.name.compareTo(typename) == 0) {
	        	return h.vf;
	        }
	    }
	    System.out.println("ComplexVariableFactory.getMembersType("+typename+") to type "+name+" gave null");
	    return null;
  }

  protected class holder
  {
    public VariableFactory vf;
    public String name;
    public holder(VariableFactory vf, String name)
    {
      this.vf = vf;   
      this.name = name;
    }

  }

  public String toString() {
	  return "Type "+getTypesName();
  }

}