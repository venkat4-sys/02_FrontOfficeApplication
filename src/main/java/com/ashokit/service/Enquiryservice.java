package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.DashBoardResponse;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.binding.Enquiryform;
import com.ashokit.entity.StudentDetailsEntity;


public interface Enquiryservice {
	
	public DashBoardResponse getDashBoardData(Integer staffId);
	
	public List<String> getCourses();
	
	public List<String> getEnqiryStatus();
	
    public  String saveEnquiry(Enquiryform form);
    
    public List<StudentDetailsEntity> getStudentDetails();
    
    public List<StudentDetailsEntity> getFilteredEnquiries(Integer staffId, EnquirySearchCriteria searchCriteria);
    
    
    
    
    
}
