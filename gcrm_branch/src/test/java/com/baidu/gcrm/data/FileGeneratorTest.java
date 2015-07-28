package com.baidu.gcrm.data;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.data.service.IFileTimerService;
@Ignore
public class FileGeneratorTest extends BaseTestContext {
	@Autowired
	IFileTimerService fileService;
	
	@Test
	public void testGenerateFile() {
		fileService.generateADContentFileEveryFiveMinutes();
	}
	
	@Test
	public void testGeneratePositionFile() {
		fileService.generatePositionFileEveryFiveMinutes();
	}

}
