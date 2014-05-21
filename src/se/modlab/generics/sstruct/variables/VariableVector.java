package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.*;

public class VariableVector extends VariableInstance
{

  protected VariableFactory vf = null;
  protected VariableInstance siv[];

  public VariableVector(String name, 
                        VariableType _vt,
                        String _filename, int _line, int _column,
                        VariableFactory _vf, 
                        int length)
    throws IntolerableException  {
    super(name, _vt, _filename, _line, _column);
    vf = _vf;
    initiateVector(length);
  }

  protected void initiateVector(int length)
    throws IntolerableException {
    siv = new VariableInstance[length];
    for(int i = 0 ; i < length ; i++) {
      siv[i] = vf.getInstance("["+i+"]", filename, line, column);
    }
  }
  
  public boolean isNull() {
	  return siv == null;
  }

  public int getLength() {
    return siv.length;
  }

  public VariableFactory getVariableFactory() {
    return vf;
  }

  public VariableInstance getVectorElement(int i)
    throws IntolerableException {
    if(i < 0) return null;
    if(i >= siv.length) return null;
    return siv[i];
  }

  public void setDefaultInitialValue()
    throws IntolerableException {
    for(int i = 0 ; i < siv.length ; i++) {
      siv[i].setDefaultInitialValue();
    }
  }

  public void setScopesName(String scopesName) {
    for(int i = 0 ; i < siv.length ; i++) {
      siv[i].setScopesName(scopesName);
    }
  }

  public void setValue(VariableInstance si)
    throws IntolerableException {
    if(si.getType() != vt) {
      throw new InternalError( 
        "Vector variable "+name+" of type "+
        vt.getTypesName()+" set to value of \n"+
        "type "+si.getType().getTypesName());
    }
    VariableVector siv_there = (VariableVector) si;
    for(int i = 0 ; i < siv.length ; i++) {
      siv[i].setValue(siv_there.getVectorElement(i));
    }
  }

  public void setInitialValue(VariableInstance val) 
    throws IntolerableException {
    setValue(val);
  }

/*
  public variableInstance copy()
    throws intolerableException
  {
    variableVector vv = new variableVector(name, vt, vf, siv.length);
    for(int i = 0 ; i < siv.length ; i++)
    {
      vv.siv[i] = siv[i].copy();
    }
    return vv;
  }
 */

  public String toString()
  {
    return "Name "+name+
           ", vector length = "+siv.length+
           ", type "+vf.getTypesName();
  }

public boolean valueEquals(VariableInstance sv) throws IntolerableException {
	if(this == sv) {
		return true;
	}
	if(!(sv instanceof VariableVector)) {
		return false;
	}
	VariableVector other = (VariableVector) sv;
	if(vf != other.vf) {
		return false;
	}
	if(siv.length != other.siv.length) {
		return false;
	}
	for(int i = 0 ; i < siv.length ; i++) {
		if(!siv[i].valueEquals(other.siv[i])) {
			return false;
		}
	}
	return true;
}

}
