package cz.improvisio.qton.car.services

import cz.improvisio.qton.car.entities.Device
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
}
