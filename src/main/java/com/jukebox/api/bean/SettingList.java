package com.jukebox.api.bean;

import java.util.ArrayList;
import java.util.List;

public class SettingList {
	private List<Setting> settings;
	
	public SettingList() {
		settings = new ArrayList<Setting>();
	}

	public List<Setting> getSettings() {
		return settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}
}
