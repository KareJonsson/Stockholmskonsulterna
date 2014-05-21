package se.modlab.generics.sstruct.variables;

import se.modlab.generics.bshro.ifc.HierarchyObject;
import se.modlab.generics.exceptions.*;
import se.modlab.generics.files.*;
import se.modlab.generics.sstruct.tables.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class VariableVectorFromFile extends VariableInstance
{

	protected VariableFactory vf = null;
	private VariableInstance siv[];
	private FileCollector fc = null;
	private DatetimeHandler dh = null;

	public VariableVectorFromFile(String name, 
			VariableType vt,
			VariableFactory vf,  
			String filename,
			String _filename, int _line, int _column)
	throws IntolerableException
	{
		this(name, vt, vf, filename, null, _filename, _line, _column);
	}

	public VariableVectorFromFile(String name, 
			VariableType vt,
			VariableFactory vf, 
			String filename,
			DatetimeHandler _dh,
			String _filename, int _line, int _column)
	throws IntolerableException
	{
		super(name, vt, _filename, _line, _column);
		this.vf = vf;
		dh = _dh;
		if(dh == null)
		{
			dh = new DefaultDatetimeHandler();
		}
		control();
		if(vf == null)
		{
			new Throwable().printStackTrace();
			System.exit(-1);
		}
		try {
			initiateVector(new FileInputStream(HierarchyObject.getReferenceFilePath()+filename));
		}
		catch(FileNotFoundException fnfe) {
			throw new InternalProgrammingError("File "+filename+" was not found.", fnfe);
		}
	}

	public VariableVectorFromFile(String name, 
			VariableType vt,
			VariableFactory vf,  
			InputStream _is,
			String _filename, int _line, int _column)
	throws IntolerableException
	{
		this(name, vt, vf, _is, null, _filename, _line, _column);
	}

	public VariableVectorFromFile(String name, 
			VariableType vt,
			VariableFactory vf, 
			InputStream _is,
			DatetimeHandler _dh,
			String _filename, int _line, int _column)
	throws IntolerableException
	{
		super(name, vt, _filename, _line, _column);
		this.vf = vf;
		dh = _dh;
		if(dh == null)
		{
			dh = new DefaultDatetimeHandler();
		}
		control();
		if(vf == null)
		{
			new Throwable().printStackTrace();
			System.exit(-1);
		}
		initiateVector(_is);
	}

	protected void control()
	throws IntolerableException
	{
		if(!(vf instanceof ComplexVariableFactory))
		{
			String s = "The type of the vector elements must be a struct created by\n"+
			"a typedef declaration. Here it was a "+vf.getTypesName();
			//System.out.println("variableVectorFromFile - control \n"+s);
			throw new UserRuntimeError(s);
		}
	}

	protected VariableInstance getInstance(String name)
	throws IntolerableException
	{
		return vf.getInstance(name, filename, line, column);
	}

	private void initiateVector(InputStream stream)
	throws IntolerableException {
		ComplexVariableFactory factory = (ComplexVariableFactory) vf;
		int members = factory.getNoMembers();

		TableHolder tab = TabParser.parseFromInputStream(stream, dh);   
		fc = tab.getCollector();
		int cols = tab.getNoColumns();
		int rows = tab.getNoRows();

		Vector<ComplexVariable> _siv = new Vector<ComplexVariable>();
		for(int actualRow = 0 ; actualRow < rows ; actualRow++) {
			ComplexVariable struct = 
				(ComplexVariable) getInstance("["+actualRow+"]");
			_siv.addElement(struct);
			for(int memsno = 0 ; memsno < members ; memsno++) {
				if(memsno >= cols) {
					VariableInstance si = struct.getMember(memsno);
					si.setDefaultInitialValue();
				}
				else {
					Holder h = tab.getValue(actualRow, memsno);
					VariableInstance si = struct.getMember(memsno);
					if(h instanceof ErrorHolder) {
						throw new UserRuntimeError(
								"Non arithmetic or boolean value "+h+" in file "+filename+"\n"+
								"This happened to the variable declared "+getPlace());
					}
					if(h instanceof ErrorHolder) {
						si.setDefaultInitialValue();
						continue;
					}
					if(!(si instanceof Variable)) {
						if((si instanceof ComplexVariable) ||
								(si instanceof VariableVector)) {
							throw new UserRuntimeError(
									"Member number "+memsno+" is of type "+si.getType().getTypesName()+
									". The members may only be of the types\n"+
									"boolean, long and double. Since the number of fields in the file\n"+
									filename+" is "+cols+", the first "+cols+" members must be of the\n"+
									"three types mentioned.\n"+
									"This happened to the variable declared "+getPlace());
						}
						throw new InternalError(
								"Logics in SimVariableVectorFromFile.initiateVector\n"+
								"is obsolete.");
					}
					Variable var = (Variable) si;
					var.setInitialValue(h);
				}
			}
		}

		siv = new VariableInstance[_siv.size()];
		for(int i = 0 ; i < siv.length ; i++) {
			siv[i] = (VariableInstance) _siv.elementAt(i);
		}
	}
	
	  public boolean isNull() {
		  return siv == null;
	  }

	public int getLength()
	{
		return siv.length;
	}

	public VariableFactory getVariableFactory()
	{
		return vf;
	}

	public FileCollector getCollector()
	{
		return fc;
	}

	public VariableInstance getVectorElement(int i)
	throws IntolerableException
	{
		if(i < 0) return null;
		if(i >= siv.length) return null;
		return siv[i];
	}

	public void setDefaultInitialValue()
	throws IntolerableException
	{
		throw new InternalError(
				"Method variableVectorFromFile.setDefaultInitialValue\n"+
				"was called! On variable "+name);
	}

	public void setScopesName(String scopesName)
	{
		for(int i = 0 ; i < siv.length ; i++)
		{
			siv[i].setScopesName(scopesName);
		}
	}

	/*
  public void resetInitialValue() 
    throws intolerableException
  {
    for(int i = 0 ; i < siv.length ; i++)
    {
      siv[i].resetInitialValue();
    }
  }
	 */

	public void setValue(VariableInstance si)
	throws IntolerableException
	{
		if(si.getType() != vt)
		{
			throw new InternalError( 
					"Vector variable "+name+" of type "+
					vt.getTypesName()+" set to value of \n"+
					"type "+si.getType().getTypesName());
		}
		VariableVector siv_there = (VariableVector) si;
		for(int i = 0 ; i < siv.length ; i++)
		{
			siv[i].setValue(siv_there.getVectorElement(i));
		}
	}

	public void setInitialValue(VariableInstance val) 
	throws IntolerableException
	{
		setValue(val);
	}

	/*
  public variableInstance copy()
    throws intolerableException
  {
    variableVector vv = new variableVector(name, vt, vf, siv.length);
    for(int i = 0 ; i < siv.length ; i++)
    {
      vv.siv[i] = siv[i].copy();
    }
    return vv;
  }
	 */

	public String toString()
	{
		return "Name "+name+
		", vector length = "+siv.length+
		", type "+vf.getTypesName();
	}
	
	public boolean valueEquals(VariableInstance sv) throws IntolerableException {
		if(this == sv) {
			return true;
		}
		if(!(sv instanceof VariableVector)) {
			return false;
		}
		VariableVector other = (VariableVector) sv;
		if(vf != other.vf) {
			return false;
		}
		if(siv.length != other.siv.length) {
			return false;
		}
		for(int i = 0 ; i < siv.length ; i++) {
			if(!siv[i].valueEquals(other.siv[i])) {
				return false;
			}
		}
		return true;
	}


}
