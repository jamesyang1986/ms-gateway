package org.xdemo.example.springmvc.bean;

import java.util.List;

/**
 * @author <a href="http://www.xdemo.org">xdemo.org</a>
 */
public class User {
	
	private String userName;
	
	private String address;
	
	private List<Phone> phones;
	
	private Phone[] phones2;
	
	
	public Phone[] getPhones2() {
		return phones2;
	}

	public void setPhones2(Phone[] phones2) {
		this.phones2 = phones2;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
