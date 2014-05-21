package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.values.*;

public class VariableVectorConst extends VariableVector 
                                         implements VariableConst
{
  private VariableVector inst;
  private VariableVectorFromFile instff;
  private static CreateConstantVersionVisitor cevv = null;
  private String ridgidness = null;

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

  public VariableVectorConst(VariableVector inst, String _ridgidness)
    throws IntolerableException
  {
    super(inst.getName(), 
          inst.getType(),
          inst.getFilename(),
          inst.getLine(),
          inst.getColumn(),
          inst.getVariableFactory(),
          inst.getLength());
    this.inst = inst;
    ridgidness = _ridgidness;
  }

  public VariableVectorConst(VariableVectorFromFile inst, String _ridgidness)
    throws IntolerableException
  {
    super(inst.getName(), 
          inst.getType(),
          inst.getFilename(),
          inst.getLine(),
          inst.getColumn(),
          inst.getVariableFactory(),
          inst.getLength());
    this.instff = inst;
    ridgidness = _ridgidness;
  }

  protected void initiateVector(int length)
    throws IntolerableException
  {
  }

  public int getLength()
  {
    if(inst == null) return instff.getLength();
    return inst.getLength();
  }

  public VariableInstance getVectorElement(int i)
    throws IntolerableException
  {
    VariableInstance si = null;
    if(inst != null) si = inst.getVectorElement(i);
    if(instff != null) si = instff.getVectorElement(i);
    if(si == null) 
    {
      throw new InternalError(
        "Variable Vector "+ridgidness+" variable "+name+" set to\n"+
        "has neither a vector from file or other.");
    }
    si.accept(cevv);
    return cevv.getExportedVersion();
  }

  public void setDefaultInitialValue()
    throws IntolerableException
  {
    throw new InternalError(
      "variableVectorConst.setDefaultInitialValue was called on "+name);
  }

  public void setScopesName(String scopesName)
  {
  }

  public void setValue(VariableInstance si)
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Variable Vector "+ridgidness+" variable "+name+" set to new value "+si
      +"\n"+"This happened to the variable declared "+getPlace());
  }

  public String toString()
  {
    return "Name "+name+
           ", vector length = "+getLength()+
           ", type "+inst.getType().getTypesName()+", style "+ridgidness;
  }

  public void setValue(sValue val) 
    throws IntolerableException
  {
    throw new UserCompiletimeError(
      "Variable vector "+inst.getType().getTypesName()+" variable "+name+" set to new value "+val
      +"\n"+"This happened to the variable declared "+getPlace());
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    variableVectorConst vvc = new variableVectorConst(this, ridgidness);
    for(int i = 0 ; i < siv.length ; i++)
    {
      vvc.siv[i] = siv[i].copy();
    }
    return vvc;
  }
 */

}
