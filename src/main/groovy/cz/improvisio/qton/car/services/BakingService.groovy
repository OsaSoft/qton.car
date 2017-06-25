package cz.improvisio.qton.car.services

import cz.improvisio.qton.car.entities.CO2
import cz.improvisio.qton.car.entities.Humidity
import cz.improvisio.qton.car.entities.Temperature
import org.springframework.stereotype.Service

/**
 * Created by Osa-S on 25.06.2017.
 */
@Service
class BakingService {

	boolean personInside(Temperature temperature, CO2 co2, Humidity humidity) {
		return true
	}
}
