package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.executables.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.sstruct.variables.*;

public class ProcedureInstance implements Procedure 
{

  private String name;
  private ArgumentAliasing ases[];
  private ProgramBlock p;
  private String filename;
  private int line;
  private int column;

  public ProcedureInstance(String name,
                           ArgumentAliasing ases[],
                           ProgramBlock p,
                           String _filename,
                           int _line,
                           int _column)
  {
    this.name = name;
    this.ases = ases;
    this.p = p;
    filename = _filename;
    line = _line;
    column = _column;
  }
  
  public String getName()
  {
    return name;
  }

  public String getFilename()
  {
    return filename;
  }  

  public int getLine()
  {
    return line;
  }  

  public int getColumn()
  {
    return line;
  }  
  
  public String getPlace() {
	  return "file "+filename+", line "+line+", column "+column;
  }

  public int getAliasingSize()
  {
    return ases.length;
  }

  public boolean isVarDeclared(int i)
    throws IntolerableException
  {
    if((i < 0) ||
       (i >= ases.length))
    {
      throw new InternalError(
        "Indexation in the procedure argument list is out of bounds.\n"+
        "In 'isVarDeclared' i = "+i+" but length = "+ases.length);
    }
    return ases[i].isVarDeclared();
  }  

  public VariableFactory getAliasesFactory(int i)
    throws IntolerableException
  {
    if((i < 0) ||
       (i >= ases.length))
    {
      throw new InternalError(
        "Indexation in the procedure argument list is out of bounds.\n"+
        "In 'getAliasesFactory' i = "+i+" but length = "+ases.length);
    }
    return ases[i].getAliasesFactory();
  }

  public String getAliasesNameClass(int i)
    throws IntolerableException
  {
    if((i < 0) ||
       (i >= ases.length))
    {
      throw new InternalError(
        "Indexation in the procedure argument list is out of bounds.\n"+
        "In 'getAliasesNameClass' i = "+i+" but length = "+ases.length);
    }
    return ases[i].getName();
  }

  public ArgumentPasser getArgumentPasser(int i, LogicalExpression le)
    throws IntolerableException
  {
    if((i < 0) ||
       (i >= ases.length))
    {
      throw new InternalError(
        "Indexation in the procedure argument list is out of bounds.\n"+
        "In 'getArgumentPasser' i = "+i+" but length = "+ases.length);
    }
    return ases[i].getArgumentPasser(le);
  }

  public ArgumentPasser getArgumentPasser(int i, StringEvaluable se)
    throws IntolerableException
  {
    if((i < 0) ||
       (i >= ases.length))
    {
      throw new InternalError(
        "Indexation in the procedure argument list is out of bounds.\n"+
        "In 'getArgumentPasser' i = "+i+" but length = "+ases.length);
    }
    return ases[i].getArgumentPasser(se);
  }

  public ArgumentPasser getArgumentPasser(int i, ArithmeticEvaluable ae)
    throws IntolerableException
  {
    return ases[i].getArgumentPasser(ae);
  }

  public ProgramBlock getProgram()
  {
    return p;
  }

}