package com.org.test.coop.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.org.test.coop.master.junit.RetailBranchWSTest;
import com.org.test.coop.master.junit.RetailCustomerDeleteWSTest;
import com.org.test.coop.master.junit.RetailCustomerSetupWSTest;
import com.org.test.coop.master.junit.RetailDocumentWSTest;
import com.org.test.coop.master.junit.RetailMaterialDeleteWSTest;
import com.org.test.coop.master.junit.RetailMaterialWSTest;
import com.org.test.coop.master.junit.RetailRateChartWSTest;
import com.org.test.coop.master.junit.RetailStockDeleteWSTest;
import com.org.test.coop.master.junit.RetailStockEntryWSTest;
import com.org.test.coop.master.junit.RetailVatRegNoWSTest;
import com.org.test.coop.master.junit.RetailVendorDeleteWSTest;
import com.org.test.coop.master.junit.RetailVendorWSTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	RetailBranchWSTest.class,
	RetailMaterialWSTest.class,
	RetailDocumentWSTest.class,
	RetailVendorWSTest.class,
	RetailRateChartWSTest.class,
	RetailCustomerSetupWSTest.class,
	RetailVatRegNoWSTest.class,
	RetailStockEntryWSTest.class,
	RetailMaterialDeleteWSTest.class,
	RetailVendorDeleteWSTest.class,
//	RetailCustomerDeleteWSTest.class,
//	RetailStockDeleteWSTest.class,
	RetailBranchWSTest.class
})
public class RetailSvcWSTestSuite {
	
}
