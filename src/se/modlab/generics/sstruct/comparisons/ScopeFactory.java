package se.modlab.generics.sstruct.comparisons;

public interface ScopeFactory
{
  public Scope getInstance(Scope s);
  public Scope getInstance(String name);
  public Scope getInstance(Scope subjacent, String name);
}
