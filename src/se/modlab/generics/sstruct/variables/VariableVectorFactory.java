package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;

public class VariableVectorFactory implements VariableFactory
{
  protected VariableFactory vf;
  protected int length;
  private Throwable t = null;

  public VariableVectorFactory(VariableFactory _vf, int _length) {
    super();
    vf = _vf;
    length = _length;
    t = new Throwable();
  }

  public VariableInstance getInstance(String name, String filename, int line, int column)
    throws IntolerableException {
    return new VariableVector(name, this, filename, line, column, vf, length);
  }

  public String getTypesName() {
    return "vector factory. Length = "+length+
           ", type "+vf.getTypesName();
  }

  public String toString() {
    StackTraceElement ets[] = t.getStackTrace();
    StringBuffer sb = new StringBuffer("Object VariableVectorFactory:\n");
    for(int i = 0 ; i < ets.length ; i++)
    {
      sb.append(ets[i].toString()+"\n");
    }
    return sb.toString();
  }

  public VariableFactory getElementsFactory() {
    return vf;
  }

}