package com.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.StudentDetailsEntity;

public interface StudentEnquiryRepo extends JpaRepository<StudentDetailsEntity,Integer>{
	/*
	@Query("select count(*) from StudentEnqiryEntity")
	public List<StudentEnqiryEntity> totalEnquiries();
	

	@Query("select count(*) from StudentEnqiryEntity where status='enrolled'")
	public List<StudentEnqiryEntity> enrolledStudentCount();
	


	@Query("select count(*) from StudentEnqiryEntity where status='lost' ")
	public List<StudentEnqiryEntity> lostStudentCount();
	
	
	*/

}
