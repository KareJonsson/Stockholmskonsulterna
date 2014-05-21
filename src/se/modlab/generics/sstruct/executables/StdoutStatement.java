package se.modlab.generics.sstruct.executables;

import se.modlab.generics.sstruct.comparisons.*;
import se.modlab.generics.sstruct.strings.*;
import se.modlab.generics.exceptions.*;

public class StdoutStatement extends ProgramStatement
{

  private StringEvaluable text;

  public StdoutStatement(StringEvaluable _text)
  {
    text = _text;
  }

  public void execute(Scope s)
    throws ReturnException,
           IntolerableException,
           BreakException,
           ContinueException
  {
    String s_text = text.evaluate(s);
    System.out.println(s_text);
  }

	public void verify(Scope s) throws IntolerableException {
	}
  
}
