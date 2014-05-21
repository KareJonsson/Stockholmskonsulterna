package se.modlab.generics.sstruct.variables;

import se.modlab.generics.exceptions.IntolerableException;


public class VariableVectorFromFileFactory implements VariableFactory
{
  private VariableFactory vf;
  private String filename;

  public VariableVectorFromFileFactory(VariableFactory vf, 
                                       String filename)
  {
    super();
    this.vf = vf;
    this.filename = filename;
  }

  public VariableInstance getInstance(String name, String _filename, int _line, int _column)
    throws IntolerableException
  {
    //H?r skall tabellen l?sas in. Det skall ocks? vara allt.
    //System.out.println("SimVariableVectorFromFileFactory - getInstance "+filename);
    return new VariableVectorFromFile(
                  name, 
                  this, 
                  vf, 
                  filename.substring(1, filename.length()-1),
                  _filename, _line, _column
);
  }

  public String getTypesName()
  {
    return "vector factory. File = "+filename+
           ", type "+vf.getTypesName();
  }


}