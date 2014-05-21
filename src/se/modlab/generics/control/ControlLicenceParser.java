package se.modlab.generics.control;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

public class ControlLicenceParser extends DefaultHandler
{

  private Stack<LicenseInformationNode> st = new Stack<LicenseInformationNode>();
  private LicenseInformationNode top;
  //private static boolean WRITE_CALLBACK = true;
  //private static boolean WRITE_STACK_TRACE = true;
  //private String error;

  public static LicenseInformationNode getParsedStructure(String xml)
  {
	  //System.out.println("ControlLicenseParser xml="+xml);
    // Use an instance of ourselves as the SAX event handler
    ControlLicenceParser handler = new ControlLicenceParser();
    // Use the default (non-validating) parser
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setValidating(true);
    try 
    {
      // Parse the input
      SAXParser saxParser = factory.newSAXParser();
      InputStream is = new ByteArrayInputStream(xml.getBytes() );
      saxParser.parse(is, handler);
      LicenseInformationNode n = handler.getTop();
      return n;
    } 
    catch (Throwable t) {
    	t.printStackTrace();
    }
    return null;
  }

  //===========================================================
  // SAX DocumentHandler methods
  //===========================================================

  public void startDocument()
  throws SAXException
  {
    //if(WRITE_CALLBACK) debugPrint("startDocument"); 
  }

  public void endDocument()
  throws SAXException
  {
    //if(WRITE_CALLBACK) debugPrint("endDocument"); 
  }

  public void startElement(String namespaceURI,
                           String lName, // local name
                           String qName, // qualified name
                           Attributes attrs)
  throws SAXException
  {
/*
    if(WRITE_CALLBACK) 
      debugPrint("startElement. Name: '"+qName+"'\n"+
                 "No of Attributes "+attrs.getLength());
 */
    LicenseInformationNode n = new LicenseInformationNode(qName.trim());
    if(st.empty()) top = n;
    else
    {
      LicenseInformationNode parent = st.peek();
      parent.addChild(n);
    }
    st.push(n);
  }

  public void endElement(String namespaceURI,
                         String sName, // simple name
                         String qName  // qualified name
                        )
  throws SAXException
  {
    //if(WRITE_CALLBACK) debugPrint("endElement. Name: '"+qName+"'");
    st.pop();
  }

  public void characters(char buf[], int offset, int len)
  throws SAXException
  {
    String s = new String(buf, offset, len);
    //if(WRITE_CALLBACK) debugPrint("CHARS: '"+s+"'");
    LicenseInformationNode parent = st.peek();
    parent.setContents(s);
    //node n = new node("CHARS");
    //n.setContents(s);
    //parent.addChild(n);
  }

/*
  public String getError()
  {
    return error;
  }
 */
  public LicenseInformationNode getTop()
  {
    return top;
  }
/*
  public static void debugPrint(String s)
  {
    System.out.println("XML parser: "+s);
  }
/*
  public void error (SAXParseException e)
  throws SAXParseException 
  {
    debugPrint("Exception line:"+e.getLineNumber()+
               " column:"+e.getColumnNumber());
    throw e;
  }
 */
}
