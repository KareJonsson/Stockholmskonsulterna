package se.modlab.generics.control;

import java.util.*;

public class LicenseInformationNode 
{
   private Vector<LicenseInformationNode> children = new Vector<LicenseInformationNode>();
   private String name;
   private String contents;
   private LicenseInformationNode parent;

   public void setParent(LicenseInformationNode n) {
      parent = n;
   }

   public LicenseInformationNode(String name)
   {
      this.name = name;
   }

   public void addChild(LicenseInformationNode n)
   {
      children.addElement(n);
      n.setParent(this);
   }

   public int getChildCount()
   {
      return children.size();
   }

   public int getChildCount(String name)
   {
      int namedChildren = 0;
      for(int i = 0 ; i < children.size() ; i++)
      {
         LicenseInformationNode n = children.elementAt(i);
         if(n.getName().compareTo(name) == 0)
         {
            namedChildren++;
         }
      }
      return namedChildren;
   }

   public LicenseInformationNode getChildAt(int child)
   {
      if(child >= children.size()) return null;
      return (LicenseInformationNode) children.elementAt(child);
   }

   public LicenseInformationNode getChildAt(String name, int child)
   {
      if(child >= children.size()) return null;
      int _child = 0;
      for(int i = 0 ; i < children.size() ; i++)
      {
         LicenseInformationNode n = children.elementAt(i);
         if(n.getName().compareTo(name) == 0)
         {
            if(_child++ == child) return n;
         }
      }
      return null;
   }

   public LicenseInformationNode getChildFromName(String name)
   {
      for(int i = 0 ; i < children.size() ; i++)
      {
         LicenseInformationNode child = children.elementAt(i);
         String childsName = child.getName();
         if(name.compareTo(childsName) == 0)
            return child;
      }
      return null;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getContents()
   {
      return contents;
   }

   public void setContents(String contents)
   {
      this.contents = contents;
   }

   public boolean hasChildren()
   {
      return children.size() == 0;
   }

   public Enumeration<LicenseInformationNode> children() {
      return children.elements();
   }

   public boolean getAllowsChildren() {
      return true;
   }

   public int getIndex(LicenseInformationNode n) {
      return children.indexOf(n);
   }

   public LicenseInformationNode getParent() {
      return parent;
   }

   public boolean isLeaf() {
      return (children.size() == 0);
   }

   public String toString() {
      if(children.size() != 0) return name;
      if((children.size() == 0) &&
         ((contents == null) ||
          (contents.trim().length() == 0))) return name+" / No value";
      return name+" / "+contents;
   }

}


