package se.modlab.generics.sstruct.tables;

import java.util.*;

public class DefaultDatetimeHandler implements DatetimeHandler
{

  public Holder getHolder(String datetime, int beginLine, int beginColumn)
  {
    Date d = new Date(datetime);
    return new LongHolder(d.getTime(), beginLine, beginColumn);
  }

}
