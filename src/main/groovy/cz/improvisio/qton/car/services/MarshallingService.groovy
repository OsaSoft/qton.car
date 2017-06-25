package cz.improvisio.qton.car.services

import cz.improvisio.qton.car.entities.CO2
import cz.improvisio.qton.car.entities.Device
import cz.improvisio.qton.car.entities.Humidity
import cz.improvisio.qton.car.entities.Temperature
import cz.improvisio.qton.car.entities.User
import groovy.json.JsonBuilder
import org.springframework.stereotype.Service

/**
 * Created by Osa-S on 25.06.2017.
 */
@Service
class MarshallingService {
	JsonBuilder marshallShort(User user) {
		new JsonBuilder([id: user?.id, username: user?.username, devices: user?.devices?.collect {marshallShort(it).content}])
	}

	JsonBuilder marshallShort(Device device) {
		new JsonBuilder([id: device?.id, uuid: device?.uuid])
	}

	JsonBuilder marshallShort(Temperature temperature, boolean predicted = false) {
		new JsonBuilder([value: temperature?.value, timestamp: temperature?.timestamp, predictedValue: predicted])
	}

	JsonBuilder marshallShort(CO2 co2, boolean predicted = false) {
		new JsonBuilder([value: co2?.value, timestamp: co2?.timestamp, predictedValue: predicted])
	}

	JsonBuilder marshallShort(Humidity humidity, boolean predicted = false) {
		new JsonBuilder([value: humidity?.value, timestamp: humidity?.timestamp, predictedValue: predicted])
	}
}
