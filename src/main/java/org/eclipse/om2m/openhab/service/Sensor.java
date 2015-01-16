package org.eclipse.om2m.openhab.service;

import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
import obix.Uri;
import obix.io.ObixEncoder;
 
public class Sensor {
 
	/** sensor state */
    private static int state;
 
	/**
     * Returns an obix XML representation describing the lamp.
     * @param sclId - SclBase id
     * @param appId - Application Id
     * @param stateCont - the STATE container id
     * @return Obix XML representation
     */
    public static String getDescriptorRep(String sclId, String appId, String stateCont, String aPoCPath) {
 
        // oBIX
        Obj obj = new Obj();
        obj.add(new Str("type", "Sensor"));
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
     * Returns an obix XML representation describing the current state.
     * @param appId - Application Id
     * @param value - current sensor state
     * @return Obix XML representation
     */
 
    public static String getStateRep(String appId, int value) {
        // oBIX
        Obj obj = new Obj();
        obj.add(new Str("type","sensor"));
        obj.add(new Str("location","Home"));
        obj.add(new Str("appId",appId));
        String strValue = Integer.toString(value);
        obj.add(new Str("state",strValue));
        return ObixEncoder.toString(obj);
 
    }
 
    /**
     * Gets SensorState
     * @return SensorState
     */
    public static void setState(int state) {
    	int Value = 10 + (int)(Math.random()*100);
        Sensor.state = Value;
    }
 
}