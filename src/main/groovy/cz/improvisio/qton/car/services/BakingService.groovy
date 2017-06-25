package cz.improvisio.qton.car.services

import cz.improvisio.qton.car.entities.CO2
import cz.improvisio.qton.car.entities.Humidity
import cz.improvisio.qton.car.entities.Temperature
import cz.improvisio.qton.car.repositories.CO2Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Osa-S on 25.06.2017.
 */
@Service
class BakingService {
	@Autowired
	CO2Repository co2Repository

	int personInside(Temperature temperature, CO2 co2, Humidity humidity) {
		def prevCo2 = co2Repository.findFirstByTimestampLessThanOrderByTimestampDesc(co2.timestamp)
		println("co $co2.id prev $prevCo2.id")

		if (prevCo2.value > co2.value || co2.value <= 1000) return 0
		if (co2.value > 1000) return 1
		if (co2.value > 2300) return 2

	}
}
