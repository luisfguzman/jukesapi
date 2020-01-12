package com.jukebox.api.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Juke {
	private String id;
	private String model;
	private List<Component> components;

	public Juke() {

	}

	public Juke(String id, String model) {
		super();
		this.id = id;
		this.model = model;
		this.components = new ArrayList<Component>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Component> getComponents() {
		return components;
	}
	
	public Set<String> retComponentsAsSet() {
		Set<String> result = new HashSet<String>();
		for(Component c: components)
			result.add(c.getName());
		return result;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ID: " + this.id + " | Model: " + this.model + " | Components: "
				+ Arrays.toString(this.components.toArray())

		;
	}

}
