package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;

import java.util.*;

public class StringContainer implements StringEvaluable
{

	private Vector<StringEvaluable> evaluables = new Vector<StringEvaluable>();

	public StringContainer()
	{
	}

	public void addStringEvaluable(StringEvaluable se)
	{
		evaluables.addElement(se);
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < evaluables.size() ; i++)
		{
			StringEvaluable se = (StringEvaluable) evaluables.elementAt(i);
			sb = sb.append(se.evaluate(s));
		}
		return sb.toString();
	}

	public void verify(Scope s) throws IntolerableException {
		for(int i = 0 ; i < evaluables.size() ; i++)
		{
			StringEvaluable se = (StringEvaluable) evaluables.elementAt(i);
			se.verify(s);
		}
	}
	
	public String reproduceExpression() {
		if(evaluables.size() == 0) {
			return "()";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("("+evaluables.elementAt(0));
		  //return ae.reproduceExpression(); 
		for(int i = 1 ; i < evaluables.size() ; i++) {
			sb.append(", "+evaluables.elementAt(i));
		}
		return sb.toString()+")";
	}
    
     
}
