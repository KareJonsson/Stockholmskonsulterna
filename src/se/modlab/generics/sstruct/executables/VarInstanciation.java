package se.modlab.generics.sstruct.executables;

public abstract class VarInstanciation extends ProgramStatement
{

  protected String varName;
  protected String filename;
  protected int line;
  protected int column;

  public VarInstanciation(String name, String _filename, int _line, int _column)
  {
    varName = name;
    filename = _filename;
    line = _line;
    column = _column;
  }
  
  public String getPlace() {
	  return "in file "+filename+", line "+line+", column "+column;
  }

  public String getVariablesName()
  {
    return varName;
  }

}
