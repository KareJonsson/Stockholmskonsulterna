package se.modlab.generics.sstruct.datetimeevaluables;

import java.text.SimpleDateFormat;
import java.util.Date;

import se.modlab.generics.exceptions.UserRuntimeError;
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.strings.StringEvaluable;
import se.modlab.generics.sstruct.values.sDate;

public class DatetimeFromString implements DatetimeEvaluable {
	
	private StringEvaluable se;
	
	public DatetimeFromString(StringEvaluable _se) {
		se = _se;
	}

	public sDate evaluate(Scope s) throws IntolerableException {
		String val = se.evaluate(s);
		return new sDate(getDate(val));
	}

	public void verify(Scope s) throws IntolerableException {
		se.verify(s);
	}

	public String reproduceExpression() {
		return "datetimefromstring("+se.reproduceExpression()+")";
	}
	
	public final static String formats[] = {
		"yyyy/MM/dd HH:mm:ss",
		"yyyy/MM/dd HH:mm",
		"yyyy/MM/dd HHmm",
		"yyyy/MM/dd HH",
		"yyyy/MM/dd",
		"yyyy-MM-dd HH:mm:ss", 
		"yyyy-MM-dd HH:mm",
		"yyyy-MM-dd HHmm",
		"yyyy-MM-dd HH",
		"yyyy-MM-dd",
		"yyyyMMdd HH:mm:ss",
		"yyyyMMdd HH:mm",
		"yyyyMMdd HHmm",
		"yyyyMMdd HH",
		"yyyyMMdd", 
	};

	
	private Date getDate(String s) throws UserRuntimeError {
	SimpleDateFormat sdf = null;
	for(int i = 0 ; i < formats.length ; i++) {
		try {
			String format = formats[i];
			sdf = new SimpleDateFormat(format);
			return sdf.parse(s);
		}
		catch(Exception e) {
			
		}
	}
	UserRuntimeError e = new UserRuntimeError("Unable to make a date from '"+s+"'");
	throw e;
}


}
