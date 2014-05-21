package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.tables.*;
import se.modlab.generics.sstruct.values.*;

public class StringVariable extends Variable {

	private StringBuffer sb = null;

	public StringVariable(String name, VariableType vt, String _filename,
			int _line, int _column) {
		super(name, vt, _filename, _line, _column);
	}

	public void setDefaultInitialValue() throws IntolerableException {
		setInitialValue(new sString(""));
		sb = null;
	}

	public void setInitialValue(Holder val) throws IntolerableException {
		sb = null;
		if (val instanceof StringHolder) {
			String s = ((StringHolder) val).getString();
			setInitialValue(new sString(s));
			return;
		}
		if (val instanceof LongHolder) {
			throw new UserRuntimeError("The " + val
					+ " was supposed to match a variable of type string.\n"
					+ "That is not properly defined." + "\n"
					+ "This happened to the variable declared " + getPlace());
		}
		if (val instanceof DoubleHolder) {
			throw new UserRuntimeError("The " + val
					+ " was supposed to match a variable of type string.\n"
					+ "That is not properly defined." + "\n"
					+ "This happened to the variable declared " + getPlace());
		}
		if (val instanceof BooleanHolder) {
			throw new UserRuntimeError("The " + val
					+ " was supposed to match a variable of type string.\n"
					+ "That is not properly defined." + "\n"
					+ "This happened to the variable declared " + getPlace());
		}
		throw new InternalError("Logics in method "
				+ "longVariable.setInitialValue(holder val)\n" + "is outdated.");
	}

	public void setInitialValue(sValue initialVal) throws IntolerableException {
		sb = null;
		if (!(initialVal instanceof sString)) {
			throw new UserRuntimeError("String variable " + name
					+ " set to non boolean value " + initialVal + "\n"
					+ "This happened to the variable declared " + getPlace());
		}
		val = initialVal.copy();
	}

	public void setValue(sValue val) throws IntolerableException {
		sb = null;
		if (!(val instanceof sString))
			throw new UserRuntimeError("String variable " + name
					+ " set to non boolean value " + val + "\n"
					+ "This happened to the variable declared " + getPlace());
		if (this.val == null) {
			throw new InternalError("Variable " + name + " had no value \n"
					+ "prior to call to SimStringVariable.setValue with value "
					+ val);
		}
		this.val = val;
	}

	public void setValue(VariableInstance si) throws IntolerableException {
		sb = null;
		if (!(si instanceof StringVariable)) {
			throw new UserRuntimeError("String variable " + name
					+ " set to value of \n" + "type " + si + "\n"
					+ "This happened to the variable declared " + getPlace());
		}
		setValue(((StringVariable) si).getValue());
	}

	public void setInitialValue(VariableInstance val)
			throws IntolerableException {
		setValue(val);
	}

	public VariableInstance copy() throws IntolerableException {
		StringVariable sv = new StringVariable(name, vt, filename, line, column);
		sv.setInitialValue(getValue());
		return sv;
	}

	public sDouble getDoubleValue() {
		return null;
	}

	public void setValue(String s) throws IntolerableException {
		sb = null;
		setValue(new sString(s));
	}

	public Class<?> getValueClass() {
		return new sString("").getClass();
	}

	public void append(sValue v) throws IntolerableException {
		if(sb != null) {
			sb.append(v.getString());
			return;
		}
		sb = new StringBuffer(val.getString());
		setDefaultInitialValue();
		sb.append(v.getString());
	}

	public sValue getValue() throws IntolerableException {
		if (val == null) {
			throw new InternalError(
					"variable.getValue called with no value set prior to get.\n"
							+ "Happened in class " + getClass().getName()
							+ ".\nName of variable " + "is " + name);
		}
		if(sb != null) {
			return new sString(sb.toString());
		}
		return val;
	}

}