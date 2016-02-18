package com.org.test.coop.master.junit;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.coop.admin.service.BranchSetupServiceImpl;
import com.org.coop.bs.config.DozerConfig;
import com.org.coop.canonical.beans.AddressBean;
import com.org.coop.canonical.beans.BranchBean;
import com.org.coop.canonical.beans.BranchRuleBean;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.society.data.admin.entities.BranchMaster;
import com.org.coop.society.data.admin.repositories.BranchMasterRepository;
import com.org.coop.society.data.transaction.config.DataAppConfig;
import com.org.test.coop.junit.JunitTestUtil;
import com.org.test.coop.society.data.transaction.config.TestDataAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "com.org.test.coop"/*, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = DataAppConfig.class) }*/)
@EnableAutoConfiguration(exclude = { DataAppConfig.class})
@ContextHierarchy({
	  @ContextConfiguration(classes={TestDataAppConfig.class, DozerConfig.class})
})
@WebAppConfiguration
public class BranchWSTest {
	private static final Logger logger = Logger.getLogger(BranchWSTest.class);
	
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	
	private String createBranchJson = null;
	private String addAddressJson = null;
	private String addAnotherAddressJson = null;
	private String createAnotherBranchJson = null;
	private String updateBranchJson = null;
	private String addBranchRuleJson = null;
	
	private ObjectMapper om = null;
	
	@Autowired
	private BranchSetupServiceImpl branchSetupServiceImpl;
	
	@Autowired
	private BranchMasterRepository branchMasterRepository;
	
	@Before
	public void runBefore() {
		try {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			om = new ObjectMapper();
			om.setSerializationInclusion(Include.NON_NULL);
			om.setDateFormat(df);
			createBranchJson = JunitTestUtil.getFileContent("inputJson/master/branch/addBranch.json");
			addAddressJson = JunitTestUtil.getFileContent("inputJson/master/branch/addAddress.json");
			
			addAnotherAddressJson = JunitTestUtil.getFileContent("inputJson/master/branch/addAnotherAddress.json");
			
			createAnotherBranchJson = JunitTestUtil.getFileContent("inputJson/master/branch/addAnotherBranch.json");
			
			updateBranchJson = JunitTestUtil.getFileContent("inputJson/master/branch/updateBranchAndAddress.json");
			
			addBranchRuleJson = JunitTestUtil.getFileContent("inputJson/master/branch/branchRules/addBranchRules.json");
			
		} catch (Exception e) {
			logger.error("Error while initializing: ", e);
		}
	}
	@Test
	public void testBranch() {
		addNewBranch();
		addNewAddress();
		addAnotherAddress();
		addDuplicateBranch();
		addAnotherNewBranch();
		updateBranch();
		addBranchRules();
	}

