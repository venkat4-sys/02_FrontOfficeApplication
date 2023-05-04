package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity,Integer>{
	
	//@Query("select courseName from CourseEntity ")
	//public List<CourseEntity> getCourseName();

}
