package cz.improvisio.qton.car.controllers

import cz.improvisio.qton.car.entities.Temperature
import cz.improvisio.qton.car.repositories.CO2Repository
import cz.improvisio.qton.car.repositories.TemperatureRepository
import cz.improvisio.qton.car.repositories.UserRepository
import cz.improvisio.qton.car.services.BakingService
import cz.improvisio.qton.car.services.MarshallingService
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by Osa-S on 24.06.2017.
 */
@Controller
@RequestMapping(path = "/api")
class ApiController {
	@Autowired
	UserRepository userRepository
	@Autowired
	TemperatureRepository temperatureRepository
	@Autowired
	CO2Repository co2Repository

	@Autowired
	BakingService bakingService
	@Autowired
	MarshallingService marshallingService

	@RequestMapping(path = "/status", method = RequestMethod.GET)
	@ResponseBody String status() {
		def rv = [:]
		rv.user =  marshallingService.marshallShort(userRepository.findByUsername("user1")).content //TODO: change to proper login logic and stuff
		Temperature temperature = temperatureRepository.findFirstByOrderByTimestampDesc()

		rv.temperature = temperature?.value
		rv.timestamp = temperature?.timestamp
		rv.personInside = bakingService.personInside(temperature)
//		rv.inMovement = TODO

		return new JsonBuilder(rv).toString()
	}

	@GetMapping(path = "/temperature")
	@ResponseBody String temperature(@RequestParam Date from, @RequestParam Date to) {
		def temps = temperatureRepository.findAllByTimestampBetween(from.time, to.time)

		if (to < new Date()) {
			return JsonOutput.toJson(temps)
		} else {
			//TODO: calc future prediction
		}
	}
}
