package se.modlab.generics.sstruct.evaluables;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.InternalProgrammingError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sLong;
import se.modlab.generics.sstruct.values.sValue;
import se.modlab.generics.sstruct.variables.*;

public class VariablePartialLookup 
{

	private String name;
	private String filename;
	private int line;
	private int column;
	private ArithmeticEvaluable ae;

	public VariablePartialLookup(String _name, 
			String _filename,
			int _line, 
			int _column, 
			ArithmeticEvaluable _ae)
	{
		name = _name;
		filename = _filename;
		line = _line;
		column = _column; 
		ae = _ae; 
	} 

	public VariableInstance getFromScope(Scope s, StringBuffer sb) throws IntolerableException {
		sb.append(name);
		VariableInstance si = s.getComplexInstance(name);
		if(si == null) {
			sb.append(" Reference in file "+filename+" on line "+line+", column "+column);
			return null;
		}
		if((!(si instanceof Complex)) && 
				(((!(si instanceof VariableVector)) && 
						(!(si instanceof VariableVectorFromFile))))) {
			//throw new internalError("Logics in variablePartialLookup.getFromScope obsolete! Type was "+si.getClass().getName());
			throw new UserCompiletimeError("Variable "+si.getName()+" was dereferenced at "+si.getPlace()+". Type is "+si.getType().getTypesName());
		}
		int i = 0;
		if((si instanceof VariableVector) || (si instanceof VariableVectorFromFile)) {
			if(ae == null) {
				//sb.append(" Reference in file "+filename+" on line "+line+", column "+column+" is of vector type\n"+
				//          "but there is no indexation."); 
				return si;
			}
			sValue index = ae.evaluate(s);
			if(!(index instanceof sLong)) {
				sb.append(" Index for vector "+name+" was not evaluated to a long value."+
						"\nIt became "+index+".\n"+
						"Happened in file "+filename+" on line "+line+", column "+column);
				return null;
			}
			i = ((sLong) index).getLong().intValue();
			sb.append("["+i+"]");
			if(i < 0) {
				sb.append(" Index for vector "+name+
						" was evaluated to the negative number "+i+".\n"+
						"Happened in file "+filename+" on line "+line+", column "+column);
				return null;
			}
		}
		if(si instanceof VariableVector) {
			VariableVector siv = (VariableVector) si;
			if(i >= siv.getLength()) {
				sb.append(" Index for vector "+name+" was evaluated to "+i+
						"\nbut the vector only ranges from 0 to "+
						(siv.getLength()-1)+".\n"+
						"Happened in file "+filename+" on line "+line+", column "+column);
				return null;
			}
			return siv.getVectorElement(i);
		}
		if(si instanceof VariableVectorFromFile) {
			VariableVectorFromFile sivff = (VariableVectorFromFile) si;
			if(i >= sivff.getLength()) {
				sb.append(" Index for vector "+name+" was evaluated to "+i+
						"\nbut the vector only ranges from 0 to "+
						(sivff.getLength()+1)+".\n"+
						"Happened in file "+filename+" on line "+line+", column "+column);
				return null;
			}
			return sivff.getVectorElement(i);
		}
		if(ae != null) {
			sb.append(" An indexation for "+name+" is wrong since it is of type "+
					si.getType().getTypesName()+" and not of vector type."+".\n"+
					"Happened in file "+filename+" on line "+line+", column "+column);
			return null;
		}
		return si;
	}

