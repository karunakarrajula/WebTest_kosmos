package com.pearson.testbed.core;

public class BrowserInfo {

	public boolean isBrowserChrome() {
		return TestBedConfig.getTestBedName().equalsIgnoreCase("Chrome");

	}

	public boolean isBrowserFireFox() {
		return TestBedConfig.getTestBedName().equalsIgnoreCase("Firefox");

	}

	public boolean isBrowserEdge() {
		return TestBedConfig.getTestBedName().equalsIgnoreCase("Edge");

	}
}
