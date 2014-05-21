package se.modlab.generics.sstruct.tables;

public class ErrorHolder extends Holder
{

  private String s;

  public ErrorHolder(String s, int beginLine, int beginColumn)
  {
    super(beginLine, beginColumn);
    this.s = s;
  }

  public String toString()
  {
    return s+" "+super.toString();
  }

}
