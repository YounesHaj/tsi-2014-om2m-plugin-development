package org.eclipse.om2m.openhab.service;
import obix.*;
import obix.io.ObixEncoder;
 
public class Lamp {
	 /** Lamp state */
    private static boolean state = false;
 
 
	 /**
     * Returns an obix XML representation describing the current state.
     * @param appId - Application Id
     * @param value - current lamp state
     * @return Obix XML representation
     */
 
	public static String getStateRep(String appId, boolean initValue) {
		// oBIX
        Obj obj = new Obj();
        obj.add(new Str("type","LAMP"));
        obj.add(new Str("location","Kitchen"));
        obj.add(new Str("appId",appId));
        obj.add(new Bool("state",initValue));
        return ObixEncoder.toString(obj);
	}
 
	public static String getDescriptorRep(String sclId, String appId, String stateCont, String aPoCPath) {
        // oBIX
        Obj obj = new Obj();
        obj.add(new Str("type", "LAMP"));
        obj.add(new Str("location","Home"));
        obj.add(new Str("appId",appId));
        // OP GetState from SCL DataBase
        Op opState = new Op();
        opState.setName("getState");
        opState.setHref(new Uri(sclId+"/"+"applications/"+appId+"/containers/"+stateCont+
                  "/contentInstances/latest/content"));
        opState.setIs(new Contract("retrieve"));
        opState.setIn(new Contract("obix:Nil"));
        opState.setOut(new Contract("obix:Nil"));
        obj.add(opState);
        // OP GetState from SCL IPU
        Op opStateDirect = new Op();
        opStateDirect.setName("getState(Direct)");
        opStateDirect.setHref(new Uri(sclId+"/"+"applications/"+appId+"/"+aPoCPath));
        opStateDirect.setIs(new Contract("retrieve"));
        opStateDirect.setIn(new Contract("obix:Nil"));
        opStateDirect.setOut(new Contract("obix:Nil"));
        obj.add(opStateDirect);
 
        return ObixEncoder.toString(obj);
    }
 
	 /**
     * Gets lampState
     * @return lampState
     */
    public static boolean getState(String lampId) {
        return state;
    }
 
    /**
     * Sets lampState
     */
    public static void setState(boolean state) {
        Lamp.state = state;
    }
 
}