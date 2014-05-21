package se.modlab.generics.sstruct.datetimeevaluables;

import java.util.Date;

import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.ArithmeticEvaluable;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sDate;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.values.sValue;
import se.modlab.generics.sstruct.values.sLong;

public class DatetimeFromMillis implements DatetimeEvaluable {
	
	private ArithmeticEvaluable ae;
	
	public DatetimeFromMillis(ArithmeticEvaluable _ae) {
		ae = _ae;
	}

	public sDate evaluate(Scope s) throws IntolerableException {
		sValue val = ae.evaluate(s);
		if(val instanceof sLong) {
			long l = ((sLong) val).getLong();
			Date d = new Date(l);
			return new sDate(d);
		}
		if(val instanceof sDouble) {
			double  db = ((sDouble) val).getDouble();
			Date da = new Date((long) db);
			return new sDate(da);
		}
		if(val instanceof sDate) {
			return (sDate) val;
		}
		throw new UserRuntimeError("Expression "+ae.reproduceExpression()+" evaluated to "+val+" and cannot be transformed to a date.");
	}

	public void verify(Scope s) throws IntolerableException {
		ae.verify(s);
	}

	public String reproduceExpression() {
		return "datetimefrommillis("+ae.reproduceExpression()+")";
	}

}