	public VariableFactory getFromScopeTypeVerification(Scope s, StringBuffer sb)
			throws IntolerableException {
		sb.append(name);
		VariableInstance si = s.getComplexInstance(name);
		if(si == null) {
			sb.append(" Reference in file "+filename+" on line "+line+", column "+column);
			return null;
		}
		if((!(si instanceof Complex)) && 
				(((!(si instanceof VariableVector)) && 
						(!(si instanceof VariableVectorFromFile))))) {
			//throw new internalError("Logics in variablePartialLookup.getFromScope obsolete! Type was "+si.getClass().getName());
			throw new UserCompiletimeError("Variable "+si.getName()+" was dereferenced at "+si.getPlace()+". Type is "+si.getType().getTypesName());
		}
		VariableType vt = si.getType();
		if(vt instanceof VariableVectorFactory) {
			//System.out.println("VariablepartialLookup.getFromScopeTypeVerification VariableVectorFactory name="+name);
			VariableVectorFactory vvf = (VariableVectorFactory) vt;
			return vvf.getElementsFactory();
		}
		if(vt instanceof VariableVectorFromFile) {
			//System.out.println("VariablepartialLookup.getFromScopeTypeVerification VariableVectorFromFile name="+name);
			VariableVectorFromFile vvff = (VariableVectorFromFile) vt;
			return vvff.getVariableFactory();
		}
		if(vt instanceof VariableFactory) {
			VariableFactory vf = (VariableFactory) vt;
			return vf;
		}
		/*
		if(vt instanceof ComplexVariableFactory) {
			//System.out.println("VariablepartialLookup.getFromScopeTypeVerification ComplexVariableFactory name="+name);
			ComplexVariableFactory cvf = (ComplexVariableFactory) vt;
			return cvf;
		}
		if(vt instanceof UntypedStructReferenceFactory) {
			ComplexVariableFactory cvf = (ComplexVariableFactory) vt;
			return vt;
		}
		*/
		throw new InternalError("Reference "+si.getName()+" accessed at "+si.getPlace()+" is of unexpected class "+si.getClass().getName()+". Types class is "+vt.getClass().getName());
	}

	public VariableInstance getFromInstanceScopeless(VariableInstance si, StringBuffer sb) throws IntolerableException {
		return getFromInstance(si, null, sb);
	}

	public boolean getCanDoTypeVerification(VariableType ti, Scope s) throws IntolerableException {
		if(ti instanceof VariableVectorFactory) {
			return false;
		}
		if(ti instanceof ComplexVariableFactory) {
			return true;
		}
		return false;
	}

	public VariableFactory getFromInstanceTypeVerification(VariableType ti, Scope s, StringBuffer sb) throws IntolerableException {
		//if((!(ti instanceof ComplexVariableFactory)) && (!(ti instanceof VariableVectorFactory))) {
		//	throw new InternalProgrammingError("Logics in VariablePartialLookup.getFromInstanceTypeVerification obsolete! type "+ti.getClass().getName());
		//}
		if(ti instanceof VariableVectorFactory) {
			sb.append(" Internal: Weird vector of vector error. This is generated from "+
					getClass().getName()+". Grammar must have been altered."+".\n"+
					"Happened in "+getPlace());
			return null;
		}
		if(ti instanceof ComplexVariableFactory) {
			return ((ComplexVariableFactory) ti).getMembersType(name);
		}
		throw new InternalProgrammingError("Logics in VariablePartialLookup.getFromInstanceTypeVerification obsolete! type "+ti.getClass().getName());
		/*
		VariableFactory member = ((ComplexVariableFactory) ti).getMembersType(name);
		//System.out.println("variablePartialLookup - "+name+" : "+member);
		if(member == null) {
			//System.out.println("variablePartialLookup - 1 - member = null: "+si);
			sb.append("."+name+" not known.\nHappened in "+getPlace());
			return null;
		}
		return member;
		*/
	}

