package se.modlab.generics.sstruct.strings;

import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.*;

public class StringLiteral implements StringEvaluable
{

	private String literal;
	//private String orig = null;

	public StringLiteral(String _literal) {
		literal = eliminateEscapes(_literal.substring(1, _literal.length()-1));
		//orig = _literal;
	}

	public String evaluate(Scope s)
	throws IntolerableException
	{
		//System.out.println("ccStringLiteral.evaluate "+orig+" -> "+literal);
		return literal;
	}

	public void verify(Scope s) throws IntolerableException {
	}

	public String reproduceExpression() {
		return "\""+literal+"\"";
	}
	
    public static String eliminateEscapes(String text)
    {
    	//System.out.println("Längd "+text.length());
    	StringBuffer sb = new StringBuffer();
    	int idx = 0;
    	//int l = 2*((int) text.length() / 2);
    	int l = text.length();
    	while(idx < l-1) {
    		//System.out.println("DEL: "+text.substring(idx, idx+2)+", tillstånd "+sb.toString());
    		if(text.substring(idx, idx+2).compareTo("\\\\") == 0) {
    			sb.append("\\");
    			idx += 2;
    		}
    		else if (text.substring(idx, idx+2).compareTo("\\t") == 0) {
    			sb.append("\t");
    			idx += 2;
    		}
    		else if (text.substring(idx, idx+2).compareTo("\\b") == 0) {
    			sb.append("\b");
    			idx += 2;
    		}
    		else if (text.substring(idx, idx+2).compareTo("\\r") == 0) {
    			sb.append("\r");
    			idx += 2;
    		}
    		else if (text.substring(idx, idx+2).compareTo("\\n") == 0) {
    			sb.append("\n");
    			idx += 2;
    		}
    		else {
    			sb.append(text.substring(idx, idx+1));
    			idx += 1;
    		}
    		//System.out.println("S: "+sb.toString()+", idx = "+idx);
    		//System.out.println("--");
    	}
    	return sb.toString()+((idx >= text.length()) ? "" : text.substring(text.length()-1, text.length()));
    }
    
    public static void main(String args[]) {
    	System.out.println("1 : <"+eliminateEscapes("\\\\\\\\aa\\bb\\tff\\cc\\\\dd\\\\\\\\\\\\\\\\\\\\\\eee")+">");
    	//System.out.println("2 : <"+parse("\\\\aa\\bb\\cc\\\\dd\\\\\\\\\\ee")+">");
    }


}