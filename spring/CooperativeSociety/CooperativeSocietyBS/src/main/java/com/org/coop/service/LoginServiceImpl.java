package com.org.coop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.coop.society.data.customer.entities.User;
import com.org.coop.society.data.customer.repositories.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserRepository staffRepository;
	
	public String getRole(String username, String password) {
		List<User> staffList = staffRepository.findAll();
		System.out.println("Staffsize:" + staffList.get(0).getFirstName());
		return staffList.get(0).getUserRoles().get(0).getRoleMaster().getRoleName();
	}
}