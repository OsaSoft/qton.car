package cz.improvisio.qton.car.controllers

import cz.improvisio.qton.car.entities.*
import cz.improvisio.qton.car.repositories.*
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

import java.util.logging.Logger

/**
 * Created by Osa-S on 24.06.2017.
 */
@Controller
@RequestMapping(path = "/sigfox")
@Log4j
class SigFoxController {
	@Value('${sigfox.data.format.sensor-data-length}')
	int SENSOR
	@Value('${sigfox.data.format.movement-data-length}')
	int MOVEMENT
	@Value('${sigfox.data.format.heartbeat-data-length}')
	int HEARTBEAT

	static Logger sigFoxLog = Logger.getLogger("sigFoxLog")

	@Autowired
	CO2Repository co2Repository
	@Autowired
	DeviceLogRepository deviceLogRepository
	@Autowired
	DeviceRepository deviceRepository
	@Autowired
	HumidityRepository humidityRepository
	@Autowired
	MovementRepository movementRepository
	@Autowired
	TemperatureRepository temperatureRepository

	@PostMapping(path = "/callback")
	@ResponseStatus(value = HttpStatus.OK)
	public void sigfoxCallback(@RequestBody String payload) {
		log.info("Received payload: $payload")
		def data = parsePayload(payload)
		def timestamp = new Date().time
		Device device = deviceRepository.findByUuid(data.device)
		if (!device) {
			device = new Device(uuid: data.device)
			deviceRepository.save(device)
		}

		def deviceLog = new DeviceLog(device: device, timestamp: timestamp, rssi: data.rssi, snr: data.snr)
		data = data.data

		deviceLog.battery = data?.battery
		deviceLogRepository.save(deviceLog)

		if (data.co2) {
			def co2 = new CO2(value: data.co2, timestamp: timestamp, device: device)
			co2Repository.save(co2)
		}
		if (data.humidity) {
			def humidity = new Humidity(value: data.humidity, timestamp: timestamp, device: device)
			humidityRepository.save(humidity)
		}
		if (data.temperature) {
			def temperature = new Temperature(value: data.temperature, timestamp: timestamp, device: device)
			temperatureRepository.save(temperature)
		}
		if (data.movement) {
			def movement = new Movement(value: data.movement, timestamp: timestamp, device: device)
			movementRepository.save(movement)
		}
	}

	private def parsePayload(String payload) {
		def slurper = new JsonSlurper().parseText(payload)
		def rv = [:]
		rv.with {
			snr = slurper.snr
			time = slurper.time
			rssi = slurper.rssi
			device = slurper.device
			data = parseData(slurper.data)
		}

		return rv
	}

	private def parseData(String data) {
		def rv = [:]

		switch (data.length()) {
			case SENSOR:
				def floats = []
				data.split("(?<=\\G........)").each {
					Long i = Long.parseLong(it, 16)
					floats << Float.intBitsToFloat(Integer.reverseBytes(i.intValue()))
				}
				rv.temperature = floats[0]
				rv.humidity = floats[1]
				rv.co2 = floats[2]
				break
			case MOVEMENT:
				Long i = Long.parseLong(data, 16)
				rv.movement = Float.intBitsToFloat(Integer.reverseBytes(i.intValue()))
				break
			case HEARTBEAT:
				Long i = Long.parseLong(data[0..7], 16)
				rv.battery = Float.intBitsToFloat(Integer.reverseBytes(i.intValue()))
				i = Long.parseLong(data[8..15], 16)
				rv.engineStartAcceleration = Float.intBitsToFloat(Integer.reverseBytes(i.intValue()))
				break
		}

		return rv
	}
}
