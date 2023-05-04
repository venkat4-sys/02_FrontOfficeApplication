package com.ashokit.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.DashBoardResponse;
import com.ashokit.binding.EnquirySearchCriteria;
import com.ashokit.binding.Enquiryform;
import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnquiryStatusEntity;
import com.ashokit.entity.StudentDetailsEntity;
import com.ashokit.entity.UserDetailsEntity;
import com.ashokit.repo.CourseRepo;
import com.ashokit.repo.EnquiryStatusRepo;
import com.ashokit.repo.StudentEnquiryRepo;
import com.ashokit.repo.UserDetailsRepo;
import com.ashokit.service.Enquiryservice;

@Service
public class EnquiryServiceimpl implements Enquiryservice {
	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnquiryRepo enqrepo;

	@Autowired
	private UserDetailsRepo userdetailsRepo;

	@Autowired
	private CourseRepo courserepo;

	@Autowired
	private EnquiryStatusRepo statusrepo;

	public DashBoardResponse getDashBoardData(Integer staffId) {

		DashBoardResponse dashboardresponse = new DashBoardResponse();
		Optional<UserDetailsEntity> findById = userdetailsRepo.findById(staffId);

		if (findById.isPresent()) {
			UserDetailsEntity userEntity = findById.get();
			List<StudentDetailsEntity> enquiries = userEntity.getStudententity();
			int totalcnt = enquiries.size();
			List<StudentDetailsEntity> enrolled = enquiries.stream().filter(e -> e.getStatus().equals("Enrolled"))
					.collect(Collectors.toList());

			int enrolledcnt = enrolled.size();
			int lostcnt = enquiries.stream().filter(e -> e.getStatus().equals("Lost")).collect(Collectors.toList())
					.size();

			dashboardresponse.setTotalEnquiry(totalcnt);
			dashboardresponse.setLost(lostcnt);
			dashboardresponse.setEnrolled(enrolledcnt);

		}

		return dashboardresponse;
	}

	@Override
	public List<String> getCourses() {
		// TODO Auto-generated method stub
		List<CourseEntity> findAll = courserepo.findAll();
		List<String> names = new ArrayList();

		for (CourseEntity entity : findAll) {

			names.add(entity.getCourseName());

		}

		return names;
	}

	@Override
	public List<String> getEnqiryStatus() {

		List<EnquiryStatusEntity> findAll = statusrepo.findAll();

		List<String> statusList = new ArrayList();

		for (EnquiryStatusEntity entity : findAll) {

			statusList.add(entity.getStatusName());

		}

		return statusList;
	}

	@Override
	public String saveEnquiry(Enquiryform form) {
		
		Integer staffId = (Integer) session.getAttribute("staffId");

		Optional<UserDetailsEntity> user = userdetailsRepo.findById(staffId);
		UserDetailsEntity userDetails = user.get();

		if (form.getEnquiryId() != null) {

			 Optional<StudentDetailsEntity> findById2 = enqrepo.findById(form.getEnquiryId());

			 StudentDetailsEntity entity = findById2.get();

			entity.setClassMode(form.getClassMode());
			entity.setCourse(form.getCourse());
			entity.setStatus(form.getStatus());
			entity.setContactNumber(form.getContactNumber());
			entity.setStudentName(form.getStudentName());

			entity.setUserDetails(userDetails);

			enqrepo.save(entity);
			return "record is updated Successfully";

		}
		
		StudentDetailsEntity enqEntity = new StudentDetailsEntity();

		BeanUtils.copyProperties(form, enqEntity);

		enqEntity.setUserDetails(userDetails);

		enqrepo.save(enqEntity);

		return "record saved successfully";
	}

	@Override
	public List<StudentDetailsEntity> getStudentDetails() {

		Integer staffId = (Integer) session.getAttribute("staffId");
		Optional<UserDetailsEntity> findById = userdetailsRepo.findById(staffId);
		if (findById.isPresent()) {

			UserDetailsEntity userDetailsEntity = findById.get();

			List<StudentDetailsEntity> enquiries = userDetailsEntity.getStudententity();

			return enquiries;
		}
		return null;

	}

@Override
public List<StudentDetailsEntity> getFilteredEnquiries(Integer staffId, EnquirySearchCriteria criteria) {

	
	Optional<UserDetailsEntity> findById = userdetailsRepo.findById(staffId);
	if (findById.isPresent()) {
		UserDetailsEntity userDetails = findById.get();
		List<StudentDetailsEntity> enquiries = userDetails.getStudententity();

		// filter logic

		if (null != criteria.getCourse() && !"".equals(criteria.getCourse())) {
			enquiries = enquiries.stream().filter(e -> e.getCourse().equals(criteria.getCourse()))
					.collect(Collectors.toList());

		}

		if (null != criteria.getEnquiryStatus() && !"".equals(criteria.getEnquiryStatus())) {
			enquiries = enquiries.stream().filter(e -> e.getStatus().equals(criteria.getEnquiryStatus()))
					.collect(Collectors.toList());

		}

		if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
			enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
					.collect(Collectors.toList());

		}

		return enquiries;
	}
	
	return null;
}
	
	
}	
	
	
	
	
	
	
	

