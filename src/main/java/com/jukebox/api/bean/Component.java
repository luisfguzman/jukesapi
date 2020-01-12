package com.jukebox.api.bean;

public class Component {
	private String name;

	public Component() {
	}

	public Component(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name: " + this.name;
	}
}
