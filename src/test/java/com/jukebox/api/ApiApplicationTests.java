package com.jukebox.api;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.jukebox.api.bean.Juke;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testBadRequest() {
		try {
			ResponseEntity<Page<Juke>> response = restTemplate.exchange(getRootUrl() + "/api/jukes", HttpMethod.GET,
					null, new ParameterizedTypeReference<Page<Juke>>() {
					});
			Assert.assertNull(response.getBody());
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
		}
	}

	@Test
	public void testRequest() {
		ResponseEntity<Page<Juke>> response = restTemplate.exchange(getRootUrl() + "/api/jukes?settingId=2321763c-8e06-4a31-873d-0b5dac2436da",
				HttpMethod.GET, null, new ParameterizedTypeReference<Page<Juke>>() {
				});
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testModelRequest() {
		ResponseEntity<Page<Juke>> response = restTemplate.exchange(getRootUrl() + "/api/jukes?settingId=2321763c-8e06-4a31-873d-0b5dac2436da&model=angelina",
				HttpMethod.GET, null, new ParameterizedTypeReference<Page<Juke>>() {
				});
		Assert.assertNotNull(response.getBody());
	}

}
