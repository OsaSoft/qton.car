package cz.improvisio.qton.car.controllers

import cz.improvisio.qton.car.entities.CO2
import cz.improvisio.qton.car.entities.Humidity
import cz.improvisio.qton.car.entities.Temperature
import cz.improvisio.qton.car.repositories.CO2Repository
import cz.improvisio.qton.car.repositories.HumidityRepository
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
	HumidityRepository humidityRepository
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
		CO2 co2 = co2Repository.findByTimestamp(temperature?.timestamp)
		Humidity humidity = humidityRepository.findByTimestamp(temperature?.timestamp)

		rv.temperature = temperature?.value
		rv.co2 = co2?.value
		rv.humidity = humidity?.value
		rv.timestamp = temperature?.timestamp
		rv.personInside = bakingService.personInside(temperature, co2, humidity)

		return new JsonBuilder(rv).toString()
	}

	@GetMapping(path = "/temperature")
	@ResponseBody String temperature(@RequestParam long from, @RequestParam long to) {
		def fromDate = new Date(from)
		def toDate = new Date(to)

		def rv = [:]
		rv.user =  marshallingService.marshallShort(userRepository.findByUsername("user1")).content //TODO: change to proper login logic and stuff
		rv.temperatures = temperatureRepository.findAllByTimestampBetween(fromDate.time, toDate.time).collect {
			marshallingService.marshallShort(it).content
		}

		new JsonBuilder(rv).toString()
	}

	@GetMapping(path = "/humidity")
	@ResponseBody String humidity(@RequestParam long from, @RequestParam long to) {
		def fromDate = new Date(from)
		def toDate = new Date(to)

		def rv = [:]
		rv.user =  marshallingService.marshallShort(userRepository.findByUsername("user1")).content //TODO: change to proper login logic and stuff
		rv.humidities = humidityRepository.findAllByTimestampBetween(fromDate.time, toDate.time).collect {
			marshallingService.marshallShort(it).content
		}

		new JsonBuilder(rv).toString()
	}

	@GetMapping(path = "/co2")
	@ResponseBody String co2(@RequestParam long from, @RequestParam long to) {
		def fromDate = new Date(from)
		def toDate = new Date(to)

		def rv = [:]
		rv.user =  marshallingService.marshallShort(userRepository.findByUsername("user1")).content //TODO: change to proper login logic and stuff
		rv.co2s = co2Repository.findAllByTimestampBetween(fromDate.time, toDate.time).collect {
			marshallingService.marshallShort(it).content
		}

		new JsonBuilder(rv).toString()
	}
}
