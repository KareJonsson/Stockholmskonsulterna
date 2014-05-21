package se.modlab.generics.sstruct.comparisons;

import java.util.*;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.variables.*;

public class Scope 
{

	protected String name = null;
	protected Hashtable<String, VariableInstance> table_variables = new Hashtable<String, VariableInstance>();
	protected Hashtable<String, Procedure> table_procedures = new Hashtable<String, Procedure>();
	protected Hashtable<String, VariableFactory> table_factories = new Hashtable<String, VariableFactory>();
	protected Scope subjacent = null;
	private int no;

	public Scope(String name)
	{
		this.name = name;
	}

	public Scope(Scope subjacent)
	{
		this.subjacent = subjacent;
	}

	public Scope(Scope subjacent, String name)
	{
		this.subjacent = subjacent;
		this.name = name;
	}

	/*
  public scope copy()
    throws intolerableException
  {
    scope s = new scope(subjacent, name);
    s.no = no;
    s.table_procedures = table_procedures;
    s.table_factories = table_factories;
    Enumeration keys = table_variables.keys();
    while(keys.hasMoreElements())
    {
      variableInstance vi = (variableInstance) table_variables.get(keys.nextElement());
      s.addComplexInstance(vi.copy());
    }
    return s;
  }
	 */

	public void setName(String name)
	{
		this.name = name;
		//System.out.println("Scope - "+name);
	}

	public String getName()
	{
		return name;
	}

	public Scope getSubjacent()
	{
		return subjacent;
	}

	public void addVariable(Variable var)
	throws IntolerableException
	{
		var.setScopesName(name);
		Object o = table_variables.get(var.getName());
		if(o != null) 
		{    	
			String place = null;
			if(o instanceof VariableInstance) {
				place = "The previous was in "+((VariableInstance) o).getPlace();
			}
			throw new UserCompiletimeError(
					"Second instanciation of instance "+
					var.getName()+" in this scopes level ("+name+").\n"+
					(( place != null)? place : ""));
		}
		table_variables.put(var.getName(), var);
	}

	public void addProcedure(Procedure proc)
	throws IntolerableException
	{
		if(proc == null) {
			Throwable t = new Throwable();
			t.printStackTrace();
			return;
		}
		Object o = table_procedures.get(proc.getName());
		if(o != null) 
		{
			String place = null;
			if(o instanceof Procedure) {
				place = "The previous was in "+((Procedure) o).getPlace();
			}

			throw new UserCompiletimeError("Second instanciation of procedure "+
					proc.getName()+" in this scopes level ("+name+").\n"+
					(( place != null)? place : ""));
		}
		table_procedures.put(proc.getName(), proc);
	}

	protected Scope getFactorysScope(VariableFactory vf)
	{
		if(vf == null) return null;
		Object o = table_factories.get(vf.getTypesName());
		if(o != null)
		{
			return this;
		}
		return subjacent.getFactorysScope(vf);
	}

	public void addFactory(VariableFactory vf)
	throws IntolerableException
	{
		Object o = table_factories.get(vf.getTypesName());
		if(o != null) 
		{
			String place = null;
			if(o instanceof VariableInstance) {
				place = "The previous was in "+((VariableInstance) o).getPlace();
			}
			throw new UserCompiletimeError(
					"Second instanciation of type "+
					vf.getTypesName()+" in this scopes level ("+name+").\n"+
					(( place != null)? place : ""));
		}
		if(vf instanceof VariableVectorFactory)
		{
			VariableVectorFactory vvf = (VariableVectorFactory) vf;
			VariableFactory subjacentFactory = vvf.getElementsFactory();
			Scope s = getFactorysScope(subjacentFactory);
			if(s != null)
			{
				VariableFactory _vf = s.getFactory(vf.getTypesName());
				if(_vf == null)
				{
					s.table_factories.put(vf.getTypesName(), vf);
				}
			}
		}
		table_factories.put(vf.getTypesName(), vf);
	}

	public VariableFactory addFactoryUniquely(VariableFactory vf)
	throws IntolerableException
	{
		VariableFactory _vf = getFactory(vf.getTypesName());
		if(_vf != null) return _vf;
		addFactory(vf);
		return vf;
	}

