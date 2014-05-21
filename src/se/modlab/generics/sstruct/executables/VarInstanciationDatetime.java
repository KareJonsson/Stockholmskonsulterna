package se.modlab.generics.sstruct.executables;

import java.util.Date;

import se.modlab.generics.exceptions.BreakException;
import se.modlab.generics.exceptions.ContinueException;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.exceptions.ReturnException;
import se.modlab.generics.exceptions.StopException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.datetimeevaluables.DatetimeEvaluable;
import se.modlab.generics.sstruct.values.sDate;
import se.modlab.generics.sstruct.variables.DateVariable;
import se.modlab.generics.sstruct.variables.Variable;

public class VarInstanciationDatetime extends VarInstanciation
{

	private DatetimeEvaluable de;

	public VarInstanciationDatetime(String varName,
			String _filename, int _line, int _column,
			DatetimeEvaluable _de) {
		super(varName, _filename, _line, _column);
		de = _de;
	}

	public void execute(Scope s)
	throws ReturnException,
	IntolerableException,
	StopException,
	BreakException,
	ContinueException {
		//System.out.println("varInstanciationString name <"+this.varName+">");
		Variable var = new DateVariable(varName, s.getFactory("datetime"), filename, line, column);
		sDate val = null;
		if(de != null) {
			val = de.evaluate(s);
		}
		else {
			val = new sDate(new Date());
		}
		var.setInitialValue(val);
		s.addVariable(var);
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = new DateVariable(varName, s.getFactory("datetime"), filename, line, column);
		if(de != null) {
			de.verify(s);
		}
		var.setDefaultInitialValue();
		s.addVariable(var);
	}

}