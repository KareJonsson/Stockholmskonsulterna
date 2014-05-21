package se.modlab.generics.sstruct.tables;

public class LongHolder extends Holder
{

  private long l;

  public LongHolder(long l, int beginLine, int beginColumn)
  {
    super(beginLine, beginColumn);
    this.l = l;
  }

  public long getLong()
  {
    return l;
  }

  public String toString()
  {
    return "long value "+l+" on "+super.toString(); 
  }

}