	private void addNewBranch() {
		try {
//			MvcResult result = this.mockMvc.perform(get("/rest/getBranch?branchId=1")
//					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
//					).andExpect(status().isOk())
//					.andExpect(content().contentType("application/json"))
//					.andReturn();
//				
//			UIModel uiModel = getUIModel(result);
//			if(uiModel.getErrorMsg() != null) {
//				return;
//			}
			
			MvcResult result = this.mockMvc.perform(post("/rest/createBranch")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(createBranchJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result);
			assertNull(uiModel.getErrorMsg());
			assertEquals(uiModel.getBranchBean().getBranchId(), 1);
			assertEquals("Kalipur",uiModel.getBranchBean().getBankName());
			assertEquals("Kalipur",uiModel.getBranchBean().getBranchName());
			assertEquals("9830625559",uiModel.getBranchBean().getPhone1());
			assertEquals("12345743",uiModel.getBranchBean().getIfscCode());
			assertEquals("87656321",uiModel.getBranchBean().getMicrCode());
			assertEquals("ashismo@gmail.com",uiModel.getBranchBean().getEmail1());
			assertEquals("coop_kalipur",uiModel.getBranchBean().getDbName());
			assertEquals("CustomerSvcWS",uiModel.getBranchBean().getContextRoot());
		} catch(Exception e) {
			logger.error("Error while creating branch", e);
		}
	}
	
	private void addNewAddress() {
		try {
			MvcResult result = this.mockMvc.perform(post("/rest/createBranch")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAddressJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result);
			AddressBean address = uiModel.getBranchBean().getAddresses().get(0);
			
			assertNull(uiModel.getErrorMsg());
			assertEquals(uiModel.getBranchBean().getBranchId(), 1);
			assertEquals("HOME",address.getAddressType());
			assertEquals("Queen Palace",address.getAddressName());
			assertEquals("Kalipur",address.getAddressLine1());
			assertEquals("712708",address.getPin());
			assertEquals(1,address.getDistId());
			assertEquals("9830525559",address.getPhoneNo1());
			assertEquals("CustomerSvcWS",uiModel.getBranchBean().getContextRoot());
		} catch(Exception e) {
			logger.error("Error while adding address", e);
		}
	}
	
	private void addAnotherAddress() {
		try {
			MvcResult result = this.mockMvc.perform(post("/rest/createBranch")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(addAnotherAddressJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result);
			AddressBean address = uiModel.getBranchBean().getAddresses().get(1);
			
			assertNull(uiModel.getErrorMsg());
			assertEquals(uiModel.getBranchBean().getBranchId(), 1);
			assertEquals("OFFICE",address.getAddressType());
			assertEquals("Benipur West Para",address.getAddressName());
			assertEquals("Janai",address.getAddressLine1());
			assertEquals("712304",address.getPin());
			assertEquals(2,address.getDistId());
			assertEquals("9830525559",address.getPhoneNo1());
			assertEquals("CustomerSvcWS",uiModel.getBranchBean().getContextRoot());
		} catch(Exception e) {
			logger.error("Error while adding address", e);
		}
	}
	
	private void addDuplicateBranch() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getBranch?branchId=1")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result);
			
			uiModel.getBranchBean().setBranchId(0);
			
			result = this.mockMvc.perform(post("/rest/createBranch")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					 .content(createBranchJson)
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			uiModel = getUIModel(result);
			
			assertNotNull(uiModel.getErrorMsg());
				
		} catch (Exception e) {
			logger.error("Error while adding duplicate branch: ", e);
			Assert.fail("Error while adding duplicate branch:");
		}
	}
	private UIModel getUIModel(MvcResult result)
			throws UnsupportedEncodingException, IOException,
			JsonParseException, JsonMappingException {
		String json = result.getResponse().getContentAsString();
		UIModel createBranchBean = om.readValue(json, UIModel.class);
		return createBranchBean;
	}
	
	private void addAnotherNewBranch() {
		try {
			MvcResult result = this.mockMvc.perform(post("/rest/createBranch")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(createAnotherBranchJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result);
			
			assertEquals(2,uiModel.getBranchBean().getBranchId());
			assertEquals("Janai",uiModel.getBranchBean().getBankName());
			assertEquals("Janai",uiModel.getBranchBean().getBranchName());
			assertEquals("9830625559",uiModel.getBranchBean().getPhone1());
			assertEquals("12345678",uiModel.getBranchBean().getIfscCode());
			assertEquals("87654321",uiModel.getBranchBean().getMicrCode());
			assertEquals("ashismo@gmail.com",uiModel.getBranchBean().getEmail1());
			assertEquals("coop_janai",uiModel.getBranchBean().getDbName());
			assertEquals("CustomerSvcWS",uiModel.getBranchBean().getContextRoot());
			assertNull(uiModel.getErrorMsg());
		} catch(Exception e) {
			logger.error("Error while creaing branch", e);
		}
	}
	
	private void updateBranch() {
		try {
			MvcResult result = this.mockMvc.perform(post("/rest/createBranch")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					 .content(updateBranchJson)
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
			
			UIModel uiModel = getUIModel(result);
			
			BranchBean branch = uiModel.getBranchBean();
			assertEquals(branch.getPhone1(), "9830525559");
			assertEquals(branch.getUpdateUser(), "ashish");
			assertEquals(branch.getAddresses().get(0).getAddressLine1(), "Kalipur Check Post");
			assertEquals(branch.getAddresses().get(0).getUpdateUser(), "ashish");
			
		} catch (Exception e) {
			logger.error("Error while updating Branch: ", e);
			Assert.fail("Error while updating Branch:");
		}
	}
	
	private void addBranchRules() {
		try {
			MvcResult result = this.mockMvc.perform(post("/rest/createBranch")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					 .content(addBranchRuleJson)
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
			
			UIModel uiModel = getUIModel(result);
			
			BranchRuleBean branchRule = uiModel.getBranchBean().getBranchRuleBeans().get(0);
			assertEquals(branchRule.getBranchName(), "Kalipur");
			assertEquals(branchRule.getBranchId(), 1);
			assertEquals(branchRule.getRuleId(), 1);
			assertEquals(branchRule.getRuleName(), "LOCK_AFTER_NO_OF_ATTEMPTS");
			assertEquals(branchRule.getRuleValue(), "3");
			
		} catch (Exception e) {
			logger.error("Error while adding Branch rules: ", e);
			Assert.fail("Error while adding Branch rules:");
		}
	}
	
}