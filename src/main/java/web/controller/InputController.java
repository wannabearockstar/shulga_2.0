package web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.model.bound.BoundCollection;
import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import mapper.ScheduleConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.Result;
import web.service.algorithm.AlgorithmService;
import web.service.data.DataService;

import java.io.IOException;

@Controller()
@RequestMapping(value = "/input")
public class InputController {

	@Autowired
	private DataService dataService;
	@Autowired
	private AlgorithmService algorithmService;

	@RequestMapping(value = "/models", method = RequestMethod.GET)
	public String inputStepModelsPage(Model model) {
		return "input/models";
	}

	@RequestMapping(value = "/{id}/models", method = RequestMethod.GET)
	public String inputStepModelsPage(@PathVariable("id") String id, Model model) throws IOException {
		ScheduleConfig config = dataService.getScheduleConfig(id);
		model.addAttribute("config", new ObjectMapper().writeValueAsString(config));
		return "input/models";
	}

	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public ResponseEntity<Result> saveConfiguration(@RequestBody CurriculumUnit[] curriculum) throws IOException {
		return new ResponseEntity<>(Result.success(dataService.createScheduleConfig(curriculum)), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/config", method = RequestMethod.POST)
	public ResponseEntity<Result> saveConfiguration(@PathVariable("id") String id, @RequestBody CurriculumUnit[] curriculum)
		throws IOException {

		ScheduleConfig loaded = dataService.getScheduleConfig(id);
		ScheduleConfig generated = ScheduleConfigLoader.fromCurriculum(curriculum);
		generated.setBounds(loaded.getBounds());

		dataService.saveSchedule(id, generated);

		return new ResponseEntity<>(Result.success(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/config", method = RequestMethod.GET)
	public ResponseEntity<Result> getConfiguration(@PathVariable("id") String id) throws IOException {
		return new ResponseEntity<>(Result.success(dataService.getScheduleConfig(id)), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/boundaries", method = RequestMethod.GET)
	public String inputStepBoundariesPage(@PathVariable("id") String id, Model model) throws IOException {
		ScheduleConfig config = dataService.getScheduleConfig(id);
		model.addAttribute("config", new ObjectMapper().writeValueAsString(config));
		return "input/boundaries";
	}

	@RequestMapping(value = "/{id}/boundaries", method = RequestMethod.PUT)
	public ResponseEntity<Result> setBoundaries(@PathVariable("id") String id, @RequestBody BoundCollection boundCollection)
		throws IOException {
		return new ResponseEntity<>(Result.success(dataService.setBoundaries(id, boundCollection)), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/run", method = RequestMethod.POST)
	public ResponseEntity<Result> runAlgorithm(@PathVariable("id") String id) {
		return new ResponseEntity<>(Result.success(algorithmService.runAlgorithm(id)), HttpStatus.OK);
	}

	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String inputDemoModelsPage(Model model) throws IOException {
		ScheduleConfig config = ScheduleConfigLoader.fromLocal("config.json");
		model.addAttribute("config", new ObjectMapper().writeValueAsString(config));
		return "input/models";
	}

	@RequestMapping(value = "/demo/config", method = RequestMethod.POST)
	public ResponseEntity<Result> saveDemoConfiguration(@RequestBody CurriculumUnit[] curriculum) throws IOException {
		ScheduleConfig loaded = ScheduleConfigLoader.fromLocal("config.json");

		String session_id = dataService.createScheduleConfig(loaded, curriculum);
		dataService.setBoundaries(session_id, loaded.getBounds());

		return new ResponseEntity<>(Result.success(session_id), HttpStatus.OK);
	}
}
