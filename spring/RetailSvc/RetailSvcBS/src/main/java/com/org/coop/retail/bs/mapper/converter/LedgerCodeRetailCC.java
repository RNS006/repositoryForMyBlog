package com.org.coop.retail.bs.mapper.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.coop.canonical.account.beans.LedgerCodeRetailBean;
import com.org.coop.retail.entities.LedgerCodeRetail;
import com.org.coop.retail.entities.MaterialGroup;
import com.org.coop.retail.entities.VendorMaster;
import com.org.coop.retail.repositories.RetailMaterialGroupMasterRepository;
import com.org.coop.retail.repositories.RetailVendorMasterRepository;

@Component("ledgerCodeRetailCC")
public class LedgerCodeRetailCC extends DozerConverter<LedgerCodeRetailBean, LedgerCodeRetail> implements MapperAware, CustomConverter {
	@Autowired
	private RetailMaterialGroupMasterRepository retailMaterialGroupMasterRepository;
	
	@Autowired
	private RetailVendorMasterRepository retailVendorMasterRepository;
	
	
	public LedgerCodeRetailCC() {
		super(LedgerCodeRetailBean.class, LedgerCodeRetail.class);
	}
	
	public LedgerCodeRetailCC(Class prototypeA, Class prototypeB) {
		super(prototypeA, prototypeB);
	}
	
	public void setMapper(Mapper arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LedgerCodeRetailBean convertFrom(LedgerCodeRetail src, LedgerCodeRetailBean dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerCodeRetail convertTo(LedgerCodeRetailBean src, LedgerCodeRetail dest) {
		// TODO Auto-generated method stub
		if(src != null) {
			MaterialGroup materialGrp = retailMaterialGroupMasterRepository.findOne(src.getMaterialGrpId());
			dest.setMaterialGroup(materialGrp);
			
			if(src.getVendorId() > 0) {
				VendorMaster vendor = retailVendorMasterRepository.findOne(src.getVendorId());
				dest.setVendorMaster(vendor);
			}
		}
		return dest;
	}
}
