package se.modlab.generics.sstruct.tables;

import java.util.*;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.files.*;

public class TableHolder
{

  private Vector<LineHolder> v = new Vector<LineHolder>();
  private int values;
  private FileCollector fc = null;

  public TableHolder(LineHolder l)
  {
    v.addElement(l);
    values = l.getNoValues();
  }

  public void addLine(LineHolder l) 
    throws IntolerableException
  {
    if(l.getNoValues() != values)
    {
      throw new UserRuntimeError(
        "Number of values changed from "+values+"\n"+
        "to "+l.getNoValues()+" on line "+v.size());
    }
    v.addElement(l);
  }

  public int getNoRows()
  {
    return v.size();
  }

  public int getNoColumns()
  {
    return values;
  }

  public Holder getValue(int row, int col)
  {
    if(row < 0) return null;
    if(row >= v.size()) return null;
    if(col < 0) return null;
    if(col >= values) return null;
    LineHolder l = (LineHolder) v.elementAt(row);
    return l.getValue(col);
  }

  public void setCollector(FileCollector fc)
  {
    this.fc = fc;
  }

  public FileCollector getCollector()
  {
    return fc;
  }

}