	public void addComplexInstance(VariableInstance inst)
	throws IntolerableException
	{
		inst.setScopesName(name);
		//System.out.println("scope addComplexInstance "+inst.getName());
		Object o = table_variables.get(inst.getName());
		if(o != null) 
		{
			String place = null;
			if(o instanceof VariableInstance) {
				place = "The previous was in "+((VariableInstance) o).getPlace();
			}
			throw new UserCompiletimeError(
					"Second instanciation of instance "+
					inst.getName()+" in this scopes level ("+name+").\n"+
					(( place != null)? place : ""));
		}
		table_variables.put(inst.getName(), inst);
	}

	public boolean removeComplexInstance(VariableInstance inst)
	throws IntolerableException
	{
		if(table_variables.contains(inst)) {
			table_variables.remove(inst.getName());
			table_variables.remove(inst);
			return true;
		}
		return false;
	} 

	public Variable getVariable(String s)
	{
		VariableInstance inst = (VariableInstance) table_variables.get(s);
		if((inst != null) && 
				(!(inst instanceof Variable)))
		{
			return null;
		}
		Variable var = (Variable) inst;
		if(var != null) return var;
		if(subjacent == null) return null;
		return subjacent.getVariable(s);
	}

	public VariableInstance getComplexInstance(String s)
	{
		VariableInstance inst = (VariableInstance) table_variables.get(s);
		if(inst != null)
		{
			return inst;
		}
		if(subjacent == null) return null;
		return subjacent.getComplexInstance(s);
	}

	public Procedure getProcedure(String s)
	{
		Procedure proc = (Procedure) table_procedures.get(s);
		if(proc != null) return proc;
		if(subjacent == null) return null;
		return subjacent.getProcedure(s);
	}

	/*
	public Vector<procedure> getProcedures() {
		Vector<procedure> out = new Vector<procedure>();
		for(Object name : table_procedures.keySet()) {
			out.add((procedure) table_procedures.get(name));
		}
		if(subjacent == null) return out;
		out.addAll(subjacent.getProcedures());
		return out;
	}
	*/
	
	public Scope getBottomScope() {
		if(subjacent == null) {
			return this;
		}
		return subjacent.getBottomScope();
	}

	public VariableFactory getFactory(String s)
	{
		VariableFactory vf = (VariableFactory) table_factories.get(s);
		if(vf != null) return vf;
		if(subjacent == null) return null;
		return subjacent.getFactory(s);
	}

	public void addVariable(Variable var, String name)
	throws IntolerableException
	{
		Object o = table_variables.get(name);
		if(o != null) 
		{
			String place = null;
			if(o instanceof VariableInstance) {
				place = "The previous was in "+((VariableInstance) o).getPlace();
			}
			throw new UserCompiletimeError("Second instanciation of variable "+
					name+" in this scopes level ("+name+").\n"+
					(( place != null)? place : ""));
		}
		table_variables.put(name, var);
		//no++;
	}

	public void addComplexInstance(VariableInstance inst, String name)
	throws IntolerableException
	{
		//System.out.println("scope addComplexInstance "+inst.getName());
		Object o = table_variables.get(name);
		if(o != null) 
		{
			String place = null;
			if(o instanceof VariableInstance) {
				place = "The previous was in "+((VariableInstance) o).getPlace();
			}
			throw new UserCompiletimeError("Second instanciation of instance "+
					inst.getName()+" in this scopes level ("+name+").\n"+
					(( place != null)? place : ""));
		}
		table_variables.put(name, inst);
	}

	private String getNameString()
	{
		StringBuffer sb = new StringBuffer("{");
		Enumeration<?> e = table_variables.keys();
		while(e.hasMoreElements())
		{
			sb.append(e.nextElement()+" ");
		}
		return sb.toString()+"}";
	}

	public String toString()
	{
		//String path;
		String _name = name+" "+no;
		if(subjacent == null) return _name+" "+getNameString()+"/ground";
		return _name+"/"+subjacent;
	}
	
	public String getAllVariableNames() {
		StringBuffer sb = new StringBuffer();
		for(Object name : table_variables.keySet()) {
			sb.append(name.toString()+" ");
		}
		return "{"+sb.toString().trim()+"}";
	}

	public String getAllVariableNamesAndValues() {
		StringBuffer sb = new StringBuffer();
		for(Object name : table_variables.keySet()) {
			sb.append(name.toString()+"="+table_variables.get(name)+" ");
		}
		return "{"+sb.toString().trim()+"} "+((subjacent != null) ? subjacent.getAllVariableNamesAndValues() : "");
	}

	public String[] getVariableNames() {
		String out[] = new String[table_variables.size()];
		int idx = 0;
		for(Object name : table_variables.keySet()) {
			out[idx] = name.toString();
		}
		return out;
	}

}
