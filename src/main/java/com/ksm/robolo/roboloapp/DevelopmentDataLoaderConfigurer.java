package com.ksm.robolo.roboloapp;

import org.springframework.stereotype.Component;

@Component
public class DevelopmentDataLoaderConfigurer {

	private boolean addTestUser = false;

	public boolean isAddTestUser() {
		return addTestUser;
	}

	public void setAddTestUser(boolean addTestUser) {
		this.addTestUser = addTestUser;
	}
}