	public VariableInstance getFromInstance(VariableInstance si, Scope s, StringBuffer sb) throws IntolerableException {
		if((!(si instanceof Complex)) && 
				(!(si instanceof VariableVector))) {
			throw new InternalProgrammingError("Logics in VariablePartialLookup.getFromInstance obsolete!");
		}
		if(si instanceof VariableVector) {
			sb.append(" Internal: Weird vector of vector error. This is generated from "+
					getClass().getName()+". Grammar must have been altered."+".\n"+
					"Happened in "+getPlace());
			return null;
		}
		VariableInstance member = null;
		try {
			member = ((Complex) si).getMember(name);
		}
		catch(IntolerableException ie) {
			String mess = ie.getMessage()+"\nError occurred at "+getPlace();
			UserRuntimeError ure = new UserRuntimeError(mess, ie);
			throw ure;
		}
		//System.out.println("variablePartialLookup - "+name+" : "+member);
		if(member == null) {
			//System.out.println("variablePartialLookup - 1 - member = null: "+si);
			sb.append("."+name+" not known.\n"+
					"Happened in "+getPlace());
			return null;
		}
		//System.out.println("variablePartialLookup - "+member.getClass().getName());
		sb.append("."+name);
		if(member instanceof VariableVector) {
			if(ae == null) {
				//sb.append(" Reference in file "+filename+" on line "+line+", column "+column+" is of vector type\n"+
				//          "but there is no indexation."); 
				return member;
			}
			sValue index = ae.evaluate(s);
			if(!(index instanceof sLong)) {
				//System.out.println("variablePartialLookup - 2 - !(index instanceof sLong)");
				sb.append(" Index for vector "+name+" was not evaluated to a long value."+
						"\nIt became "+index+"."+".\n"+
						"Happened in "+getPlace());
				return null;
			}
			int i = ((sLong) index).getLong().intValue();
			sb.append("["+i+"]");
			if(i < 0) {
				//System.out.println("VariablePartialLookup - 3 - i < 0");
				sb.append(" Index for vector "+name+" was evaluated to the negative number "+i+".\n"+
						"Happened in "+getPlace());
				return null;
			}
			//System.out.println("Type = "+si);
			VariableVector siv = (VariableVector) member;
			if(i >= siv.getLength()) {
				//System.out.println("VariablePartialLookup - 4 - i >= siv.getLength()");
				sb.append(" Index for vector "+name+" was evaluated to "+i+
						"\nbit the vector only ranges from 0 to "+
						(siv.getLength()-1)+".\n"+
						"Happened in "+getPlace());
				return null;
			}
			return siv.getVectorElement(i);
		}
		if(member instanceof VariableVectorFromFile) {
			if(ae == null) {
				//sb.append(" Reference in file "+filename+" on line "+line+", column "+column+" is of vector type\n"+
				//          "but there is no indexation."); 
				return member;
			}
			sValue index = ae.evaluate(s);
			if(!(index instanceof sLong)) {
				sb.append(" Index for vector "+name+" was not evaluated to a long value."+
						"\nIt became "+index+"."+".\n"+
						"Happened in "+getPlace());
				return null;
			}
			int i = ((sLong) index).getLong().intValue();
			sb.append("["+i+"]");
			if(i < 0) {
				sb.append(" Index for vector "+name+" was evaluated to the negative number "+i+".\n"+
						"Happened in "+getPlace());
				return null;
			}
			//System.out.println("Type = "+si);
			VariableVectorFromFile siv = (VariableVectorFromFile) member;
			if(i >= siv.getLength()) {
				sb.append(" Index for vector "+name+" was evaluated to "+i+
						"\nbit the vector only ranges from 0 to "+
						(siv.getLength()-1)+".\n"+
						"Happened in "+getPlace());
				return null;
			}
			return siv.getVectorElement(i);
		}
		if(ae != null) {
			sb.append(" An indexation for "+name+" is wrong since it is of type "+
					member.getType().getTypesName()+" and not of vector type."+".\n"+
					"Happened in "+getPlace());
			return null;
		}
		return member;
	}

	public String getName() {
		return name;
	}

	public String getFilename() {
		return filename;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return line;
	}

	public ArithmeticEvaluable getIndexing() {
		return ae;
	}

	public String getPlace() {
		return "file "+filename+", line "+line+", column "+column;
	}

	public String toString() {
		return name+" "+getPlace()+((ae != null)? "[...]":"");
	}

	public String reproduceExpression() {
		if(ae == null) {
			return name;
		}
		return name+"["+ae.reproduceExpression()+"]";
	}



}

