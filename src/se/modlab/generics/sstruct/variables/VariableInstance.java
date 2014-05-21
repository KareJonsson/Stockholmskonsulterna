package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;

public abstract class VariableInstance
{

  static 
  {
    try
    {
      Class.forName("se.modlab.generics.sstruct.variables.pa");
    }
    catch(ClassNotFoundException e)
    {
      //System.out.println("Commons f?rs?ker ladda. 3");
    }
    catch(NoClassDefFoundError e)
    {
      //System.out.println("Commons f?rs?ker ladda. 4");
    }
  }

  protected String name;
  protected VariableType vt;
  private String scopesname;
  protected String filename = null;
  protected int line = -1;
  protected int column = -1;

  public VariableInstance(String _name, VariableType _vt, String _filename, int _line, int _column)
  {
    name = _name;
    vt = _vt;
    filename = _filename;
    line = _line;
    column = _column;
  }

  public final String getName()
  {
    return name; 
  }
  
  public String getFilename() {
	  return filename;
  }

  public int getLine() {
	  return line;
  }
  
  public int getColumn() {
	  return column;
  }
  
  public String getPlace() {
	  return "in file "+filename+", line "+line+", column "+column;
  }
  
  public final void setName(String _name)
  {
    name = _name; 
  }

  public final VariableType getType()
  {
    return vt;
  }

  public abstract void setDefaultInitialValue()
    throws IntolerableException;

  public abstract void setInitialValue(VariableInstance val) 
    throws IntolerableException;

  public abstract void setValue(VariableInstance si)
		    throws IntolerableException;
		 
  public abstract boolean isNull();
		 
  public void accept(InstanceVisitor inst)
    throws IntolerableException
  {
    inst.visit(this);
  }

  public void setScopesName(String scopesname)
  {
    this.scopesname = scopesname;
  }

  public String getScopesName()
  {
    return scopesname;
  }
  
  public abstract boolean valueEquals(VariableInstance sv) throws IntolerableException;
  
}