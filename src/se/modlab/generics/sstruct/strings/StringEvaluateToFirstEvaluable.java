package se.modlab.generics.sstruct.strings;

import java.util.Vector;

import se.modlab.generics.exceptions.UserRuntimeError; 
import se.modlab.generics.exceptions.IntolerableException;
import se.modlab.generics.sstruct.comparisons.Scope;

public class StringEvaluateToFirstEvaluable implements StringEvaluable {

	// Fields
	private Vector<StringEvaluable> source;

	// Constructors
	public StringEvaluateToFirstEvaluable(Vector<StringEvaluable> _source) {
		source = _source;
	}

	// Methods
	public String evaluate(Scope s) throws IntolerableException {
		StringEvaluable expr = null;
		String out = null;
		//System.out.println("ccEvaluateToFirstEvaluable 1 expression "+reproduceExpression());
		for(int i = 0 ; i < source.size() ; i++) {
			try {
				expr = null;
				out = null;
				expr = source.elementAt(i);
				out = expr.evaluate(s);
				//System.out.println("ccEvaluateToFirstEvaluable loop evaluate("+expr.reproduceExpression()+") -> \""+out+"\"");
				return out;
			}
			catch(IntolerableException ie) {
				//System.out.println("ccEvaluateToFirstEvaluable catch 1 "+s.getAllVariableNamesAndValues());
				//System.out.println("ccEvaluateToFirstEvaluable catch evaluate "+ie+" :: evaluate("+expr.reproduceExpression()+") -> \""+out+"\"");
			}
			catch(Exception e) {
				//System.out.println("ccEvaluateToFirstEvaluable catch 1 "+s.getAllVariableNamesAndValues());
				//System.out.println("ccEvaluateToFirstEvaluable catch evaluate "+ie+" :: evaluate("+expr.reproduceExpression()+") -> \""+out+"\"");
			}
		}
		throw new UserRuntimeError("Unable to evaluate any of the expressions");
	}

	public void verify(Scope s) throws IntolerableException {
		for(int i = 0 ; i < source.size() ; i++) {
			source.elementAt(i).verify(s);
		}
	}
	
	public String reproduceExpression() {
		StringBuffer sb = new StringBuffer();
		sb.append("firstevaluable("+source.elementAt(0).reproduceExpression());//+source.reproduceExpression()+", "+tolength.reproduceExpression()+", "+completion.reproduceExpression()+")";
		for(int i = 1 ; i < source.size() ; i++) {
			sb.append(", "+source.elementAt(i).reproduceExpression());
		}
		return sb.toString()+")";
	}

}
