package com.org.coop.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.coop.retail.entities.RetailCustomerMaster;

public interface RetailCustomerMasterRepository extends JpaRepository<RetailCustomerMaster, Integer> {
	@Query(value = "select count(*) from retail_customer_master where " +
			"exists(select * from material_tran_hrd where customer_id = :customerId limit 1) " +
			"and customer_id = :customerId", nativeQuery=true)
	public int checkIfAnyChildRecordExists(@Param("customerId") int customerId);
}
