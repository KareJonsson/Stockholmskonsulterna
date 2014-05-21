package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;

public abstract class CreateConstantVersionVisitor extends InstanceVisitor
{
  protected VariableInstance si;
  protected static CreateConstantVersionVisitor instance = null;
  
  public static CreateConstantVersionVisitor getInstance()
    throws IntolerableException 
  {
    if(instance == null)
    { 
      throw new InternalProgrammingError(
        "createConstantVersionVisitor.getInstance called before\n"+
        "the instance was set from variableVectorConst classload.");
    } 
    return instance;
  }

  public static void setInstance(CreateConstantVersionVisitor iv)
    throws IntolerableException 
  {
    if(instance != null)
    {
      throw new InternalProgrammingError(
        "createConstantVersionVisitor.setInstance called after\n"+
        "the instance was set.");
    }
    instance = iv;
  }

  public VariableInstance getExportedVersion()
  {
    return si;
  }

  public void visit(BooleanVariable inst) 
    throws IntolerableException 
  {
    si = new BooleanConst(inst);
  }
 
  public void visit(BooleanConst inst) 
    throws IntolerableException 
  {
    si = inst;
  }
 
  public void visit(DoubleVariable inst) 
    throws IntolerableException 
  {
    si = new DoubleConst(inst);
  }
 
  public void visit(DoubleConst inst) 
    throws IntolerableException 
  {
    si = inst;
  }
 
  public void visit(LongVariable inst) 
    throws IntolerableException 
  {
    si = new LongConst(inst);
  }
 
  public void visit(LongConst inst) 
    throws IntolerableException 
  {
    si = inst;
  }
 
  public void visit(ComplexVariable inst) 
    throws IntolerableException 
  {
    si = new ComplexVariableConst(inst, "constant");
  }

  public void visit(VariableVector inst) 
    throws IntolerableException 
  {
    si = new VariableVectorConst(inst, "constant");
  }

  public void visit(VariableVectorFromFile inst) 
    throws IntolerableException 
  {
    si = new VariableVectorConst(inst, "constant");
  }

}