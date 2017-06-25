package cz.improvisio.qton.car.services

import cz.improvisio.qton.car.entities.Temperature
import org.springframework.stereotype.Service

/**
 * Created by Osa-S on 25.06.2017.
 */
@Service
class BakingService {

	boolean personInside(Temperature temperature) {
		return true
	}
}
