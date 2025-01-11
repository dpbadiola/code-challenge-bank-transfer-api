package com.dpbapps.codechallenge.banktransfer;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseControllerTest {

	protected MockMvc mockMvc;

	protected MockMvc setupMockMvc(WebApplicationContext context) {
		return MockMvcBuilders
			.webAppContextSetup(context)
			.alwaysDo(MockMvcResultHandlers.print())
			.build();
	}

}
