package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public class StringOperatorEquals extends StringOperator
{

	public StringOperatorEquals(String _filename, int _line, int _column)
	{
		super(_filename, _line, _column);
	}
	
  public boolean compare(StringEvaluable left,
                         StringEvaluable right,
                         Scope s)
    throws IntolerableException
  {
    String s_l = left.evaluate(s);
    String s_r = right.evaluate(s);
    if(s_l == null && s_r == null) {
    	return true;
    }
    if(s_l != null && s_r != null) {
    	return s_l.compareTo(s_r) == 0;
    }
    return false;
  }
  
	public String reproduceExpression() {
		return "==";
	}


}