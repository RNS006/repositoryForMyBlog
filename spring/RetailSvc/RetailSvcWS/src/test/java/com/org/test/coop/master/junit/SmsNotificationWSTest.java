package com.org.test.coop.master.junit;

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
import com.org.coop.bs.config.DozerConfig;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.retail.bs.config.RetailDozerConfig;
import com.org.coop.society.data.transaction.config.DataAppConfig;
import com.org.test.coop.junit.JunitTestUtil;
import com.org.test.coop.society.data.transaction.config.TestDataAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "com.org.test.coop")
@EnableAutoConfiguration(exclude = { DataAppConfig.class, DozerConfig.class})
@ContextHierarchy({
	  @ContextConfiguration(classes={TestDataAppConfig.class, RetailDozerConfig.class})
})
@WebAppConfiguration
public class SmsNotificationWSTest {
	private static final Logger logger = Logger.getLogger(SmsNotificationWSTest.class);
	
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;
	
	private String sendSmsJson = null;
	private String sendSms1Json = null;
	private String sendSms2Json = null;

	private ObjectMapper om = null;
	
	
	@Before
	public void runBefore() {
		try {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			om = new ObjectMapper();
			om.setSerializationInclusion(Include.NON_NULL);
			om.setDateFormat(df);
			sendSmsJson = JunitTestUtil.getFileContent("inputJson/retail/branch/customer/sendSmsNotification.json");
			sendSms1Json = JunitTestUtil.getFileContent("inputJson/retail/branch/customer/sendSmsNotification1.json");
			sendSms2Json = JunitTestUtil.getFileContent("inputJson/retail/branch/customer/sendSmsNotification2.json");
		} catch (Exception e) {
			logger.error("Error while initializing: ", e);
			Assert.fail("Error while initializing: ");
		}
	}
	@Test
	public void smsNotificationTest() {
		
		// Send SMS 3 times
		sendSmsNotification(sendSmsJson);
		sendSmsNotification(sendSms1Json);
		sendSmsNotification(sendSms2Json);
		getSmsNotificationsByBranchId();
		getSmsNotificationsByBranchIdAndCustomerId();
		getSmsNotificationsByMobileNumber();
		
	}

	
	
	private void sendSmsNotification(String sendSmsJson) {
		try {
			MvcResult result = this.mockMvc.perform(post("/rest/saveSmsNotifications")
				 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
				 .content(sendSmsJson)
				).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
			
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/customer/sendSmsNotification.json");
			
		} catch(Exception e) {
			logger.error("Error while adding  SMS notifications", e);
			Assert.fail("Error while adding  SMS notifications");
		}
	}
	
	private void getSmsNotificationsByBranchId() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getSmsNotifications?branchId=2")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/customer/getSmsNotificationsByBranchId.json");
		} catch(Exception e) {
			logger.error("Error while retrieving  SMS notifications", e);
			Assert.fail("Error while retrieving  SMS notifications");
		}
	}
	
	private void getSmsNotificationsByBranchIdAndCustomerId() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getSmsNotifications?branchId=2&customerId=1")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/customer/getSmsNotificationsByBranchIdAndCustomerId.json");
		} catch(Exception e) {
			logger.error("Error while retrieving SMS notifications", e);
			Assert.fail("Error while retrieving  SMS notifications");
		}
	}
	
	private void getSmsNotificationsByMobileNumber() {
		try {
			MvcResult result = this.mockMvc.perform(get("/rest/getSmsNotifications?branchId=2&mobileNo=9830525559")
					 .contentType("application/json").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ashish:ashish".getBytes()))
					).andExpect(status().isOk())
					.andExpect(content().contentType("application/json"))
					.andReturn();
				
			UIModel uiModel = getUIModel(result, "outputJson/retail/branch/customer/getSmsNotificationsByMobileNumber.json");
		} catch(Exception e) {
			logger.error("Error while retrieving SMS notifications", e);
			Assert.fail("Error while retrieving  SMS notifications");
		}
	}
	
	private UIModel getUIModel(MvcResult result)
			throws UnsupportedEncodingException, IOException,
			JsonParseException, JsonMappingException {
		String json = result.getResponse().getContentAsString();
		UIModel createBranchBean = om.readValue(json, UIModel.class);
		return createBranchBean;
	}
	
	private UIModel getUIModel(MvcResult result, String path)
			throws UnsupportedEncodingException, IOException,
			JsonParseException, JsonMappingException {
		UIModel createBranchBean = getUIModel(result);
		JunitTestUtil.writeJSONToFile(createBranchBean, path);
		return createBranchBean;
	}
	
}
