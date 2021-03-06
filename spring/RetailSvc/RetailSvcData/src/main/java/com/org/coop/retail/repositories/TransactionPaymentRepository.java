package com.org.coop.retail.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.coop.retail.entities.TransactionPayment;

public interface TransactionPaymentRepository extends JpaRepository<TransactionPayment, Integer> {
	
	@Query("select tp from TransactionPayment tp where tp.branchMaster.branchId = :branchId and tp.glLedgerHrd.glTranId = :glTranId")
	public List<TransactionPayment> findByBranchIdAndGlTranId(@Param("branchId")int branchId,@Param("glTranId")int glTranId);
}
