package com.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "student_tbl")
public class StudentDetailsEntity {
	

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer enquiryId;

		private String studentName;
		
		private Long contactNumber;
		
		private String classMode;
		
		private String course;
		
		private String status;

		@CreationTimestamp
		private LocalDate createdDate;

		@UpdateTimestamp
		private LocalDate updatedDate;
		
		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "staffId")
		private UserDetailsEntity userDetails;

}
