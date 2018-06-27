package com.freegym.web.config.action;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.config.Setting;

public class ConfigBasicAction extends BaseBasicAction {

	private static final long serialVersionUID = 4091581716232222072L;

	protected Setting setting;

	protected Integer autoGen;

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Integer getAutoGen() {
		return autoGen;
	}

	public void setAutoGen(Integer autoGen) {
		this.autoGen = autoGen;
	}

}
