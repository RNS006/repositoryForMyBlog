package com.org.coop.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.coop.retail.entities.RetailPayment;
import com.org.coop.retail.entities.RetailTransaction;

public interface RetailPaymentRepository extends JpaRepository<RetailPayment, Integer> {
	@Query("select rp from RetailPayment rp where rp.transactionPayment.paymentId = :paymentId and rp.materialTranHrd.tranId = :tranId")
	public RetailPayment findByPaymentIdAndTranId(@Param("paymentId")int paymentId,@Param("tranId")int tranId);
	
	
	@Query("select rp from RetailPayment rp where rp.materialTranHrd.tranId = :tranId")
	public RetailPayment findByTranId(@Param("tranId")int tranId);
}
