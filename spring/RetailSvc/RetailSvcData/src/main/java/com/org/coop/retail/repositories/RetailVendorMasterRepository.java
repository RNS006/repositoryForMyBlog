package com.org.coop.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.coop.retail.entities.RetailMaster;
import com.org.coop.retail.entities.VendorMaster;

public interface RetailVendorMasterRepository extends JpaRepository<VendorMaster, Integer> {
	@Query(value = "select count(*) from vendor_master where " +
			"exists(select * from stock_entry where vendor_id = :vendorId limit 1) " +
			"and vendor_id = :vendorId", nativeQuery=true)
	public int checkIfAnyChildRecordExists(@Param("vendorId") int vendorId);
}
