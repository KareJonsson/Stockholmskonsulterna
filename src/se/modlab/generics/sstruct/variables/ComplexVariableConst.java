package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.values.sValue;

public class ComplexVariableConst extends ComplexVariable 
                                  implements VariableConst
{
  private ComplexVariable inst;
  private static CreateConstantVersionVisitor cevv = null;
  private String type = null;
 
  static
  {  
    try
    {
      cevv = CreateConstantVersionVisitor.getInstance();
    }
    catch(IntolerableException e)
    {
      //System.out.println("Commons laddning misslyckad. nsme");
    }  
  }

  public ComplexVariableConst(ComplexVariable inst, String type)
  {
    super(inst.getName(), inst.getType(), inst.getFilename(), inst.getLine(), inst.getColumn());
    this.inst = inst;
    //this.type = type;
  }

  public void addMember(VariableInstance si)
    throws IntolerableException
  {
    throw new InternalProgrammingError(
      "complexVariableExported.addMember was called on "+name);
  }
 
  public int getSize()
  {
    return inst.getSize();
  }

  public VariableInstance getMember(int i)
    throws IntolerableException
  {
    VariableInstance si = (VariableInstance) inst.getMember(i);
    si.accept(cevv);
    return cevv.getExportedVersion();
  }

  public VariableInstance getMember(String name)
    throws IntolerableException
  {
    for(int i = 0 ; i < inst.getSize() ; i++)
    {
      VariableInstance si = (VariableInstance) inst.getMember(i);
      if(name.compareTo(si.getName()) == 0) 
      {
        si.accept(cevv);
        return cevv.getExportedVersion();
      }
    }
    return null;
  }

  public void setDefaultInitialValue()
    throws IntolerableException
  {
    throw new InternalProgrammingError(
      "complexVariableExported.setDefaultInitialValue was called on "+name);
  }

/*
  public void resetInitialValue() 
    throws intolerableException
  {
    throw new internalError(
      "SimComplexVariableExported.resetInitialValue was called on "+name);
  }
 */

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Complex "+type+" "+inst.getType().getTypesName()+" variable "+name+" set to new value "+si
      +"\n"+"This happened to the variable declared "+getPlace());
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Complex "+type+" "+inst.getType().getTypesName()+" variable "+name+" set to new value "+val
      +"\n"+"This happened to the variable declared "+getPlace());
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    return this;
  }
 */

  public String toString()
  {
    return name;
  }

}
