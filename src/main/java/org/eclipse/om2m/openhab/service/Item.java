package org.eclipse.om2m.openhab.service;

import obix.Bool;
import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
import obix.Uri;
import obix.io.ObixEncoder;

public class Item<T> {

	private T _state;
//private static String _type;
/** Lamp state */
//private static boolean stateBool = false;
//private static double stateDouble = 0.00;
//private static int stateInt = 0;
//private static String stateString = "";
//private static long stateLong = 0;
//private static float stateFloat = 0;
//private static ColorItem stateRGB = new ColorItem(0,0,0);


 /**
 * Returns an obix XML representation describing the current state.
 * @param appId - Application Id
 * @param value - current lamp state
 * @return Obix XML representation
 */

public static <T> String getStateRep(String appId, T initValue) {
	// oBIX
    Obj obj = new Obj();
    obj.add(new Str("type","ITEM"));
    obj.add(new Str("location","Kitchen"));
    obj.add(new Str("appId",appId));
    obj.add(new Str("state",initValue.toString()));
    return ObixEncoder.toString(obj);
}

public static String getDescriptorRep(String sclId, String appId, String stateCont, String aPoCPath) {
    // oBIX
    Obj obj = new Obj();
    obj.add(new Str("type", "ITEM"));
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

public T getState(String itemId) {
	return _state;
	//if (_type.equals("NumberItem")) {return  stateDouble;}
    //if (_type.equals("SwitchItem")) {return  stateString;}
    //if (_type.equals("ContactItem")) {return  stateString;}
    //if (_type.equals("ColorItem")) {Item.stateInt = ColorItem.parseColorItem(state.toString());}
    //if (_type.equals("DimmerItem")) {return  stateString;}
    //if (_type.equals("StringItem")) {return  stateString;}
}

/**
 * Sets lampState
 * @param <T>
 */
public void setState(T state) {
    _state=state;
	//if (_type.equals("NumberItem")) {Item.stateDouble = Double.parseDouble(state.toString());}
    //if (_type.equals("SwitchItem")) {Item.stateString = state.toString();}
    //if (_type.equals("ContactItem")) {Item.stateString = state.toString();}
    //if (_type.equals("ColorItem")) {Item.stateInt = ColorItem.parseColorItem(state.toString());}
    //if (_type.equals("DimmerItem")) {Item.stateString = state.toString();}
    //if (_type.equals("StringItem")) {Item.stateString= state.toString();} 
}
}
