package org.eclipse.om2m.openhab.service;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import obix.*;
import obix.io.ObixEncoder;
 



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.service.SclService;
 
 
public class IpuMonitor {
	 /** Logger */
    private static Log LOGGER = LogFactory.getLog(IpuMonitor.class);
    /** Sclbase id */
    public final static String SCLID = System.getProperty("org.eclipse.om2m.sclBaseId","");
    /** Admin requesting entity */
    static String REQENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","");
    /** Generic create method name */
    public final static String METHOD_CREATE = "CREATE";
    /** Generic execute method name */
    public final static String METHOD_EXECUTE = "EXECUTE";
    /** State container id */
    public final static String DATA = "DATA";
    /** Descriptor container id */
    public final static String DESC = "DESCRIPTOR";
    /** Discovered SCL service*/
    static SclService SCL;
    public final static String APOCPATH = "ipu";
    /**
     * Constructor
     * @param scl - discovered SCL
     */
 
    public IpuMonitor(SclService scl) {
        SCL=scl;
    }
 
    public void start (ArrayList<ItemRest>lista) throws XPathExpressionException {
 

			//createItemResources(lista.get(3).get_name(), APOCPATH);
    	for (int i=0;i<lista.size();i++) {
    		//System.out.println();
    		if(!lista.get(i).get_type().equals("GroupItem")){
    			createItemResources(lista.get(i).get_name(), APOCPATH);
    			}
		}
    	
    	// Create initial resources for the sensor
        //createLampResources("lamp_0", false, APOCPATH);
       // createSensorResources("sensor_0", APOCPATH);
 
}  

    public <T> void createItemResources(String aId,String aPoCPath) throws XPathExpressionException {
        String appId="item_"+aId;
    	System.out.println("SITUACIJA");
    	ItemService serv= new ItemService();
    	ItemRest irest = serv.GetStateOf(aId);
		T initValue= (T) irest.get_state();
		System.out.println(initValue);
    	// Create the Application resource
        ResponseConfirm response = SCL.doRequest(new RequestIndication(METHOD_CREATE,SCLID+
                    "/applications",REQENTITY,new Application(appId,aPoCPath)));
        // Create Application sub-resources only if application not yet created
        if(response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
            // Create DESCRIPTOR container sub-resource
            SCL.doRequest(new RequestIndication(METHOD_CREATE,SCLID+"/applications/"+appId+
                             "/containers",REQENTITY,new Container(DESC)));
            // Create STATE container sub-resource
            SCL.doRequest(new RequestIndication(METHOD_CREATE,SCLID+"/applications/"+appId+
                             "/containers",REQENTITY,new Container(DATA)));
 
            String content, targetID;
            // Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
            content = Item.getDescriptorRep(SCLID, appId, DATA, aPoCPath);
            targetID= SCLID+"/applications/"+appId+"/containers/"+DESC+"/contentInstances";
            SCL.doRequest(new RequestIndication(METHOD_CREATE,targetID,REQENTITY,
                              new ContentInstance(content.getBytes())));
 
            // Create initial contentInstance on the STATE container resource
            content = Item.getStateRep(appId, initValue);
            targetID = SCLID+"/applications/"+appId+"/containers/"+DATA+"/contentInstances";
            SCL.doRequest(new RequestIndication(METHOD_CREATE,targetID,REQENTITY,
                              new ContentInstance(content.getBytes())));
        }
        else if (response.getStatusCode().equals(StatusCode.STATUS_ALREADY_EXISTS)){
            String content, targetID;
            // Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
            content = Item.getDescriptorRep(SCLID, appId, DATA, aPoCPath);
            targetID= SCLID+"/applications/"+appId+"/containers/"+DESC+"/contentInstances";
            SCL.doRequest(new RequestIndication(METHOD_CREATE,targetID,REQENTITY,
                              new ContentInstance(content.getBytes())));
 
            // Create initial contentInstance on the STATE container resource
            content = Item.getStateRep(appId, initValue);
            targetID = SCLID+"/applications/"+appId+"/containers/"+DATA+"/contentInstances";
            SCL.doRequest(new RequestIndication(METHOD_CREATE,targetID,REQENTITY,
                              new ContentInstance(content.getBytes())));
        	
        }
    }


 
    public static <T> void createContentResource(String itemId, T value) {
        // Creates lampCI with new State
        String content = Item.getStateRep(itemId, value);
        String targetID = SCLID+"/applications/"+itemId+"/containers/"+DATA+"/contentInstances";
        SCL.doRequest(new RequestIndication(METHOD_CREATE,targetID,REQENTITY,new ContentInstance(content.getBytes())));
    }
 
    public static <T> void setItemState(final String appId, T value) {
       // T newState=null;
        //??????
       // Item n= new Item();
      //??????
        //T currentState =  Item.getState(appId);
       // T currentState =  (T) n.getState(appId);
       // String str = currentState.toString();
       // if(value.toString().equals(str)) {
         //   newState.toString().equals(currentState);
            createContentResource(appId, value);
        //} else {
           // newState = value;
            // Create the CI in the case when the newState is different to the Current Lamp State
            //if (newState != currentState) {
                //createContentResource(appId, newState);
            //}
        //}
        //Lamps.LAMPS_STATES.set(index, newState);
       // Item.setState(newState);
        //n.setState(newState);
    }
 
 
}