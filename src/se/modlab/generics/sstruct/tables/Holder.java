package se.modlab.generics.sstruct.tables;

public class Holder
{

  private int beginLine;
  private int beginColumn;

  public Holder(int beginLine, int beginColumn)
  {
    this.beginLine = beginLine;
    this.beginColumn = beginColumn;
  }

  public String toString()
  {
    return "line "+beginLine+", column "+beginColumn;
  }

}
