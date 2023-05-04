package com.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.Loginform;
import com.ashokit.binding.SignUpform;
import com.ashokit.binding.Unlockform;
import com.ashokit.service.UserService;

@Controller
public class UserController {

	@Autowired
	public UserService service;

	@GetMapping("/login")
	public String login(Model model) {

		Loginform form = new Loginform();
		model.addAttribute("login", form);

		return "login";
	}

	@PostMapping("/loginlogic")
	public String loginlogic(@ModelAttribute("login") Loginform form, Model model) {

		System.out.println(form);

		String status = service.login(form);

		if (status.contains("Success")) {
			
			return "redirect:/dashboard";

		}

		model.addAttribute("errMsg", status);

		return "login";
	}

	@GetMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("sign", new SignUpform());

		return "signup";
	}

	@PostMapping("/signuplogic")
	public String signupOperation(@ModelAttribute("sign") SignUpform signup, Model model) {

		System.out.println(signup);

		boolean userSignup = service.UserSignup(signup);

		if (userSignup) {
			model.addAttribute("sucess", "registration succesfull and Email Sent");

		} else {
			model.addAttribute("denied", "you must provide unique email");
		}

		return "signup";

	}

	@GetMapping("/unlock")
	public String unlock(@RequestParam String staffEmail, Model model) {

		Unlockform unlockform = new Unlockform();
		unlockform.setStaffEmail(staffEmail);

		model.addAttribute("unlocks", unlockform);

		return "unlock";
	}

	@PostMapping("/unlocklogic")
	public String unlockoperation(@ModelAttribute("unlocks") Unlockform unlock, Model model) {

		System.out.println(unlock.getStaffEmail());

		if (unlock.getConfirmPwd().equals(unlock.getNewPwd())) {

			boolean unlocks = service.Unlock(unlock);
			if (unlocks) {
				model.addAttribute("key1", "your account is unlocked..");
			} else {
				model.addAttribute("key2", "please check your credentials");
			}

		} else {
			model.addAttribute("errormsg", "Invalid credentials");
		}

		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgot(Model model) {
		return "forgotPwd";
	}
	
	@PostMapping("/forgotlogic")
	public String forgotlogic(@RequestParam("email") String email,Model model) {
		
		System.out.println(email);
		
		boolean status= service.forgot(email);
		
		if(status) {
			model.addAttribute("succmsg", "password sent to your Email");
			
		}
		else {
			model.addAttribute("errmsg", "Email not found");
		}
		
		
		return "forgotPwd";
	}

}
