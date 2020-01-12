package com.jukebox.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			ResponseEntity<List<Juke>> jukesResponse = restTemplate.exchange(
//					"http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes/", HttpMethod.GET, null,
//					new ParameterizedTypeReference<List<Juke>>() {
//					});
//			List<Juke> jukes = jukesResponse.getBody();
//			log.warn(jukes.get(0).toString());
//
//			SettingList settingsResponse = restTemplate.getForObject(
//					"http://my-json-server.typicode.com/touchtunes/tech-assignment/settings/", SettingList.class);
//			List<Setting> settings = settingsResponse.getSettings();
//			log.warn(settings.toString());
//		};
//	}

}
