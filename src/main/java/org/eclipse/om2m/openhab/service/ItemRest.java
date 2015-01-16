package org.eclipse.om2m.openhab.service;

public class ItemRest<T> {
		private String _type;
		private String _name;
		private T _state;
		private String _link;
	
		public ItemRest(){
			
		}
	
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public T get_state() {
		return _state;
	}
	public void set_state(T _state) {
		this._state = _state;
	}
	public String get_link() {
		return _link;
	}
	public void set_link(String _text) {
		this._link = _text;
	}

    //System.out.print("type: \"" + xp.evaluate("type/text()", accounts.item(i)) + "\", ");
   // System.out.print("type: \"" + xp.evaluate("name/text()", accounts.item(i)) + "\", ");
   // System.out.print("type: \"" + xp.evaluate("state/text()", accounts.item(i)) + "\", ");
   // System.out.print("type: \"" + xp.evaluate("link/text()", accounts.item(i)) + "\", ");

}
