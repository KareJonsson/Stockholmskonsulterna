package se.modlab.generics.sstruct.tables;

public class DoubleHolder extends Holder
{

  private double d;

  public DoubleHolder(double d, int beginLine, int beginColumn)
  {
    super(beginLine, beginColumn);
    this.d = d;
  }

  public double getDouble()
  {
    return d;
  }

  public String toString()
  {
    return "double value "+d+" on "+super.toString(); 
  }

}
