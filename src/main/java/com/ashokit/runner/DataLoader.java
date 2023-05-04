package com.ashokit.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnquiryStatusEntity;
import com.ashokit.repo.CourseRepo;
import com.ashokit.repo.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnquiryStatusRepo statusRepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		CourseEntity c1=new CourseEntity();
		
		c1.setCourseName("Java");

		CourseEntity c2=new CourseEntity();
		
		c2.setCourseName("Python");

		CourseEntity c3=new CourseEntity();
		
		c3.setCourseName("Devops");
		
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		statusRepo.deleteAll();
		
		EnquiryStatusEntity s1=new EnquiryStatusEntity();
		
		s1.setStatusName("New");
		
EnquiryStatusEntity s2=new EnquiryStatusEntity();
		
		s2.setStatusName("Enrolled");
		
EnquiryStatusEntity s3=new EnquiryStatusEntity();
		
		s3.setStatusName("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1,s2,s3));
		
		
		
		
		
		
	}
	
	

}
