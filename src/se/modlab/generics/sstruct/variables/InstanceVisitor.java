package se.modlab.generics.sstruct.variables;

import java.lang.reflect.*;

import se.modlab.generics.exceptions.*;

public class InstanceVisitor {

	public void visit(Object o) throws IntolerableException {
		try {
			Class<?> c = getClass();
			Method m = c.getMethod("visit", 
					new Class[] { o.getClass() });
			m.invoke(this, new Object[] { o });
		} 
		catch(Exception e) {
			System.out.println("se.modlab.generics.sstruct.variables.InstanceVisitor: no method for class "+o.getClass().getName()+" in visitor class "+this.getClass().getName());
			e = getException(e);
			if(e instanceof IntolerableException) throw (IntolerableException)e;
			throw new InternalProgrammingError(getMessage(e), e);
		}
	}

	private Exception getException(Throwable e) {
		if(!(e instanceof InvocationTargetException)) {  
			if(!(e instanceof Exception)) {
				return new InternalProgrammingError(
						"Non Exception throwable in InstanceVisitor.\n"+
								"Actual class "+e.getClass().getName()+", message "+e.getMessage(), e);
				//System.out.println("Actual class "+e.getClass().getName()+", message "+e.getMessage());
			}
			return (Exception)e;
		}
		while(e instanceof InvocationTargetException) {
			e = ((InvocationTargetException) e).getTargetException();
		}
		if(!(e instanceof Exception)) {
			return new InternalProgrammingError(
					"Non Exception throwable in instanceVisitor.\n"+
							"Actual class "+e.getClass().getName()+", message "+e.getMessage(), e);
			//System.out.println("Actual class "+e.getClass().getName()+", message "+e.getMessage());
		}
		return (Exception)e;
	}

	private String getMessage(Throwable e) {
		Exception _e = getException(e);
		//_e.printStackTrace();
		return _e.toString();
	}

}