package com.gcu.business;

import com.gcu.model.LoginModel;

public interface SecurityBusinessInterface {
	boolean authenticate(LoginModel credentials);
}