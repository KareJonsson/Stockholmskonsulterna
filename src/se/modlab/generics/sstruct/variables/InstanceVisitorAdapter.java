package se.modlab.generics.sstruct.variables;

import java.lang.reflect.*;

import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;


public class InstanceVisitorAdapter extends InstanceVisitor
{

  private Exception getException(Throwable e)
  {
    if(!(e instanceof InvocationTargetException))
    {  
    	if(!(e instanceof Exception)) {
    		return new InternalProgrammingError(
    				"Non Exception throwable in instanceVisitorAdapter.\n"+
    				"Actual class "+e.getClass().getName()+", message "+e.getMessage(), e);
    		//System.out.println("Actual class "+e.getClass().getName()+", message "+e.getMessage());
    	}
      return (Exception)e;  
    }
    while(e instanceof InvocationTargetException)
    {
      e = ((InvocationTargetException) e).getTargetException();
    }
	if(!(e instanceof Exception)) {
		return new InternalProgrammingError(
				"Non Exception throwable in instanceVisitorAdapter.\n"+
				"Actual class "+e.getClass().getName()+", message "+e.getMessage(), e);
		//System.out.println("Actual class "+e.getClass().getName()+", message "+e.getMessage());
	}
    return (Exception)e;
  }

  private String getMessage(Throwable e)
  {
    Exception _e = getException(e);
    //_e.printStackTrace();
    return _e.toString();
  }

  protected void traverse(VariableInstance inst)
    throws IntolerableException
  {
    try 
    {
      Class<?> c = getClass();
      Method m = c.getMethod("traverse", 
                             new Class[] { inst.getClass() });
      m.invoke(this, new Object[] { inst });
    } 
    catch (Exception e) 
    {
      e = getException(e);
      if(e instanceof IntolerableException) throw (IntolerableException)e;
      throw new InternalProgrammingError(getMessage(e));
    }
  } 

  protected void traverse(Complex inst)
    throws IntolerableException
  {
    //System.out.println("instanceVisitorAdapter traverse SimComplexVariable "+inst.getName());
    for(int i = 0 ; i < inst.getSize() ; i++)
    {
      VariableInstance _inst = inst.getMember(i);
      _inst.accept(this);
    }
  }

  protected void traverse(ComplexVariable inst)
    throws IntolerableException
  {
    traverse((Complex) inst);
  }

  protected void traverse(VariableVector inst)
    throws IntolerableException
  {
    //System.out.println("instanceVisitorAdapter traverse SimVariableVector "+inst.getName());
    for(int i = 0 ; i < inst.getLength() ; i++)
    {
      VariableInstance _inst = inst.getVectorElement(i);
      _inst.accept(this);
    }
  }

  protected void traverse(VariableVectorFromFile inst)
    throws IntolerableException
  {
    //System.out.println("instanceVisitorAdapter traverse SimVariableVector "+inst.getName());
    for(int i = 0 ; i < inst.getLength() ; i++)
    {
      VariableInstance _inst = inst.getVectorElement(i);
      _inst.accept(this);
    }
  }

  protected void traverse(BooleanVariable inst) throws IntolerableException { this.visit(inst); }
  protected void traverse(BooleanConst inst) throws IntolerableException { this.visit(inst); }

  protected void traverse(DoubleVariable inst) throws IntolerableException { this.visit(inst); }
  protected void traverse(DoubleConst inst) throws IntolerableException { this.visit(inst); }

  protected void traverse(LongVariable inst) throws IntolerableException { this.visit(inst); }
  protected void traverse(LongConst inst) throws IntolerableException { this.visit(inst); }

  public void visit(BooleanVariable inst) 
    throws IntolerableException 
  {
  }
 
  public void visit(BooleanConst inst) 
    throws IntolerableException 
  {
  }
 
  public void visit(DoubleVariable inst) 
    throws IntolerableException 
  {
  }
 
  public void visit(DoubleConst inst) 
    throws IntolerableException 
  {
  }
 
  public void visit(LongVariable inst) 
    throws IntolerableException 
  {
  }
 
  public void visit(LongConst inst) 
    throws IntolerableException 
  {
  }
 
  public void visit(ComplexVariable inst) 
    throws IntolerableException 
  {
  }

  public void visit(VariableVector inst) 
    throws IntolerableException 
  {
  }

  public void visit(VariableVectorFromFile inst) 
    throws IntolerableException 
  {
  }

}