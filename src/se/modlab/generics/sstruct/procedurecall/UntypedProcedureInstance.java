package se.modlab.generics.sstruct.procedurecall;

import se.modlab.generics.sstruct.executables.ProgramBlock;
import se.modlab.generics.sstruct.variables.Procedure;

public class UntypedProcedureInstance implements Procedure {

	private String name;
	private UntypedAliasing ases[];
	private ProgramBlock p;
	private String filename;
	private int line;
	private int column;

	public UntypedProcedureInstance(
			String _name,
			UntypedAliasing _ases[],
			ProgramBlock _p,
			String _filename,
			int _line,
			int _column) {
		name = _name;
		ases = _ases;
		p = _p;
		filename = _filename;
		line = _line;
		column = _column;
	}
	
	public String getName() {
		return name;
	}

	public String getPlace() {
		return "file "+filename+", line "+line+", column "+column;
	}
	
	public int getNoAliases() {
		return ases.length;
	}

	public UntypedAliasing getAlias(int i) {
		return ases[i];
	}

	public ProgramBlock getProgram() {
		return p;
	}

}
