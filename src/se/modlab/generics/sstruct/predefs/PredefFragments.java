package se.modlab.generics.sstruct.predefs;

import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.sstruct.values.*;
import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.exceptions.*;

public class PredefFragments implements ArithmeticEvaluable {

  private StringEvaluable cse;

  public PredefFragments(StringEvaluable _cse) {
    cse = _cse;
  }

  public sValue evaluate(Scope s) throws IntolerableException {
    String line = cse.evaluate(s);
    if(line == null) return new sLong(-1);
    String piece[] = line.trim().split("[' '|'\t']+");
    return new sLong(piece.length);
  }

	public void verify(Scope s) throws IntolerableException {
		cse.verify(s);
	}

	  public String reproduceExpression() {
		  return "fragments("+cse.reproduceExpression()+")";
	  }


}