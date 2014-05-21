package se.modlab.generics.sstruct.tables;

public class BooleanHolder extends Holder
{

  private boolean b;

  public BooleanHolder(boolean b, int beginLine, int beginColumn)
  {
    super(beginLine, beginColumn);
    this.b = b;
  }

  public boolean getBoolean()
  {
    return b;
  }

  public String toString()
  {
    return "boolean value "+b+" on "+super.toString(); 
  }

}
