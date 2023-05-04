package com.ashokit.binding;

import javax.persistence.Id;

import lombok.Data;


@Data
public class Enquiryform {
	
	@Id
	private Integer enquiryId;
	
	private String studentName;
	
	private long contactNumber;
	
	private String classMode;
	
	private String course;
	
	private String status;
	
	
	
	
	

}
