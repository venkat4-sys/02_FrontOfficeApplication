package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.EnquiryStatusEntity;

public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatusEntity,Integer> {
   /*
	@Query("select status from EnquiryStatusEntity")
	public List<EnquiryStatusEntity> getStatusNames();
	*/
}
