package com.org.coop.admin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.coop.bs.mapper.BranchMappingImpl;
import com.org.coop.canonical.beans.BranchBean;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.society.data.admin.entities.BranchMaster;
import com.org.coop.society.data.admin.entities.User;
import com.org.coop.society.data.admin.repositories.BranchMasterRepository;
import com.org.coop.society.data.admin.repositories.UserAdminRepository;

@Service
public class BranchSetupServiceImpl {

	private static final Logger log = Logger.getLogger(BranchSetupServiceImpl.class); 
	
	@Autowired
	private BranchMasterRepository branchMasterRepository;
	
	@Autowired
	private UserAdminRepository userAdminRepository;
	
	@Autowired
	private BranchMappingImpl branchMappingImpl;
	
	@Transactional(value="adminTransactionManager")
	public void addOrUpdateBranch(UIModel uiModel) {
		// Check if the branch already exists
		BranchMaster branch = branchMasterRepository.findByMicrCodeAndIfscCode(uiModel.getBranchBean().getMicrCode(), uiModel.getBranchBean().getIfscCode());
		if(branch != null && uiModel.getBranchBean().getBranchId() == 0) {
			uiModel.setErrorMsg("Branch already exists");
			return;
		}
		if(branch == null) {
			branch = new BranchMaster();
		}
		branchMappingImpl.mapBean(uiModel.getBranchBean(), branch);
		branchMasterRepository.save(branch);
		log.debug("A new branch has been created");
	}
	
	@Transactional(value="customerTransactionManager")
	public UIModel getBranch(int branchId) {
		UIModel uiModel = new UIModel();
		// Check if the branch already exists
		BranchMaster branch = branchMasterRepository.findOne(branchId);
		if(branch == null) {
			uiModel.setErrorMsg("Branch does not exist for Branch Id: " + branchId);
			return uiModel;
		}
		BranchBean branchBean = new BranchBean();
		branchMappingImpl.mapBean(branch, branchBean);
		uiModel.setBranchBean(branchBean);
		
		log.debug("Branch details has been retrieved from database for branchId: " + branchId);
		return uiModel;
	}
	
}