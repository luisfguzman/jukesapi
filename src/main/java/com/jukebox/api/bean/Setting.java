package com.jukebox.api.bean;

import java.util.ArrayList;
import java.util.List;

public class Setting {
	private String id;
	private List<String> requires;

	public Setting() {
	}

	public Setting(String id) {
		super();
		this.id = id;
		this.requires = new ArrayList<String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getRequires() {
		return requires;
	}

	public void setRequires(List<String> requires) {
		this.requires = requires;
	}

}
