package se.modlab.generics.util;

import java.util.Vector;
import java.util.Comparator;

public class Sort 
{
  public static void addUniquely(Vector vec, Object str, Comparator sc)
  {
    addUniquely_sayWhere(vec, str, sc);
  }
  
  public static int addUniquely_sayWhere(Vector vec, Object obj, Comparator sc)
  {
    if(vec == null) return -1;
    if(vec.size() == 0)
    {
      vec.addElement(obj);
      return 0;
    }
    
    Object t = vec.elementAt(0);
    int comp = sc.compare(t, obj);
    if(comp == 0) return 0;
    if(comp > 0)
    {
      vec.insertElementAt(obj, 0);
      return 0;
    }

    int upper = vec.size() - 1;
    t = vec.elementAt(upper); 
    comp = sc.compare(obj, t);

    if(comp == 0) return upper;
    if(comp > 0)
    {
      vec.addElement(obj);
      return upper + 1;
    }

    int lower = 0;

    while((lower + 1) != upper)
    {
      int test = (int) ((upper + lower) / 2);	
      Object t_obj = vec.elementAt(test);
      comp = sc.compare(t_obj, obj);
      if(comp == 0)
        return test;

      if(comp < 0)
        lower = test;
      else
        upper = test;
    }

    vec.insertElementAt(obj, upper);
    return upper;
  }

  public static void add(Vector vec, Object str, Comparator sc)
  {
    add_sayWhere(vec, str, sc);
  }
  
  public static int add_sayWhere(Vector vec, Object obj, Comparator sc)
  {
    if(vec == null) return -1;
    if(vec.size() == 0)
    {
      vec.addElement(obj);
      return 0;
    }
    
    Object t = vec.elementAt(0);
    int comp = sc.compare(t, obj);
    if(comp >= 0)
    {
      //System.out.println("Sort - first "+obj);
      vec.insertElementAt(obj, 0);
      return 0;
    }

    int upper = vec.size() - 1;
    t = vec.elementAt(upper); 
    comp = sc.compare(t, obj);
    //System.out.println("generics.Sort: comp = "+comp+" for "+obj);
    if(comp <= 0)
    {
      //System.out.println("Sort - last "+obj);
      vec.addElement(obj);
      return upper + 1;
    }

    int lower = 0;

    if(lower == upper)
    {
      // Illogical it appears but if the comparator say 
      // below or above randomly it will happen. This is not 
      // unreasonable.
      //System.out.println("generics sort - XXX");
      vec.insertElementAt(obj, lower);
      return lower;
    }

    while((lower + 1) != upper)
    {
      //System.out.println("SORT upper = "+upper+", lower = "+lower+", size = "+vec.size());
      int test = (int) ((upper + lower) / 2);	
      Object t_obj = vec.elementAt(test);
      comp = sc.compare(t_obj, obj);
      if(comp == 0)
      {
        vec.insertElementAt(obj, test);
        return test;
      }

      if(comp < 0)
        lower = test;
      else
        upper = test;
    }

    vec.insertElementAt(obj, upper);
    return upper;
  }

  public static Comparator<Object> getStringSort()
  {
    return new Comparator<Object>()
      {
	public int compare(Object first, Object second)
	  {
	    return ((String) first).compareTo((String) second);
	  }
      };
  }

  public static Comparator<Object> getIntegerSort()
  {
    return new Comparator<Object>()
      {
	public int compare(Object first, Object second)
	  {
	    return (((Integer) first).intValue() - ((Integer) second).intValue());
	  }
	
      };
  }

  public static Vector getSorted(Vector v, Comparator sc)
  {
    Vector<? super Object> res = new Vector<Object>();
    for(int i = 0 ; i < v.size() ; i++)
      {
	add(res, v.elementAt(i), sc);
      }
    return res;
  }
  
  public static void sort(Vector v, Comparator sc)
  {
    Vector<?> res = getSorted(v, sc);
    v.removeAllElements();
    for(int i = 0 ; i < res.size() ; i++)
    {
      v.addElement(res.elementAt(i));
    }
  }

  public static boolean isPresent(Vector vec, Object str, Comparator sc)
  {
    return sayWhere(vec, str, sc) != -1;
  }

  public static int sayWhere(Vector vec, Object obj, Comparator sc)
  {
    if(vec == null) return -1;
    if(vec.size() == 0) return -1;
    
    Object t = vec.elementAt(0);
    int comp = sc.compare(t, obj);
    if(comp == 0) return 0;

    int upper = vec.size() - 1;
    t = vec.elementAt(upper); 
    comp = sc.compare(obj, t);

    if(comp == 0)
    {
      return upper;
    }

    int lower = 0;

    while((lower + 1) != upper)
    {
      int test = (int) ((upper + lower) / 2);	
      Object t_obj = vec.elementAt(test);
      comp = sc.compare(t_obj, obj);
      if(comp == 0)
      {
        return test;
      }

      if(comp < 0)
        lower = test;
      else
        upper = test;
    }

    return -1;
  }

  public static void main(String args[])
  {
    Comparator<Object> c = getStringSort();
    Vector<Object> v = new Vector<Object>();
    add(v, "AAA", c);
    add(v, "DDD", c);
    add(v, "BBB", c);
    add(v, "EEE", c);
    add(v, "DDDD", c);
    for(int i = 0 ; i < v.size() ; i++)
    {
      System.out.println("Nr: "+i+" "+v.elementAt(i));
    }
    v = new Vector<Object>();
    c = getIntegerSort();
    add(v, new Integer(7), c);
    add(v, new Integer(6), c);
    add(v, new Integer(8), c);
    add(v, new Integer(9), c);
    add(v, new Integer(4), c);
    add(v, new Integer(88), c);
    add(v, new Integer(77), c);

    for(int i = 0 ; i < v.size() ; i++)
    {
      System.out.println("Nr: "+i+" "+v.elementAt(i));
    }
  }
  
}

