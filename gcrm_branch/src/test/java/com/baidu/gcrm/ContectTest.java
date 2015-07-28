package com.baidu.gcrm;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.model.ContactPerson.DecisionMakerStatus;
import com.baidu.gcrm.contact.model.ContactPerson.LegalPersonStatus;
import com.baidu.gcrm.contact.service.IContactService;

@Ignore
public class ContectTest extends BaseTestContext {
	@Autowired
	IContactService contactService;
	
	@Test
	@Rollback
	public void testSaveAndUpdateCustomerId(){
		Long currCustomerId = Long.valueOf(1111111111l);
		ContactPerson testContactPerson = new ContactPerson();
		testContactPerson.setCustomerNumber(currCustomerId);
		//testContactPerson.setGender(1);
		testContactPerson.setName("testName");
		testContactPerson.setPositionName("testPosition");
		testContactPerson.setSuperiorPosition("superiorPosition");
		testContactPerson.setDepartment("department");
		testContactPerson.setMobile("mobile");
		testContactPerson.setTelephone("telephone");
		testContactPerson.setEmail("email");
		testContactPerson.setIsLegalPerson(LegalPersonStatus.DISABLE);
		testContactPerson.setIsDecisionMaker(DecisionMakerStatus.DISABLE);
		
		testContactPerson.setCreateOperator(1l);
		testContactPerson.setCreateTime(new Date());
		testContactPerson.setUpdateOperator(1l);
		testContactPerson.setUpdateTime(new Date());
		
		contactService.saveContact(testContactPerson);
		Long currId = testContactPerson.getId();
		Assert.assertTrue(currId != null && currId.longValue() > 0);
		
		Long newCustomerId = Long.valueOf(2222222222l);
		int changeCount = contactService.updateCustomerId(newCustomerId, currCustomerId);
		Assert.assertEquals(changeCount, 1);
		
		
	}

}
