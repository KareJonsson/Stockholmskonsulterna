package se.modlab.generics.sstruct.strings;

import java.text.SimpleDateFormat;
import java.util.Date;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.variables.*;

public class GeneralizedStringEvaluable implements StringEvaluable
{

	private VariableLookup vl;

	public GeneralizedStringEvaluable(VariableLookup _vl)
	{
		vl = _vl;
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		Variable var = vl.getVariable(s);
		if(var instanceof StringVariable)
		{
			StringVariable svar = (StringVariable) var;
			sString val = (sString) svar.getValue();
			return val.getString();
		}
		if(var instanceof BooleanVariable)
		{
			BooleanVariable bvar = (BooleanVariable) var;
			sBoolean sb = (sBoolean) bvar.getValue();
			boolean out = sb.getBoolean().booleanValue();
			if(out) return "true";
			return "false";
		}
		if(var instanceof LongVariable)
		{
			LongVariable lvar = (LongVariable) var;
			sLong sl = (sLong) lvar.getValue();
			return sl.getLong().toString();
		}
		if(var instanceof DoubleVariable)
		{
			DoubleVariable dvar = (DoubleVariable) var;
			sDouble sd = (sDouble) dvar.getValue();
			return sd.getDouble().toString();
		}
		if(var instanceof DateVariable)
		{
			DateVariable dvar = (DateVariable) var;
			sDate sd = (sDate) dvar.getValue();
			return getDatePresentable(sd.getDate());
		}
		if(var == null)
		{
			throw new UserRuntimeError(
					"The reference "+vl+" evalutes to null");
		}
		throw new InternalProgrammingError(
				"Class GeneralizedStringEvaluable is not \n"+
				"prepared for an instance of class \n"+var.getClass().getName());
	}
	
	public static String getDatePresentable(Date d) {
		//return Calendar.getInstance().
		SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyyMMdd");
		return formatNowYear.format(d);
	}
	
	public static void main(String args[]) {
		Date d = new Date();
		System.out.println("Date "+d+" -> "+getDatePresentable(d));
	}

	public void verify(Scope s) throws IntolerableException {
		Variable var = vl.getVariable(s);
		if(var instanceof StringVariable)
		{
			return;
		}
		if(var instanceof BooleanVariable)
		{
			return;
		}
		if(var instanceof LongVariable)
		{
			return;
		}
		if(var instanceof DoubleVariable)
		{
			return;
		}
		if(var == null)
		{
			throw new UserCompiletimeError(
					"The reference "+vl+" evalutes to null");
		}
		throw new InternalError(
				"Class GeneralizedStringEvaluable is not \n"+
				"prepared for an instance of class \n"+var.getClass().getName());
	}
	
	public String reproduceExpression() {
		return vl.reproduceExpression();
	}


}