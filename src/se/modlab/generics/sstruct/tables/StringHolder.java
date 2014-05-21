package se.modlab.generics.sstruct.tables;

public class StringHolder extends Holder
{

  private String s;

  public StringHolder(String s, int beginLine, int beginColumn)
  {
    super(beginLine, beginColumn);
    this.s = s;
  }

  public String getString()
  {
    return s;
  }

  public String toString()
  {
    return "string value "+s+" on "+super.toString(); 
  }

}
