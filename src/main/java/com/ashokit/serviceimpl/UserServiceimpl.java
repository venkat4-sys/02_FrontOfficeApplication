package com.ashokit.serviceimpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.Loginform;
import com.ashokit.binding.SignUpform;
import com.ashokit.binding.Unlockform;
import com.ashokit.entity.UserDetailsEntity;
import com.ashokit.repo.UserDetailsRepo;
import com.ashokit.service.UserService;
import com.ashokit.utility.EmailUtils;
import com.ashokit.utility.Passwordutils;

@Service
public class UserServiceimpl implements UserService {
	
	
	@Autowired
	public UserDetailsRepo repo;
	@Autowired
	public Passwordutils pwd;
	
	@Autowired
	public EmailUtils email;
	
	@Autowired
	private HttpSession session;
	
	

	public boolean UserSignup(SignUpform user) {
		
	  UserDetailsEntity findBystaffEmail = repo.findBystaffEmail(user.getStaffEmail());
		
		if(findBystaffEmail!=null) {
			return false;
			
		}
		
		UserDetailsEntity entity = new UserDetailsEntity();
		
		BeanUtils.copyProperties(user,entity);
		
		 String generatePassword = pwd.generatePassword();
		 
		entity.setNewPwd(generatePassword);
		entity.setAccountStatus("Locked");
		
		
	    repo.save(entity);
	    
	    
	     //String generatePassword = pwd.generatePassword();
	    String staffEmail = entity.getStaffEmail();
	    
	    
	    
	  String to=staffEmail;
	     
	   
	     
	     String subject="Office Application";
	     
	     String body = "<h1> Please click on below link to unlock your account</h1>"
					+ "<a href=\"http://localhost:8080/unlock?staffEmail="+to+"\">Unlock your account</a>"
					+"<p>Your temporary password is:<strong>"+ generatePassword +"</string></p>";
	     
	     
	     email.sendEmail(subject, body, to);
	     
	    
	    
		return true;
	}

	public boolean Unlock(Unlockform unlock) {

		UserDetailsEntity entity = repo.findBystaffEmail(unlock.getStaffEmail());
		
		if(unlock.getTempPwd().equals(entity.getNewPwd())) {
			
			entity.setAccountStatus("unlock");
			
			entity.setNewPwd(unlock.getNewPwd());
			
			repo.save(entity);
			
			return true;
			
	 }
		
		
		return false;
			
}

	public String login(Loginform login) {
		
		UserDetailsEntity entity = repo.findBystaffEmailAndNewPwd(login.getStaffEmail(), login.getNewPwd());
		
		
		if(entity == null){
			
			return "Invalid Credentials";
		}
		if(entity.getAccountStatus().equals("Locked")) {
			
			return "your Account is Locked";
			
		}
		
		//create a session 
		session.setAttribute("staffId",entity.getStaffId());
		
		
		
		
		
		
		
		
		return "Success";
	}

	public boolean forgot(String emails) {
		
		UserDetailsEntity entity = repo.findBystaffEmail(emails);
		
		if(entity==null) {
			return false;
			
		}
		
		String subject="Recover Password";
		String body="your pwd:"+entity.getNewPwd();
		
		email.sendEmail(subject, body, emails);
		
	
	return true;
	}

}
