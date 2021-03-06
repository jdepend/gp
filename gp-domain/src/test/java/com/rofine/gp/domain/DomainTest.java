package com.rofine.gp.domain;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rofine.gp.platform.exception.GpException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DomainTest {

	@Autowired
	private DomainTestService domainTestService;

	@BeforeClass
	public static void testInit() {
		DomainTestService.testInit();
	}

	@Test
	public void testDomain() throws GpException {
		domainTestService.testDomain();
	}

}
