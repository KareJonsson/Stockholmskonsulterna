package se.modlab.generics.sstruct.values;

import java.util.Date;

import se.modlab.generics.exceptions.*;

abstract public class sValue
{
  //private static int antal = 0;
  private double time;

  public abstract double getValue() throws IntolerableException;
  public abstract Double getDouble();
  public abstract Long getLong();
  public abstract Boolean getBoolean();
  public abstract String getString();
  public abstract Date getDate();
  public abstract Object getObject();
  public abstract sValue copy();
  public abstract boolean valueEquals(sValue val) throws IntolerableException;
  public abstract boolean less(sValue val) throws IntolerableException;
  public abstract boolean lessEq(sValue val) throws IntolerableException;

  public void setTime(double time) {
    this.time = time;
  }

  public double getTime() {
    return time;
  }

}
