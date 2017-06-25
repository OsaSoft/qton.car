package cz.improvisio.qton.car.controllers

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Osa-S on 24.06.2017.
 */

class SigFoxControllerSpec extends Specification {
	def sigFoxController = new SigFoxController()

	MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sigFoxController).build()

	void setup() {
		sigFoxController.SENSOR = 24
		sigFoxController.MOVEMENT = 4
		sigFoxController.HEARTBEAT = 16
	}

	void cleanup() {}

	def "payload parsing works for sensor data"() {
		given:
			String payload = """{
			  "snr":0.0,
			  "time":123,
			  "rssi":1.1,
			  "device":"asd1234556asdfaf",
			  "data":"0000e0410000a44100002842"
			}"""

		expect:
			sigFoxController.parsePayload(payload) == ["snr": 0.0, "time":123,
			"rssi":1.1, "device": "asd1234556asdfaf",
			"data": ["co2": 42.0, "humidity": 20.5, "temperature": 28.0]]
	}

	@Unroll
	def "data parsing works"() {
		expect:
			sigFoxController.parseData(data) == result

		where:
			data << ["0000e0410000a44100002842", "4040", "0000ac4266666641"]
			result << [
					["co2": 42, "humidity": 20.5f, "temperature": 28],
					["movement": 3],
					["battery": 86, "engineStartAcceleration": 14.4f]
			]
	}
}
