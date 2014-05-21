package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.comparisons.VariableLookup;
import se.modlab.generics.sstruct.variables.*;

public class VariableFullLookup implements VariableLookup
{
  private VariablePartialLookup vpls[];

  public VariableFullLookup(VariablePartialLookup vpls[])
    throws IntolerableException
  {
    if(vpls.length == 0)
    {
      throw new InternalProgrammingError(
         "Referencing of zero steps error! In variableFullLookup.ctor");
    }
    this.vpls = vpls;
  }
  
  public VariablePartialLookup[] getPartialLookups() {
	  return vpls;
  }

  public VariableInstance getInstance(Scope s) 
    throws IntolerableException
  {
    StringBuffer sb = new StringBuffer();
    return getInstance(s, sb);
  }
 
  private VariableInstance getInstance(Scope s, StringBuffer sb) 
    throws IntolerableException
  {
    VariableInstance si = vpls[0].getFromScope(s, sb);
    if(si == null)
    {
      throw new UserCompiletimeError(sb.toString());
    }
    //System.out.println("variableFullLookup 0 "+si);
    for(int i = 1 ; i < vpls.length ; i++)
    {
      //System.out.println("Testar "+vpls[i]+" "+si);
      si = vpls[i].getFromInstance(si, s, sb);
      //System.out.println("variableFullLookup "+i+" "+si);
      if(si == null)
      {
        throw new UserRuntimeError(sb.toString()+" : "+s.getAllVariableNamesAndValues());
      }
    }
    return si;
  }
  
  public void verify(Scope s) throws IntolerableException {
	    StringBuffer sb = new StringBuffer();
	    getInstanceTypeVerification(s, sb);
  }

  private void getInstanceTypeVerification(Scope s, StringBuffer sb) 
		  throws IntolerableException
		  {
	  VariableType ti = vpls[0].getFromScopeTypeVerification(s, sb);
	  if(ti == null) {
		  throw new UserCompiletimeError(sb.toString());
	  }
	  //System.out.println("VariableFullLookup 0 "+si);
	  for(int i = 1 ; i < vpls.length ; i++) {
		  if(!vpls[0].getCanDoTypeVerification(ti, s)) {
			  return;
		  }
		  //System.out.println("Testar "+vpls[i]+" "+si);
		  ti = vpls[i].getFromInstanceTypeVerification(ti, s, sb);
		  //System.out.println("VariableFullLookup "+i+" "+si);
		  if(ti == null) {
			  throw new UserRuntimeError(sb.toString()+" : "+s.getAllVariableNamesAndValues());
		  }
	  }
  }

  public VariableInstance getInstanceScopelessByDereferencing(
            VariableInstance si,
            StringBuffer sb) 
    throws IntolerableException {
    try {
      for(int i = 0 ; i < vpls.length ; i++) {
        si = vpls[i].getFromInstanceScopeless(si, sb);
        if(si == null) {
          throw new UserCompiletimeError(sb.toString());
        }
      }
    }  
    catch(NullPointerException npe) {
      throw new UserCompiletimeError(sb.toString());
    }
    return si;
  }

  public Variable getVariable(Scope s) 
    throws IntolerableException
  {
    StringBuffer sb = new StringBuffer();
    VariableInstance si = getInstance(s, sb);
    if(!(si instanceof Variable))
    {
      throw new InternalProgrammingError(
         sb.toString()+" was not a variable but "+si.getType().getTypesName()+
         "\nHappened in variableFullLookup.getVariable()");
    }
    return (Variable) si;
  }

  public int getLine()
  {
    return vpls[0].getLine();
  }

  public String getFilename()
  {
    return vpls[0].getFilename();
  }

  public int getColumn()
  {
    return vpls[0].getColumn();
  }
  
	public String getPlace() {
		return "file "+getFilename()+", line "+getLine()+", column "+getColumn();
	}


  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(vpls[0]);
    for(int i = 1 ; i < vpls.length ; i++)
    { 
      sb.append("."+vpls[i]);
    }
    return sb.toString();
  }

	public String reproduceExpression() {
		StringBuffer sb = new StringBuffer(vpls[0].reproduceExpression());
		for(int i = 1 ; i < vpls.length ; i++) {
			sb.append("."+vpls[i].reproduceExpression());
		}
		return sb.toString();
	}

}

/*

  public SimVariable getVariable(String s)
  public SimInstance getComplexInstance(String s)

*/