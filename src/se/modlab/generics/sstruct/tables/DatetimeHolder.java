package se.modlab.generics.sstruct.tables;

import java.util.*;

public class DatetimeHolder extends Holder
{

  private String dt;

  public DatetimeHolder(String dt, int beginLine, int beginColumn)
  {
    super(beginLine, beginColumn);
    this.dt = dt;
  }

  public long getDatetime()
  {
    return new Date(dt).getTime();
  }

  public String getString()
  {
    return dt;
  }

  public String toString()
  {
    return "datetime value "+dt+" on "+super.toString(); 
  }

}
