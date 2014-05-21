package se.modlab.generics.sstruct.executables;

import java.io.PrintWriter;
import java.io.StringWriter;

import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.BreakException;
import se.modlab.generics.exceptions.ContinueException;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.exceptions.ReturnException;
import se.modlab.generics.exceptions.StopException;
import se.modlab.generics.exceptions.UserBreak;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.comparisons.ScopeFactory;
import se.modlab.generics.sstruct.values.sString;
import se.modlab.generics.sstruct.variables.StringVariable;
import se.modlab.generics.sstruct.variables.VariableType;

public class TryStatement extends ProgramStatement {
	
	private ProgramStatement prgstatement;
	private String stringsname;
	private String tracessname;
	private String filename;
	private int line;
	private int column;
	private ProgramStatement catchstatement;
	private ScopeFactory sf;
	
	public TryStatement(ProgramStatement _prgstatement, String _stringsname, String _tracessname, String _filename, int _line, int _column, ProgramStatement _catchstatement, ScopeFactory _sf) {
		prgstatement = _prgstatement;
		stringsname = _stringsname;
		tracessname = _tracessname;
		catchstatement = _catchstatement;
		sf = _sf;
		filename = _filename;
		line = _line;
		column = _column;
	}
	
	private String getMessageForVariable(Exception e) {
		if(e instanceof NullPointerException) {
			return "Null pointer exception";
		}
		String m = e.getMessage();
		if(m != null) {
			return m;
		}
		return e.getClass().getName();
	}
	
	private String getStacktraceForVariable(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	public void execute(Scope s) throws ReturnException, IntolerableException,
			StopException, BreakException, ContinueException {
		try {
			Scope tmp = sf.getInstance(s, "Temporary program");
			prgstatement.execute(tmp);
		}
		catch(BreakException be) {
			
		}
		catch(ContinueException ce) {
			
		}
		catch(ReturnException re) {
			return;
		}
		catch(UserBreak ub) {
			
		}
		catch(UserCompiletimeError uce) {
			throw uce;
		}
		catch(Exception e) {
			//e.printStackTrace();
			Scope tmp = sf.getInstance(s, "Temporary program");
			VariableType vt = s.getFactory("string");
			StringVariable sv = new StringVariable(stringsname, vt, filename, line, column);
			sv.setInitialValue(new sString(getMessageForVariable(e)));
			tmp.addVariable(sv);
			sv = new StringVariable(tracessname, vt, filename, line, column);
			sv.setInitialValue(new sString(getStacktraceForVariable(e)));
			tmp.addVariable(sv);
			catchstatement.execute(tmp);
		}
	}

	public void verify(Scope s) throws IntolerableException {
	}

}
