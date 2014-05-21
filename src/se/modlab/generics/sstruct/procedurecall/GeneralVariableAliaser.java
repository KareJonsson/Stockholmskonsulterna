package se.modlab.generics.sstruct.procedurecall;

//import se.modlab.generics.exceptions.UserCompiletimeError;
import se.modlab.generics.exceptions.*;
import se.modlab.generics.sstruct.comparisons.Scope;
import se.modlab.generics.sstruct.comparisons.VariableLookup;
import se.modlab.generics.sstruct.values.sDouble;
import se.modlab.generics.sstruct.variables.DoubleVariable;
import se.modlab.generics.sstruct.variables.Variable;
import se.modlab.generics.sstruct.variables.VariableInstance;
import se.modlab.generics.sstruct.variables.VariableVector;
import se.modlab.generics.sstruct.variables.VariableVectorFromFile;

public class GeneralVariableAliaser extends UntypedArgumentPasser
{
  private VariableLookup oldname;

  public GeneralVariableAliaser(String _filename, int _line, int _column, VariableLookup _oldname) {
	  super(_filename, _line, _column);
	  oldname = _oldname;
  }

  public void passArgument(Scope from, Scope to, UntypedAliasing ua)
    throws IntolerableException
  {
    VariableInstance si = oldname.getInstance(from);
    if(si == null) {
      throw new UserCompiletimeError(
        "Variable "+oldname+" "+getPlaceString()+" not known in this scope.");
    }
    /*
    if(si instanceof variable) {
      throw new UserCompiletimeError(
        "Instance "+oldname+" "+getPlaceString()+" is of predefined type in contradiction to demand in\n"+
        "procedure declaration.");
    }*/
    boolean arrayInstance = isArrayInstance(si); 
    if(arrayInstance != ua.isArray()) {
		  throw new UserRuntimeError(
				  "Parameter of type "+si.getType().getTypesName()+((arrayInstance) ? "[]":"")+" is passed to variable of type "+ua.getTypename()+((ua.isArray()) ? "[]": "")+".\n"+
				  "It is required that both passing and recieving side is of array type or not.\n"+
				  "Parameter declared "+ua.getPlaceString()+", passed to "+getPlaceString()+".");
    }
    if(ua.isReferenced()) {
    	handleReferencedVariablePass(si, to, ua.getTypename(), ua.getVarname(), ua.getPlaceString());
    }
    else {
    	handleValuePass(si, to, ua.getTypename(), ua.getVarname(), ua.getPlaceString());
    }
/*
    if(si.getType().getTypesName().compareTo(ua.getTypename()) != 0) {
        throw new UserRuntimeError(
                "Parameter of type "+si.getType().getTypesName()+" is passed to variable of type "+ua.getTypename());
    }
    to.addComplexInstance(si, ua.getVarname());
 */
  }
  
  private boolean isArrayInstance(VariableInstance vi) {
	  if(vi instanceof VariableVector) {
		  return true;
	  }
	  if(vi instanceof VariableVectorFromFile) {
		  return true;
	  }
	  return false;
  }
  
  private void handleReferencedVariablePass(VariableInstance si, Scope to, String typename, String varname, String placeString) throws IntolerableException {
	  String varsType = null;
	  try {
		  varsType = si.getType().getTypesName();
	  }
	  catch(Exception e) {
		  throw new UserRuntimeError(
				  "Type of variable "+si.getName()+" passed to "+typename+" was not possible to verify.");
	  }
	  if(varsType.compareTo(typename) != 0) {
		  throw new UserRuntimeError(
				  "Parameter of type "+si.getType().getTypesName()+" is passed to variable of type "+typename+".\n"+
				  "Parameter declared "+placeString+", passed to "+getPlaceString()+".");
	  }
	  to.addComplexInstance(si, varname);
  }

  private void handleValuePass(VariableInstance si, Scope to, String typename, String varname, String placeString) throws IntolerableException {
	  if(varname.compareTo("double") == 0) {
		  DoubleVariable dv = new DoubleVariable(varname, to.getFactory("double"), filename, line, column);
		  if(si.getType().getTypesName().compareTo("long") == 0) {
			  if(si instanceof Variable) {
				  Variable v = (Variable) si;
				  dv.setInitialValue(new sDouble(v.getValue().getLong()));
				  to.addVariable(dv);
			  }
			  else {
				  throw new InternalError(
						  "Parameter of type "+si.getType().getTypesName()+" is passed to variable of type "+typename+".\n"+
						  "but programaticaly type is "+si.getClass().getName()+".\n"+
						  "Parameter declared "+placeString+", passed to "+getPlaceString()+".");
			  }
			  return;
		  }
		  if(si.getType().getTypesName().compareTo("double") == 0) {
			  if(si instanceof Variable) {
				  Variable v = (Variable) si;
				  dv.setInitialValue(v.getValue());
				  to.addVariable(dv);
			  }
			  else {
				  throw new InternalError(
						  "Parameter of type "+si.getType().getTypesName()+" is passed to variable of type "+typename+".\n"+
						  "but programaticaly type is "+si.getClass().getName()+".\n"+
						  "Parameter declared "+placeString+", passed to "+getPlaceString()+".");
			  }
			  return;
		  }
		  throw new UserRuntimeError(
				  "Parameter of type "+si.getType().getTypesName()+" is passed to variable of type "+typename+".\n"+
				  "Parameter declared "+placeString+", passed to "+getPlaceString()+".");
	  }
	  if(si.getType().getTypesName().compareTo(typename) != 0) {
		  throw new UserRuntimeError(
				  "Parameter of type "+si.getType().getTypesName()+" is passed to variable of type "+typename+".\n"+
				  "Parameter declared "+placeString+", passed to "+getPlaceString()+".");
	  }
	  to.addComplexInstance(si, varname);
  }
 
}