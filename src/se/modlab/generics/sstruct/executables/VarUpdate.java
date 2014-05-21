package se.modlab.generics.sstruct.executables;

import se.modlab.generics.sstruct.comparisons.*;

public abstract class VarUpdate extends ProgramStatement
{
 
  protected VariableLookup vl;
  protected String filename;
  protected int line;
  protected int column;

  public VarUpdate(VariableLookup _vl, String _filename, int _line, int _column)
  {
    vl = _vl;
    filename = _filename;
    line = _line;
    column = _column;
  }

  public String getPlace() {
	  return "in file "+filename+", line "+line+", column "+column;
  }
  

}
