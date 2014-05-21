package se.modlab.generics.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LineFileReader {
	
	private Vector<String> lines = new Vector<String>();
	
	public LineFileReader(File f) throws FileNotFoundException {
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
		catch(IOException ioe) {
			
		}
		try {
			br.close();
		}
		catch(IOException ioe) {
		}
	}
	
	public String[] getLines() {
		String[] out = new String[lines.size()];
		for(int i = 0 ; i < lines.size() ; i++) {
			out[i] = lines.elementAt(i);
		}
		return out;
	}

}
