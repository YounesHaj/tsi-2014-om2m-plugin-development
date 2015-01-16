package org.eclipse.om2m.openhab.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class ItemService {

	public ItemService(){}
		
	public ArrayList<ItemRest> GiveMeItems() throws XPathExpressionException{
		ArrayList<ItemRest> lista= new ArrayList<ItemRest>();
		try {
		String localhostOutside="http://192.168.200.250/rest/items";
		
		//String localhostOutside= "http://80.65.65.76:20050/rest/items";
		String userpass = "root:mosquitto";
		
		URL url = new URL(localhostOutside);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/xml");
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
			BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		 	XPathFactory fact = XPathFactory.newInstance();
		    XPath xp = fact.newXPath();
		    InputSource is = new InputSource(br);
		    xp.setNamespaceContext(new UrchinNamespaceContext());
		    // retrieve list of items
		    NodeList accounts = (NodeList)xp.evaluate("/items/item", is, XPathConstants.NODESET);
		    
		    for(int i = 0; i < accounts.getLength(); i++){
		      ItemRest n= new ItemRest();
		      n.set_name(xp.evaluate("name/text()", accounts.item(i)));
		      n.set_type(xp.evaluate("type/text()", accounts.item(i)));
		      n.set_state(xp.evaluate("state/text()", accounts.item(i)));
		      n.set_link(xp.evaluate("link/text()", accounts.item(i)));
		      lista.add(n);
		    }
		    br.close();
		conn.disconnect();
	  } catch (MalformedURLException e) {e.printStackTrace();
	  } catch (IOException e) {e.printStackTrace();}

	return lista;
	};
	
	public ItemRest GetStateOf(String aId) throws XPathExpressionException{
		ItemRest n= new ItemRest();
		try {
		String localhostOutside="http://192.168.200.250/rest/items";
		//String localhostOutside= "http://80.65.65.76:20050/rest/items";
		String userpass = "root:mosquitto";
		
		URL url = new URL(localhostOutside+"/"+aId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/xml");
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		
		if (conn.getResponseCode() != 200) { 
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
			BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		 	XPathFactory fact = XPathFactory.newInstance();
		    XPath xp = fact.newXPath();
		    InputSource is = new InputSource(br);
		    xp.setNamespaceContext(new UrchinNamespaceContext());
		    // retrieve list of items
		    NodeList accounts = (NodeList)xp.evaluate("/item", is, XPathConstants.NODESET);
		      n.set_name(xp.evaluate("name/text()", accounts.item(0)));
		      n.set_type(xp.evaluate("type/text()", accounts.item(0)));
		      n.set_state(xp.evaluate("state/text()", accounts.item(0)));
		      n.set_link(xp.evaluate("link/text()", accounts.item(0)));
		    br.close();
		conn.disconnect();
	  } catch (MalformedURLException e) {e.printStackTrace();
	  } catch (IOException e) {e.printStackTrace();}

	return n;
	};
	
	public <T> Boolean SetStateOf(String aId, T value) throws XPathExpressionException{
		Boolean n = false;
		try {
		String localhostOutside="http://192.168.200.250/rest/items";
		//String localhostOutside= "http://80.65.65.76:20050/rest/items";
		String userpass = "root:mosquitto";
		
		URL url = new URL(localhostOutside+"/"+aId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/xml");
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.setRequestProperty("Content-type", "text/plain");
		String input = value.toString();
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		
		System.out.println(conn.getResponseCode());
		
		if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201 && conn.getResponseCode() != 204) {
			n=false;
			conn.disconnect();
			return n;
		}
		n=true;
		conn.disconnect();
	  } catch (MalformedURLException e) {e.printStackTrace();
	  } catch (IOException e) {e.printStackTrace();}

	return n;
	};
}
