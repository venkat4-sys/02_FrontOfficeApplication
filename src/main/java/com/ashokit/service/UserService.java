package com.ashokit.service;

import com.ashokit.binding.Loginform;
import com.ashokit.binding.SignUpform;
import com.ashokit.binding.Unlockform;

public interface UserService {
	
	public boolean UserSignup(SignUpform user);
	     
	public boolean Unlock(Unlockform unlock);
	         
	public String login(Loginform login);
	
	
	public boolean forgot(String email);
	
	
	

}
