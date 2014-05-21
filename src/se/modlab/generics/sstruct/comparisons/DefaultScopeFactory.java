package se.modlab.generics.sstruct.comparisons;

public class DefaultScopeFactory implements ScopeFactory
{

  public Scope getInstance(Scope s)
  {
    return new Scope(s);
  }

  public Scope getInstance(String name)
  {
    return new Scope(name);
  }

  public Scope getInstance(Scope subjacent, String name)
  {
    return new Scope(subjacent, name);
  }

}
