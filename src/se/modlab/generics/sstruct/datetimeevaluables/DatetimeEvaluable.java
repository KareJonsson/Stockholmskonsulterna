package se.modlab.generics.sstruct.datetimeevaluables;

import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.values.sDate;

public interface DatetimeEvaluable {

		  public sDate evaluate(Scope s)
		    throws IntolerableException;

		  public void verify(Scope s)
		    throws IntolerableException;

		  public String reproduceExpression();

}
