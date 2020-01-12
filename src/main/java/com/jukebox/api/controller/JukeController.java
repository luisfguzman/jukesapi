package com.jukebox.api.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jukebox.api.bean.Juke;
import com.jukebox.api.bean.Setting;
import com.jukebox.api.bean.SettingList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Jukebox API")
@RequestMapping("/api")
public class JukeController {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@ApiOperation(value = "View a list of available jukes given a setting", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/jukes")
	public ResponseEntity<Page<Juke>> getJukes(@RequestParam String settingId, @RequestParam Optional<String> model,
			@RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit, RestTemplate restTemplate) {

		SettingList settingsResponse = restTemplate.getForObject(
				"http://my-json-server.typicode.com/touchtunes/tech-assignment/settings/", SettingList.class);
		List<Setting> settings = settingsResponse.getSettings();

		Setting setting = settings.stream().filter(s -> settingId.equals(s.getId())).findAny().orElse(null);
		if (setting == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		ResponseEntity<List<Juke>> jukesResponse = restTemplate.exchange(
				"http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Juke>>() {
				});
		List<Juke> jukes = jukesResponse.getBody();

		Set<String> settingRequires = new HashSet<String>(setting.getRequires());

		List<Juke> results = new ArrayList<Juke>();
		for (Juke j : jukes) {
			if (j.retComponentsAsSet().containsAll(settingRequires)) {
				results.add(j);
			}
		}
		if (model.isPresent())
			results = results.parallelStream().filter(j -> model.get().equals(j.getModel()))
					.collect(Collectors.toList());

		if ((offset.isPresent() && offset.get() < 0))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if ((limit.isPresent() && limit.get() < 1))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(results.size()));
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > results.size() ? results.size()
				: (start + pageable.getPageSize()));

		Page<Juke> page = new PageImpl<Juke>(results.subList(start, end), pageable, results.size());
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
}
