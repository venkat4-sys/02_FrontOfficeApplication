package com.ashokit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.DashBoardResponse;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.binding.Enquiryform;
import com.ashokit.entity.StudentDetailsEntity;
import com.ashokit.repo.StudentEnquiryRepo;
import com.ashokit.serviceimpl.EnquiryServiceimpl;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession session;
	
	

	@Autowired
	private EnquiryServiceimpl enquiries;
	
	@Autowired
	private StudentEnquiryRepo studentRepo;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();

		return "index";
	}

	@GetMapping("/dashboard")
	public String loadDashboard(Model model) {
		Integer userId = (Integer) session.getAttribute("staffId");
		DashBoardResponse dashBoardData = enquiries.getDashBoardData(userId);

		model.addAttribute("dashboarddata", dashBoardData);
		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {

		initform(model);

		return "add-enquiry";
	}

	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("formObj") Enquiryform form, Model model) {

		 String saveEnquiry = enquiries.saveEnquiry(form);

			model.addAttribute("succMsg", saveEnquiry);
			
			initform(model);

			

		


		return "add-enquiry";
	}

	private void initform(Model model) {
		
		List<String> enqiryStatus = enquiries.getEnqiryStatus();

		List<String> courses = enquiries.getCourses();

		Enquiryform formobj = new Enquiryform();

		model.addAttribute("formObj", formobj);

		model.addAttribute("enquirystatus", enqiryStatus);

		model.addAttribute("courses", courses);
	}

	@GetMapping("/enquires")
	public String viewEnquiry(@ModelAttribute("formObj") Enquiryform form, Model model) {

		initform(model);

		List<StudentDetailsEntity> studentDetails = enquiries.getStudentDetails();
		model.addAttribute("details", studentDetails);

		return "view-enquiries";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("enquiryId") Integer enquiryId, Model model) {

		initform(model);

		Optional<StudentDetailsEntity> editEnquiry = studentRepo.findById(enquiryId);

		if (editEnquiry.isPresent()) {

			StudentDetailsEntity studentEnquiryForm = editEnquiry.get();

			model.addAttribute("formObj", studentEnquiryForm);
			model.addAttribute("hidden", enquiryId);

		}

		return "add-enquiry";

	}
	

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String course,

			@RequestParam String enquiryStatus, @RequestParam String classMode, Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();

		criteria.setCourse(course);
		criteria.setClassMode(classMode);
		criteria.setEnquiryStatus(enquiryStatus);;

		System.out.println(criteria);

		Integer staffId = (Integer) session.getAttribute("staffId");

	List<StudentDetailsEntity> filteredEnquiries = enquiries.getFilteredEnquiries(staffId, criteria);
	

		model.addAttribute("details", filteredEnquiries);

		return "filter-enquiry-page";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
