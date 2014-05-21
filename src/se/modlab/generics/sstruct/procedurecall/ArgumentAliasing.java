package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.evaluables.*;
import se.modlab.generics.sstruct.logics.*;
import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.sstruct.variables.*;

public class ArgumentAliasing
{
/*
  public final static Class longclass = new SimLong(0).getClass();
  public final static Class doubleclass = new SimDouble(0).getClass();
  public final static Class booleanclass = new SimBoolean(true).getClass();
  public final static Class valueclass = longclass.getSuperclass();
*/
  private VariableFactory vf;
  private boolean varDeclaration;
  private String aliasName;
  private String filename;
  private int line;
  private int column;

  public ArgumentAliasing(VariableFactory _vf, boolean _varDeclaration, String _aliasName, String _filename, int _line, int _column)
    throws IntolerableException
  {
    vf = _vf;
    varDeclaration = _varDeclaration;
    aliasName = _aliasName;
    filename = _filename;
    line = _line;
    column = _column;
  }

  public boolean isVarDeclared()
  {
    return varDeclaration;
  }

  public VariableFactory getAliasesFactory()
  {
    return vf;
  }

  public String getName()
  {
    return aliasName;
  }

  public void makeDummyInstanciation(Scope s)
    throws IntolerableException
  {
	  //System.out.println("aliasing.makeDummyInstanciation 1");
    VariableInstance si = vf.getInstance(aliasName, "File unknown", -1, -1);
	  //System.out.println("aliasing.makeDummyInstanciation 2");
    si.setDefaultInitialValue();
	  //System.out.println("aliasing.makeDummyInstanciation 3");
    s.addComplexInstance(si);
	  //System.out.println("aliasing.makeDummyInstanciation 4");
  }

  public ArgumentPasser getArgumentPasser(LogicalExpression le)
    throws IntolerableException
  {
    if(vf.getTypesName().compareTo("boolean") != 0)
    {
      throw new InternalError("Not boolean as believed in parser.");
    }
    if(!varDeclaration)
    {
      // Not var declared. 
      return new BooleanValueAliasing(aliasName, filename, line, column, le);
    }
    if(!(le instanceof LogicalExpressionVariable))
    {
      // Expression passed to var declared parameter. Make temporary variable
      // i.e. pass as if not var declared.
      return new BooleanValueAliasing(aliasName, filename, line, column, le);
    }
    LogicalExpressionVariable lev = (LogicalExpressionVariable) le;
    // Var declared parameter and only a variable supplied. As expected. 
    // User appears to know what he is doing.
    return new BooleanVariableAliasing(aliasName, filename, line, column, lev.getVariableReference());
  }

  public ArgumentPasser getArgumentPasser(StringEvaluable se)
    throws IntolerableException
  {
    if(vf.getTypesName().compareTo("string") != 0)
    {
      throw new InternalError("Not string as believed in parser.");
    }
    if(!varDeclaration)
    {
      // Not var declared. 
      return new StringValueAliasing(aliasName, filename, line, column, se);
    }
    if(!(se instanceof StringVariableEvaluable))
    {
      // Expression passed to var declared parameter. Make temporary variable
      // i.e. pass as if not var declared.
      return new StringValueAliasing(aliasName, filename, line, column, se);
    }
    StringVariableEvaluable sev = (StringVariableEvaluable) se;
    // Var declared parameter and only a variable supplied. As expected. 
    // User appears to know what he is doing.
    return new StringVariableAliasing(aliasName, filename, line, column, 
                                      sev.getVariableReference());
  }

  public ArgumentPasser getArgumentPasser(ArithmeticEvaluable ae)
    throws IntolerableException
  {
    if(vf.getTypesName().compareTo("boolean") == 0)
    {
      throw new InternalError("Boolean contradicting belief in parser.");
    }
    if(vf.getTypesName().compareTo("string") == 0)
    {
      throw new InternalError("String contradicting belief in parser.");
    }
    if(vf.getTypesName().compareTo("long") == 0)
    {
      if(!varDeclaration)
      {
        // Not var declared. 
        return new LongValueAliasing(aliasName, filename, line, column, ae);
      }
      if(!(ae instanceof ArithmeticEvaluableVariable))
      {
        // Expression passed to var declared parameter. Make temporary variable
        // i.e. pass as if not var declared.
        return new LongValueAliasing(aliasName, filename, line, column, ae);
      }
      ArithmeticEvaluableVariable aev = (ArithmeticEvaluableVariable) ae;
      return new LongVariableAliasing(aliasName, filename, line, column, 
                                      aev.getVariableReference());
    }
    if(vf.getTypesName().compareTo("double") == 0)
    {
      if(!varDeclaration)
      {
        // Not var declared. 
        //System.out.println("Not var decl");
        return new DoubleValueAliasing(aliasName, filename, line, column, ae);
      }
      if(!(ae instanceof ArithmeticEvaluableVariable))
      {
        //System.out.println("Not var called");
        // Expression passed to var declared parameter. Make temporary variable
        // i.e. pass as if not var declared.
        return new DoubleValueAliasing(aliasName, filename, line, column, ae);
      }
      //System.out.println("Full var call");
      ArithmeticEvaluableVariable aev = (ArithmeticEvaluableVariable) ae;
      return new DoubleVariableAliasing(aliasName, filename, line, column, 
                                        aev.getVariableReference());
    }
    // Passing struct or vector
    if(!(ae instanceof ArithmeticEvaluableVariable))
    {
      //System.out.println("Actual class "+ae.getClass().getName());
      // arithmeticEvaluableFakerReferenceHolder
      // Expression passed to var declared parameter. Make temporary variable
      // i.e. pass as if not var declared.
      throw new UserCompiletimeError(
        "You may not pass a non trivial arithmetic expression to a\n"+
        "struct declared procedure parameter.\n"+
        "Happens in file "+filename+", line "+line+", column "+column);
    }
    ArithmeticEvaluableVariable aev = (ArithmeticEvaluableVariable) ae;
    if(!varDeclaration)
    {
      // Not var declared. 
      //System.out.println("Not var decl");
      return new StructValueAliasing(aliasName, filename, line, column, aev.getVariableReference());
    }
    //System.out.println("Full var call");
    return new StructVariableAliasing(aliasName, filename, line, column, aev.getVariableReference());
  }

}