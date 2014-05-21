package se.modlab.generics.sstruct.comparisons;

import se.modlab.generics.exceptions.*;

public abstract class ComparisonOperator 
{

  private String opsymbol;
  private String filename;
  private int line;
  private int column;

  public ComparisonOperator(String _opsymbol, String _filename, int _line, int _column)
  {
    opsymbol = _opsymbol;
    filename = _filename;
    line = _line;
    column = _column;
  }

  public abstract boolean compare(ArithmeticEvaluable ae_l, 
                                  ArithmeticEvaluable ae_r, 
                                  Scope s)
    throws IntolerableException;

  public String toString()
  {
    return "operator "+opsymbol+" in file "+filename+", line "+line+", column "+column;
  }
  
  public String getPlace() {
	  return "File "+filename+", line "+line+", column "+column;
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
  
  public String reproduceExpression() {
	  return opsymbol;
  }

}
