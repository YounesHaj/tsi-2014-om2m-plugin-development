package org.eclipse.om2m.openhab.service;
import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
public class UrchinNamespaceContext implements NamespaceContext  {

	  public String getNamespaceURI(String prefix) {
		    if (prefix == null) throw new NullPointerException("Null prefix");
		    if (prefix.equals("tns")) return "https://urchin.com/api/urchin/v1/";
		    else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
		    return XMLConstants.NULL_NS_URI;
		  }
		  public String getPrefix(String namespaceURI) {
		    throw new UnsupportedOperationException();
		  }
		  public Iterator getPrefixes(String namespaceURI) {
		    throw new UnsupportedOperationException();
		  }
}
