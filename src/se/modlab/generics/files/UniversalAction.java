package se.modlab.generics.files;

import java.lang.reflect.*;

public class UniversalAction implements Runnable
{
  protected Object target;
  protected Object[] arguments;
  protected Method m;
  private String date = null;
  
  public UniversalAction(Object target, String methodname)
       throws SecurityException
  {
    this(target, methodname, null);
  }

  public UniversalAction(Object target, String methodname, Object arg)
       throws SecurityException
  {
    this.target = target;
    
    // Now look up and save the Method to invoke on that target object
    Class<?> c, parameters[];
    //System.out.println("UniversalAction - (target instanceof Class) "+(target instanceof Class)+" "+((Class) target).getName());
    if(target instanceof Class)
      c = (Class<?>) target;
    else
      c = target.getClass();

    if(arg == null) 
      {
	parameters = new Class[0];
	arguments = new Object[0];
      }
    else
      {
	if(arg instanceof Object[])
	  {
	    arguments = (Object[]) arg;
	    int len = arguments.length;
	    parameters = new Class[len];
	    for(int i = 0 ; i < len ; i++)
	      {
		parameters[i] = (arguments[i] != null) ? arguments[i].getClass() : null;
	      }
	  }
	else
	  {
	    parameters = new Class[] { arg.getClass() };
	    arguments = new Object[] { arg };
	  }
      }
    try 
      {
	m = c.getMethod(methodname, parameters);
      }
    catch(NoSuchMethodException e)
      {
	System.out.print("UniversalAction - NoSuchMethodException "+c.getName()+"."+methodname+"(");
	for(int i = 0 ; i < parameters.length-1 ; i++)
	  {
	    System.out.print(((parameters[i] != null) ? parameters[i].getName() : "null")+", ");
	  }
	if(parameters.length > 0)
	  System.out.println(((parameters[parameters.length-1] != null) ? parameters[parameters.length-1].getName() : "null")+")");
	else
	  System.out.println(")");
	System.exit(1);
      }
    
    java.util.Date myDate = java.util.Calendar.getInstance().getTime();
    date = java.text.DateFormat.getDateTimeInstance().format(myDate);

  }
  
  public void run() 
  {
    //Object res = null;
    try 
      { 
	/*res =*/ m.invoke(target, arguments); 
      }
    catch(IllegalAccessException e) 
      {
	System.err.println("UniversalActionListener: "+e);
	e.printStackTrace();
      }
    catch(InvocationTargetException e) 
      {
	System.err.println("UniversalActionListener: "+e+" : "+e.getTargetException());
	e.printStackTrace();
      }
    //return res;
  }

  public void runButThrow(Class<?> exception) throws Throwable
  {
    //Object res = null;
    try 
      { 
	/*res =*/ m.invoke(target, arguments); 
      }
    catch(IllegalAccessException e) 
      {
	System.err.println("UniversalActionListener: "+e);
	e.printStackTrace();
      }
    catch(InvocationTargetException e) 
      {
	Throwable t = e.getTargetException();
	if(t.getClass() != exception)
	  {
	    System.err.println("UniversalActionListener: "+e+" : "+t);
	    e.printStackTrace();
	  }
	else
	  throw t;
      }
    //return res;
  }

  public static void main(String[] args)
  {
    UniversalAction t1 = new UniversalAction(System.out,"println", "Hej värld");
    t1.run(); 
    UniversalAction t2 = new UniversalAction(System.out,"println",new Object[] {"Hej värld","hjghj"});
    t2.run();
  }

  public String toString()
  {
    return "Action: "+date;
  }

} 
