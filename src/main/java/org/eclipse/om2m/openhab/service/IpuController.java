package org.eclipse.om2m.openhab.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;
 
 
 
public class IpuController implements IpuService {
	public final static String APOCPATH = "ipu";
	private static Log LOGGER = LogFactory.getLog(IpuController.class);
 
 
 
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		String[] info = requestIndication.getTargetID().split("/");
	        String lampId = info[info.length-3];
	        String type = lampId.split("_")[0];
	        String itemIdService=lampId.split("_")[1];
	        String value = info[info.length-1];
	        ItemService serv= new ItemService();
	        try {

	            if(type.equals("item")) {
	            	boolean result=serv.SetStateOf(itemIdService,value);
	            	System.out.println("rezultat"+result);
	            	if (result) {
		            	IpuMonitor.setItemState(lampId, value);
		            	
		                javax.swing.JOptionPane.showMessageDialog(null, "Item "+lampId+", state changed to "+value);
		                }
		            else{javax.swing.JOptionPane.showMessageDialog(null, "Item "+lampId+", not changed state");}
	                return new ResponseConfirm(StatusCode.STATUS_OK);
	            } else{
	                return new ResponseConfirm(StatusCode.STATUS_NOT_FOUND,type+" Not found");
	            }
	        } catch (Exception e) {
	            LOGGER.error("IPU Lamp Error",e);
	            return new ResponseConfirm(StatusCode.STATUS_NOT_IMPLEMENTED,"IPU Lamp Error");
	        }
	}
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
		 String[] info = requestIndication.getTargetID().split("/");
	        String appId = info[info.length-2];
	        String type= appId.split("_")[0];
	        int contvalue;
	        boolean value;
	        String content=null; 
	        try {
	        	if (type.equals("sensor")){
	            // Get a random Value for the sensor
	        	contvalue = 10 + (int)(Math.random()*100); 
	        	content = Sensor.getStateRep(appId, contvalue);
	        	}
	        	else if (type.equals("lamp")){
	        		 // Get the boolean Value for the lamp
	        		value = Lamp.getState(appId);
	        		content = Lamp.getStateRep(appId, value);
	        	}
	        	return new ResponseConfirm(StatusCode.STATUS_OK,content);	
	        } catch (Exception e) {
	            LOGGER.error("Hello sample Error",e);
	            return new ResponseConfirm(StatusCode.STATUS_NOT_IMPLEMENTED,"IPU Sample Error" );
	        }
	}
 
 
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,
                                         requestIndication.getMethod()+" Method not Implemented"));
	}
 
 
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,
                                       requestIndication.getMethod()+" Method not Implemented"));
	}
 
 
	public ResponseConfirm doCreate(RequestIndication requestIndication) {
		 return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,
                                      requestIndication.getMethod()+" Method not Implemented"));
	}
 
 
	public String getAPOCPath() {
		// TODO Auto-generated method stub
		return APOCPATH;
	}
 
}