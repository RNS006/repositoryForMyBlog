package com.org.coop.retail.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.org.coop.canonical.beans.UIModel;
import com.org.coop.retail.servicehelper.RetailBranchSetupServiceHelperImpl;

@Service
public class RetailBranchSetupServiceImpl {

	private static final Logger log = Logger.getLogger(RetailBranchSetupServiceImpl.class); 
	
	@Autowired
	private RetailBranchSetupServiceHelperImpl branchSetupServiceHelperImpl;
	
	
	public UIModel getBranch(int branchId) {
		return branchSetupServiceHelperImpl.getBranch(branchId);
	}
	
	public UIModel createRetailBranch(UIModel uiModel) {
		int branchId = uiModel.getBranchBean().getBranchId();
		uiModel = branchSetupServiceHelperImpl.createRetailBranch(uiModel);
		if(uiModel.getErrorMsg() != null) {
			return uiModel;
		}
		return branchSetupServiceHelperImpl.getBranch(branchId);
	}
}
