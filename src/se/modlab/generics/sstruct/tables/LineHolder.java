package se.modlab.generics.sstruct.tables;

import java.util.*;

public class LineHolder
{

  private Vector<Holder> v = new Vector<Holder>();

  public LineHolder(Holder firstVal)
  {
    if(firstVal == null) return;
    v.addElement(firstVal);
  }

  public void addValue(Holder val)
  {
    v.addElement(val);
    //System.out.println("Holder = "+val);
  }

  public int getNoValues()
  {
    return v.size();
  }

  public Holder getValue(int col)
  {
    //if(col < 0) return null;
    //if(col >= v.size()) return null;
    return (Holder) v.elementAt(col);
  }

}
